package hillinsight.metadata.api.utils.excel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import hillinsight.metadata.api.dto.web.MetaAndThirdImportResult;
import hillinsight.metadata.api.models.vo.ThirdFieldExcelimportCopy;
import hillinsight.metadata.api.models.vo.ThirdFieldExcelimportVo;
import hillinsight.metadata.api.utils.convention.Constant;
import hillinsight.metadata.api.utils.convention.UniqueUtils;
import hillinsight.metadata.api.utils.s3.AwsS3Util;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.BuilderException;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.*;
import java.util.List;

/**
 * excel根据模板导出
 * 模板需要标题行,以及第一行数据行(提供样式)
 *
 * @author db117
 * @date 2019/12/23/023 11:02
 */
@Slf4j
public class ExcelExportUtil {
    private static ThreadLocal<ExportObject> threadLocal = new ThreadLocal<>();

    /**
     * 构建导出对象(默认关闭输入流)
     *
     * @param inputStream 模板文件流
     * @param objectKey   对象key对应得列数(0开始)
     * @param sheetMaxRow 单sheet大小(2-100W)
     */
    public static void build(InputStream inputStream, String[] objectKey, int sheetMaxRow) {
        build(inputStream, true, objectKey, sheetMaxRow);
    }

    /**
     * 构建导出对象
     *
     * @param inputStream   模板文件流
     * @param isCloseStream 是否关闭流
     * @param objectKey     对象key对应得列数(0开始)
     * @param sheetMaxRow   单sheet大小(2-100W)
     */
    @SneakyThrows({IOException.class})
    public static void build(InputStream inputStream, boolean isCloseStream, String[] objectKey, int sheetMaxRow) {
        if (sheetMaxRow > 1000000 || sheetMaxRow < 2) {
            throw new IllegalArgumentException("单sheet最大行不正确");
        }
        threadLocal.remove();

        CellStyle[] contentCellStyles;
        String[] headTitle;
        CellStyle[] headCellStyles;

        // 为不影响源文件,不直接读取文件
        InputStream is = new ByteArrayInputStream(IoUtil.readBytes(inputStream, isCloseStream));
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        XSSFSheet sheet = workbook.getSheetAt(0);
        // 样式行
        XSSFRow row = sheet.getRow(1);
        // 列数(0开始)
        int len = row.getLastCellNum();

        // 数据样式
        contentCellStyles = new CellStyle[len];
        row.iterator().forEachRemaining(cell -> contentCellStyles[cell.getColumnIndex()] = cell.getCellStyle());

        // 表头
        XSSFRow rowHead = sheet.getRow(0);
        headTitle = new String[len];
        headCellStyles = new CellStyle[len];
        rowHead.iterator().forEachRemaining(cell -> {
            headCellStyles[cell.getColumnIndex()] = cell.getCellStyle();
            headTitle[cell.getColumnIndex()] = cell.getStringCellValue();
        });

        // 列宽
        int[] colWidth = new int[len];
        for (int i = 0; i < colWidth.length; i++) {
            colWidth[i] = sheet.getColumnWidth(i);
        }
        int numberOfSheets = workbook.getNumberOfSheets();

        // 删除所有内容
        for (int i = 0; i < numberOfSheets; i++) {
            workbook.removeSheetAt(0);
        }


        ExportObject exportObject = new ExportObject()
                .setHeadCellStyles(headCellStyles)
                .setHeadTitle(headTitle)
                .setWorkbook(new SXSSFWorkbook(workbook))
                .setContentCellStyles(contentCellStyles)
                .setObjectKey(objectKey)
                .setSheetMaxRow(sheetMaxRow)
                .setColWidth(colWidth)
                .setErrorStyle(sheet.getRow(1).getCell(16).getCellStyle());
        threadLocal.set(exportObject);
    }

    /**
     * 写入数据
     */
    public static void writeMap(List<Map<String, Object>> data) {
        for (Map<String, Object> map : data) {
            writeByRow(map, null, null);
        }
    }

    /**
     * 写入数据 用于元数据导出错误报告
     */
    public static void writeObject(List data, MetaAndThirdImportResult metaFieldImportResult, Integer type) {
        writeByRow(new HashMap<>(), metaFieldImportResult, type);
        for (Object o : data) {
            writeByRow(BeanUtil.beanToMap(o), metaFieldImportResult, type);
        }
    }

    /**
     * 写入数据 用于元数据正常标准导出
     */
    public static void writeObjectNorm(List data) {
        for (Object o : data) {
            writeByRowNorm(BeanUtil.beanToMap(o));
        }
    }

    /**
     * 导出空数据文件,包含表头
     */
    public static File stopNotDate() {
        getSheet();
        return stop();
    }

    /**
     * 导出结束
     */
    @SneakyThrows
    public static File stop() {
        ExportObject exportObject = threadLocal.get();
        File tempFile = File.createTempFile("excelExport", ".xlsx");
        try (SXSSFWorkbook workbook = exportObject.workbook;
             OutputStream outputStream = new FileOutputStream(tempFile)) {
            workbook.write(outputStream);
        }
        return tempFile;
    }

    /**
     * 导出结束(业务对象导出返回File文件)
     */
    @SneakyThrows
    public static File thirdStop(XSSFWorkbook wk) {
        File tempFile = File.createTempFile("excelExport", ".xlsx");
        try (XSSFWorkbook workbook = wk;
             OutputStream outputStream = new FileOutputStream(tempFile)) {
            workbook.write(outputStream);
        }
        return tempFile;
    }

    /**
     * 写入行
     */
    private static void writeByRow(Map<String, Object> map, MetaAndThirdImportResult metaFieldImportResult, Integer type) {
        checkRange();
        ExportObject exportObject = threadLocal.get();
        SXSSFSheet sheet = getSheet();

        //不同业务不同处理
        if (type == Constant.META_OBJECT_TYPE) {//处理元数据导入信息
            metaFeildExInfoBuild(exportObject, sheet, map, metaFieldImportResult);
        } else if (type == Constant.THIRD_OBJECT_TYPE) {//处理业务据导入信息
            thirdFeildExInfoBuild(exportObject, sheet, map, metaFieldImportResult);
        }
        exportObject.rowNum++;
    }

    /**
     * 写入行 用于元数据正常导出
     */
    private static void writeByRowNorm(Map<String, Object> map) {
        checkRange();
        ExportObject exportObject = threadLocal.get();
        SXSSFSheet sheet = getSheet();
        if (exportObject.rowNum == 1) {//合并元数据模板第一行
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
        }

        // 内容
        String[] objectKey = exportObject.getObjectKey();
        // 获取行对象
        SXSSFRow row = sheet.createRow(exportObject.rowNum + 1);
        // 获取行对象
        SXSSFRow rowObjectKey = sheet.createRow(1);

        for (int i = 0; i < objectKey.length; i++) {
            SXSSFCell cell = row.createCell(i);
            SXSSFCell cell1 = rowObjectKey.createCell(i);
            cell1.setCellStyle(exportObject.contentCellStyles[i]);
            Object o = map.get(objectKey[i]);
            if (objectKey == null) {
                continue;
            }
            //插入表头
            setValue(objectKey[i], cell1);
            //插入内容
            setValue(o, cell);
        }
        exportObject.rowNum++;
    }

    /**
     * 构建业务数据错误报告excel信息
     *
     * @param exportObject          出口对象
     * @param sheet                 表
     * @param map                   地图
     * @param metaFieldImportResult 元领域导入结果
     */
    private static void thirdFeildExInfoBuild(ExportObject exportObject, SXSSFSheet sheet, Map<String, Object> map, MetaAndThirdImportResult metaFieldImportResult) {
        metaFeildExInfoBuild(exportObject, sheet, map, metaFieldImportResult);
    }

    /**
     * 构建元数据错误报告excel信息
     *
     * @param exportObject 出口对象
     * @param sheet        表
     */
    private static void metaFeildExInfoBuild(ExportObject exportObject, SXSSFSheet sheet,
                                             Map<String, Object> map, MetaAndThirdImportResult metaFieldImportResult) {
        if (exportObject.rowNum == 1) {//合并元数据模板第一行
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
        }
        // 获取行对象
        SXSSFRow row = sheet.createRow(exportObject.rowNum);
//        // 内容
        String[] objectKey = exportObject.getObjectKey();
        //获取错误数据map集合
        List<Map<String, Object>> failureList = metaFieldImportResult.getFailureList().get("meta");
        boolean isNeed = false;//是否需要渲染数据
        String misTakeStr = null;//错误信息字符串  方便判断时使用
        for (int i = 0; i < objectKey.length; i++) {
            SXSSFCell cell = row.createCell(i);
            // 处理数据
            if (map.size() > 0) {//map 不为0表示录入数据
                Object o = map.get(objectKey[i]);
//                if (o == null) {
//                    continue;
//                }
                //判断问题数据 并渲染数据 只在获取第一个cell时判断 因为第一个cell为idNUm
                if (i == 0) {
                    for (Map<String, Object> stringObjectMap : failureList) {
                        for (String s : stringObjectMap.keySet()) {
                            if (null == o || o.equals(s)) {
                                isNeed = true;
                                misTakeStr = stringObjectMap.get(s).toString();
                                break;
                            }
                        }
                    }
                }
                if (isNeed) {
                    StringBuffer cellTitleInfo = new StringBuffer();
                    String[] split = misTakeStr.split(";");
                    boolean isSetCellStyle = false;//此cell是都需要设置样式
                    if (split.length > 0) {
                        for (String str : split) {
                            //判断当前cell是否为错误字段
                            if (str.contains(objectKey[i])) {//包含错误字段内容 代表校验此字段出现错误 需要渲染cell
                                cellTitleInfo.append(str + ";");
                                isSetCellStyle = true;
                            }
                        }
                        if (isSetCellStyle) {//渲染此cell
                            cell.setCellStyle(exportObject.getErrorStyle() == null ? null : exportObject.getErrorStyle());
                            //设置批注
                            SXSSFDrawing drawingPatriarch = sheet.createDrawingPatriarch();//创建绘图
                            CellAddress address = cell.getAddress();
                            // 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
                            Comment cellComment = drawingPatriarch.createCellComment(new XSSFClientAnchor
                                    (0, 0, 0, 0, (short) address.getColumn(), address.getRow(), (short) address.getColumn(), address.getRow()));
                            cellComment.setString(new XSSFRichTextString(cellTitleInfo.toString()));
                            cell.setCellComment(cellComment);
                        }
                    }
                }
                setValue(o, cell);
            } else {//map为零表示 设置excel  样式模板类型
                cell.setCellStyle(exportObject.contentCellStyles[i]);
                setValue(objectKey[i], cell);
            }
        }
    }

    /**
     * 设置单元格值
     *
     * @param o    值对象
     * @param cell Excel单元格对象
     */
    private static void setValue(Object o, SXSSFCell cell) {
        // 根据类型处理数据
        if (null != o) {
            switch (o.getClass().getName()) {
                case "java.lang.Number":
                    cell.setCellValue(((Number) o).doubleValue());
                    break;
                case "java.util.Date":
                    cell.setCellValue((Date) o);
                    break;
//            case "java.time.LocalDateTime":
//                cell.setCellValue((LocalDateTime) o);
//                break;
//            case "java.time.LocalDate":
//                cell.setCellValue((LocalDate) o);
//                break;
                case "java.lang.Boolean":
                    cell.setCellValue((Boolean) o);
                    break;
                case "java.util.Calendar":
                    cell.setCellValue((Calendar) o);
                    break;
                default:
                    cell.setCellValue(new XSSFRichTextString(o.toString()));
            }
        }
    }

    /**
     * 业务对象设置单元格值
     *
     * @param o    值对象
     * @param cell Excel单元格对象
     */
    private static void setValueThird(Object o, XSSFCell cell) {
        // 根据类型处理数据
        if (null != o) {
            switch (o.getClass().getName()) {
                case "java.lang.Number":
                    cell.setCellValue(((Number) o).doubleValue());
                    break;
                case "java.util.Date":
                    cell.setCellValue((Date) o);
                    break;
                case "java.lang.Boolean":
                    cell.setCellValue((Boolean) o);
                    break;
                case "java.util.Calendar":
                    cell.setCellValue((Calendar) o);
                    break;
                default:
                    cell.setCellValue(new XSSFRichTextString(o.toString()));
            }
        }
    }

    /**
     * 获取当前写入行的sheet
     */
    private static SXSSFSheet getSheet() {
        checkRange();
        ExportObject exportObject = threadLocal.get();
        SXSSFWorkbook workbook = exportObject.workbook;

        if (exportObject.rowNum != 0) {
            return workbook.getSheetAt(exportObject.currentSheetIndex);
        }
        SXSSFSheet sheet = null;
        if (exportObject.currentSheetIndex != 0) {
            // 需要创建sheet
            sheet = workbook.createSheet();
        }
        if (exportObject.currentSheetIndex == 0) {
            // 第一个sheet直接获取
            if (workbook.getNumberOfSheets() == 0) {
                sheet = workbook.createSheet();
            } else {
                sheet = workbook.getSheetAt(0);
            }
        }

        // 设置列宽
        for (int i = 0; i < exportObject.colWidth.length; i++) {
            sheet.setColumnWidth(i, exportObject.colWidth[i]);
        }

        // 第一行写入头
        writeTitle();
        return sheet;
    }

    private static void checkRange() {
        ExportObject exportObject = threadLocal.get();
        if (exportObject.rowNum > exportObject.getSheetMaxRow()) {
            exportObject.rowNum = 0;
            exportObject.currentSheetIndex++;
        }
    }

    /**
     * 新sheet写入头
     */
    private static void writeTitle() {
        ExportObject exportObject = threadLocal.get();
        // 获取行对象
        SXSSFRow row = exportObject.getWorkbook()
                .getSheetAt(exportObject.currentSheetIndex)
                .createRow(exportObject.rowNum);
        // 表头
        String[] title = exportObject.getHeadTitle();

        for (int i = 0, titleLength = title.length; i < titleLength; i++) {
            SXSSFCell cell = row.createCell(i);
            cell.setCellStyle(exportObject.headCellStyles[i]);
            cell.setCellValue(title[i]);
        }
        exportObject.rowNum++;
    }

    /**
     * 导出中转对象
     */
    @Data
    @Accessors(chain = true)
    private static class ExportObject {
        /**
         * 导出Excel对象
         */
        private SXSSFWorkbook workbook;
        /**
         * 内容样式
         */
        private CellStyle[] contentCellStyles;
        /**
         * 行号
         */
        private int rowNum;
        /**
         * 样式头部
         */
        private CellStyle[] headCellStyles;
        /**
         * 头部标题
         */
        private String[] headTitle;
        /**
         * 导出对象的key
         */
        private String[] objectKey;
        /**
         * 列宽
         */
        private int[] colWidth;
        /**
         * 当前sheet
         */
        private int currentSheetIndex;
        /**
         * 单sheet最大行,默认100万
         */
        private int sheetMaxRow = 1000000;

        /**
         * 样式头部
         */
        private CellStyle errorStyle;
    }

    /**
     * @param workbook
     * @param sheetNum   (sheet的位置，0表示第一个表格中的第一个sheet)
     * @param sheetTitle （sheet的名称）
     * @param headers    （表格的标题）
     * @param result     （表格的数据）
     * @param out        （输出流）
     * @throws Exception
     * @Title: exportExcel
     * @Description: Excel的方法(导出多个sheet)
     * @author: evan @ 2021-04-19
     */
    public static void exportExcel(String explainName, XSSFWorkbook workbook, int sheetNum,
                                   String sheetTitle, String[] headers, List<List<String>> result,
                                   OutputStream out) {
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle);
        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth((short) 20);
        // 生成一个样式
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        // 生成一个字体
        XSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        // 把字体应用到当前的样式
        style.setFont(font);

        // 指定当单元格内容显示不下时自动换行
        style.setWrapText(true);
        // 填写说明
        Integer rowNum = 0;
        XSSFRow rowReportTitle = sheet.createRow(rowNum);
        Cell cell1 = rowReportTitle.createCell(0); // 0列
        // 设置值
        cell1.setCellValue(explainName);
        if (rowNum == 0) {//合并元数据模板第一行
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 25));
        }

        // 产生表格标题行
        XSSFRow row = sheet.createRow(1);
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell((short) i);

            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text.toString());
        }
        // 遍历集合数据，产生数据行
        if (result != null) {
            int index = 2;
            for (int i = 0; i < result.size(); i++) {
                row = sheet.createRow(index);
                int cellIndex = 0;
                for (int j = 0; j < result.get(i).size(); j++) {
                    XSSFCell cell = row.createCell((short) cellIndex);
                    cell.setCellValue(String.valueOf(result.get(i).get(j)));
                    cellIndex++;
                }
                index++;
            }
        }
    }

    /**
     * @param stream                      (输入流)
     * @param thirdFieldExcelimportCopies （错误数据）
     * @param metaFieldImportResult       （错误信息）
     * @Description: Excel的方法(业务对象导出错误报告)
     * @author: 2021-04-26
     */
    public static File writeExcel(InputStream stream, List<List<ThirdFieldExcelimportCopy>> thirdFieldExcelimportCopies, MetaAndThirdImportResult metaFieldImportResult) {
        File thirdFieldExFile = null;
        // 为不影响源文件,不直接读取文件
        InputStream is = new ByteArrayInputStream(IoUtil.readBytes(stream));
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(is);
            //每个sheet的开始行数
            int num = 2;
            int numMoney = 2;
            int numValue = 2;
            int numPercent = 2;
            int numText = 2;
            int numDt = 2;
            //用来判断是那个sheet下的数据
            int index = 0;
            for (List<ThirdFieldExcelimportCopy> errorThirdFieldList : thirdFieldExcelimportCopies) {
                index = index + 1;
                for (ThirdFieldExcelimportCopy errorThirdField : errorThirdFieldList) {
                    //获取模板中的6个sheet
                    XSSFSheet sheet = workbook.getSheetAt(0);
                    XSSFSheet sheetMoney = workbook.getSheetAt(1);
                    XSSFSheet sheetValue = workbook.getSheetAt(2);
                    XSSFSheet sheetPercent = workbook.getSheetAt(3);
                    XSSFSheet sheetText = workbook.getSheetAt(4);
                    XSSFSheet sheetDt = workbook.getSheetAt(5);
                    if (index == 2) {
                        // 获取行对象
                        XSSFRow row = sheetMoney.createRow(numMoney++);
                        String[] objectKey = new String[]{"idNum", "fieldName", "showNameCn", "showNameEn", "metadataObjName", "metadataFieldName", "isExtension", "extensionName", "isEmployMoney", "showThousands", "saveDecimalsPlaces", "unitTransformName", "unitShownameCn", "unitShownameEn", "unitShowlocationName", "moneyScopeMin", "moneyScopeMax", "defaultValueMoney", "isMustMoney", "isShowCurrency", "misTakeMsgMoney"};
                        //获取错误数据map集合
                        if (null != metaFieldImportResult) {
                            List<Map<String, Object>> failureList = metaFieldImportResult.getFailureList().get("金额");
                            //导出错误报告渲染数据及样式
                            renderData(failureList, errorThirdField, sheetMoney, row, objectKey);
                        } else {
                            setCorrectDate(errorThirdField, row, objectKey);
                        }

                    } else if (index == 3) {
                        // 获取行对象
                        XSSFRow row = sheetValue.createRow(numValue++);
                        // 内容
                        String[] objectKey = new String[]{"idNum", "fieldName", "showNameCn", "showNameEn", "metadataObjName", "metadataFieldName", "isExtension", "extensionName", "isEmployValue", "showThousandsValue", "saveDecimalsPlacesValue", "unitShownameCnValue", "unitShownameEnValue", "unitShowlocationNameValue", "defaultValueValue", "isMustValue", "multipleTransformValue", "numberScopeMinValue", "numberScopeMaxValue", "misTakeMsgValue"};
                        if (null != metaFieldImportResult) {
                            //获取错误数据map集合
                            List<Map<String, Object>> failureList = metaFieldImportResult.getFailureList().get("数值");
                            //导出错误报告渲染数据及样式
                            renderData(failureList, errorThirdField, sheetValue, row, objectKey);
                        } else {
                            setCorrectDate(errorThirdField, row, objectKey);
                        }
                    } else if (index == 4) {
                        // 获取行对象
                        XSSFRow row = sheetPercent.createRow(numPercent++);
                        // 内容
                        String[] objectKey = new String[]{"idNum", "fieldName", "showNameCn", "showNameEn", "metadataObjName", "metadataFieldName", "isExtension", "extensionName", "isEmployPercent", "inputNumberWayName", "saveDecimalsPlacesPercent", "numberScopeMin", "numberScopeMax", "defaultValuePercent", "isMustPercent", "misTakeMsgPercent"};
                        if (null != metaFieldImportResult) {
                            //获取错误数据map集合
                            List<Map<String, Object>> failureList = metaFieldImportResult.getFailureList().get("百分比");
                            //导出错误报告渲染数据及样式
                            renderData(failureList, errorThirdField, sheetPercent, row, objectKey);
                        } else {
                            setCorrectDate(errorThirdField, row, objectKey);
                        }
                    } else if (index == 5) {
                        // 获取行对象
                        XSSFRow row = sheetText.createRow(numText++);
                        // 内容
                        String[] objectKey = new String[]{"idNum", "fieldName", "showNameCn", "showNameEn", "metadataObjName", "metadataFieldName", "isExtension", "extensionName", "isEmployText", "lineTypeName", "maxNumberLimit", "formatValidataName", "defaultValueText", "isMustText", "misTakeMsgText"};
                        if (null != metaFieldImportResult) {
                            //获取错误数据map集合
                            List<Map<String, Object>> failureList = metaFieldImportResult.getFailureList().get("文本");
                            //导出错误报告渲染数据及样式
                            renderData(failureList, errorThirdField, sheetText, row, objectKey);
                        } else {
                            setCorrectDate(errorThirdField, row, objectKey);
                        }
                    } else if (index == 6) {
                        // 获取行对象
                        XSSFRow row = sheetDt.createRow(numDt++);
                        // 内容
                        String[] objectKey = new String[]{"idNum", "fieldName", "showNameCn", "showNameEn", "metadataObjName", "metadataFieldName", "isExtension", "extensionName", "isEmployDt", "isDate", "isTime", "dateFormatName", "timeFormatName", "startTimeScope", "endTimeScope", "isMustDt", "misTakeMsgDt"};
                        if (null != metaFieldImportResult) {
                            //获取错误数据map集合
                            List<Map<String, Object>> failureList = metaFieldImportResult.getFailureList().get("日期时间");
                            //导出错误报告渲染数据及样式
                            renderData(failureList, errorThirdField, sheetDt, row, objectKey);
                        } else {
                            setCorrectDate(errorThirdField, row, objectKey);
                        }
                    } else if (index == 1) {
                        // 获取行对象
                        XSSFRow row = sheet.createRow(num++);
                        // 内容
                        String[] objectKey = new String[]{"idNum", "fieldName", "fieldTypeName", "showNameCn", "showNameEn", "metadataObjName", "metadataFieldName", "isExtension", "extensionName", "misTakeMsg"};
                        if (null != metaFieldImportResult) {
                            //获取错误数据map集合
                            List<Map<String, Object>> failureList = metaFieldImportResult.getFailureList().get("thirdInfo");
                            //导出错误报告渲染数据及样式
                            renderData(failureList, errorThirdField, sheet, row, objectKey);
                        } else {
                            setCorrectDate(errorThirdField, row, objectKey);
                        }
                    }
                }
            }
            thirdFieldExFile = ExcelExportUtil.thirdStop(workbook);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return thirdFieldExFile;
    }

    //导出excel数据
    private static void setCorrectDate(ThirdFieldExcelimportCopy errorThirdField, XSSFRow row, String[] objectKey) {
        Map<String, Object> map = BeanUtil.beanToMap(errorThirdField);
        for (int j = 0; j < objectKey.length; j++) {
            XSSFCell cell = row.createCell(j);
            // 处理数据
            if (map.size() > 0) {//map 不为0表示录入数据
                //添加内容
                Object cellValue = map.get(objectKey[j]);
                //判断问题数据 并渲染数据 只在获取第一个cell时判断 因为第一个cell为idNUm
                setValueThird(cellValue, cell);
            }
        }
    }

    /**
     * @param failureList     (错误信息集合)
     * @param errorThirdField （每条数据）
     * @param sheet           （sheet）
     * @param row             （row）
     * @param objectKey
     * @Description: Excel的方法(导出错误报告渲染数据及样式)
     * @author: 2021-04-26
     */
    private static void renderData(List<Map<String, Object>> failureList, ThirdFieldExcelimportCopy errorThirdField, XSSFSheet sheet, XSSFRow row, String[] objectKey) {
        boolean isNeed = false;//是否需要渲染数据
        String misTakeStr = null;//错误信息字符串  方便判断时使用
        //将循环的数据转为map
        Map<String, Object> map = BeanUtil.beanToMap(errorThirdField);
        for (int j = 0; j < objectKey.length; j++) {
            XSSFCell cell = row.createCell(j);
            // 处理数据
            if (map.size() > 0) {//map 不为0表示录入数据
                //添加内容
                Object cellValue = map.get(objectKey[j]);
                //判断问题数据 并渲染数据 只在获取第一个cell时判断 因为第一个cell为idNUm
                if (j == 0) {
                    for (Map<String, Object> stringObjectMap : failureList) {
                        for (String s : stringObjectMap.keySet()) {
                            if (null == cellValue || cellValue.equals(s)) {
                                isNeed = true;
                                misTakeStr = stringObjectMap.get(s).toString();
                                break;
                            }
                        }
                    }
                }
                if (isNeed) {
                    StringBuffer cellTitleInfo = new StringBuffer();
                    String[] split = misTakeStr.split(";");
                    boolean isSetCellStyle = false;//此cell是都需要设置样式
                    if (split.length > 0) {
                        for (String str : split) {
                            //判断当前cell是否为错误字段
                            if (str.contains(objectKey[j])) {//包含错误字段内容 代表校验此字段出现错误 需要渲染cell
                                cellTitleInfo.append(str + ";");
                                isSetCellStyle = true;
                            }
                        }
                        if (isSetCellStyle) {//渲染此cell
                            cell.setCellStyle(sheet.getRow(1).getCell(31).getCellStyle() == null ? null : sheet.getRow(1).getCell(31).getCellStyle());
                            //设置批注
                            XSSFDrawing drawingPatriarch = sheet.createDrawingPatriarch();//创建绘图
                            CellAddress address = cell.getAddress();
                            // 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
                            Comment cellComment = drawingPatriarch.createCellComment(new XSSFClientAnchor
                                    (0, 0, 0, 0, (short) address.getColumn(), address.getRow(), (short) address.getColumn(), address.getRow()));
                            cellComment.setString(new XSSFRichTextString(cellTitleInfo.toString()));
                            cell.setCellComment(cellComment);
                        }
                    }
                    errorThirdField.setMisTakeMsg(cellTitleInfo.toString());
                }
                setValueThird(cellValue, cell);
            }
        }
    }
}