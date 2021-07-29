package hillinsight.metadata.api.portal.web.excel;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import focus.core.ResponseResult;
import hillinsight.acs.api.sdk.ApiAcsPermissionResult;
import hillinsight.acs.api.sdk.PermissionUtil;
import hillinsight.acs.api.sdk.UserInfoVo;
import hillinsight.metadata.api.dto.web.MetaAndThirdImportResult;
import hillinsight.metadata.api.dto.web.req.ThirdObjectExcelExportReq;
import hillinsight.metadata.api.models.vo.ThirdFieldExcelimportCopy;
import hillinsight.metadata.api.models.vo.ThirdFieldExcelimportVo;
import hillinsight.metadata.api.service.web.excel.ThirdExcelService;
import hillinsight.metadata.api.utils.convention.Constant;
import hillinsight.metadata.api.utils.excel.ExcelExportUtil;
import hillinsight.metadata.api.utils.excel.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.BuilderException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @ClassName ThirdExcelController
 * @Description TODO 业务数据excel导入  控制层
 * @Author wcy
 * @Date 2020/11/24
 * @Version 1.0
 */

@RestController
@RequestMapping(path = "/thirdExcel")
@Slf4j
public class ThirdExcelController {

    @Autowired
    private ThirdExcelService thirdExcelService;


    /**
     * 业务字段导入
     *
     * @param file       文件
     * @param request    请求
     * @param thirdObjId 业务对象id
     * @return {@link ResponseResult< MetaAndThirdImportResult >}* @throws Exception 异常
     */
    @RequestMapping(path = "/thirdExcelimport", method = RequestMethod.POST)
    public ResponseResult<MetaAndThirdImportResult> thirdExcelimport(@RequestParam("file") MultipartFile file,
                                                                     HttpServletRequest request, @RequestParam("thirdObjId") Integer thirdObjId,
                                                                     @RequestParam("isSaveDb") Integer isSaveDb) throws Exception {
        File filePath = FileUtils.multipartFileToFile(file);
        String[] sheet = new String[]{"thirdInfo", "金额", "数值", "百分比", "文本", "日期时间"};
        List<List<ThirdFieldExcelimportVo>> thirdFieldExcelimport = new ArrayList<>();//需要导入的excel数据
        for (String st : sheet) {
            //读取excel 多个sheet的信息
            ExcelReader reader = ExcelUtil.getReader(filePath, st);
            List<ThirdFieldExcelimportVo> thirdFieldExcelimportVos = reader.read(1, 2,
                    ThirdFieldExcelimportVo.class);
            thirdFieldExcelimport.add(thirdFieldExcelimportVos);
            ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
            UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
            for (ThirdFieldExcelimportVo thirdFieldExcelimportVo : thirdFieldExcelimportVos) {
                thirdFieldExcelimportVo.setCreatorId(userInfoVo.getUserCode());
                thirdFieldExcelimportVo.setCreatorName(userInfoVo.getUserName());
                thirdFieldExcelimportVo.setCreatorTime(new Date());
            }
        }
        //获取导入错误报告模板流文件
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("template/third.xls");
        if (null == stream) throw new BuilderException("获取错误报告模板异常！");
        return ResponseResult.success(thirdExcelService.thirdExcelimport(thirdFieldExcelimport, thirdObjId, stream, isSaveDb));
    }


    /**
     * 对象管理模板导出
     *
     * @param response 响应
     * @throws IOException ioexception
     */
    @RequestMapping(path = "/thirdExport", method = RequestMethod.GET)
    public void metatemplateExport(HttpServletResponse response) throws IOException {
        String filePath = "template/third.xls";
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(filePath);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + "thirdImportTemplate" + ".xls");
        ServletUtil.write(response, stream);
    }

    /**
     * 导出业务对象字段Excel
     *
     * @param thirdObjectExcelExportReq
     * @throws IOException ioexception
     */
    @RequestMapping(path = "/thirdExcelExport", method = RequestMethod.GET)
    public void thirdExcelExport(ThirdObjectExcelExportReq thirdObjectExcelExportReq, HttpServletResponse response) throws IOException {
        //获取导出模板流文件
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("template/third.xls");
        if (null == stream) throw new BuilderException("获取导出模板异常！");
        //根据id查询返回业务对象信息
        List<ThirdFieldExcelimportVo> thirdFieldList = thirdExcelService.getThirdDataInfoByIds(thirdObjectExcelExportReq);
        // 组装后的数据
        List<List<ThirdFieldExcelimportCopy>> data = new ArrayList<>();
        if (null != thirdFieldList) {
            extracted(thirdFieldList, data);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + "ThirdFieldList" + DateUtil.format(new Date(), "HHmmss") + ".xls");
        //组装多个sheet数据
        File file = ExcelExportUtil.writeExcel(stream, data, null);
        InputStream inputStream = new FileInputStream(file.getPath());
        //原理就是将所有的数据一起写入，然后再关闭输入流。
        ServletUtil.write(response, inputStream);
    }

    //组装多个sheet数据
    private void extracted(List<ThirdFieldExcelimportVo> thirdFieldList,List<List<ThirdFieldExcelimportCopy>> data) {
        //每个sheet的idNum
        int index = 1;
        int index1 = 1;
        int index2 = 1;
        int index3 = 1;
        int index4 = 1;
        int index5 = 1;
        //不同sheet的数据
        List<ThirdFieldExcelimportCopy> rowData = new ArrayList();
        List<ThirdFieldExcelimportCopy> rowData1 = new ArrayList();
        List<ThirdFieldExcelimportCopy> rowData2 = new ArrayList();
        List<ThirdFieldExcelimportCopy> rowData3 = new ArrayList();
        List<ThirdFieldExcelimportCopy> rowData4 = new ArrayList();
        List<ThirdFieldExcelimportCopy> rowData5 = new ArrayList();
        for (ThirdFieldExcelimportVo third : thirdFieldList) {
            //金额组装数据
            if (third.getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_MONEY)) {
                ThirdFieldExcelimportCopy thitdCopy = new ThirdFieldExcelimportCopy();
                thitdCopy.setIdNum(String.valueOf(index++));
                thitdCopy.setFieldName(third.getFieldName() == null ? "" : third.getFieldName());
                thitdCopy.setShowNameCn(third.getShowNameCn() == null ? "" : third.getShowNameCn());
                thitdCopy.setShowNameEn(third.getShowNameEn() == null ? "" : third.getShowNameEn());
                thitdCopy.setMetadataObjName(third.getMetadataObjName() == null ? "" : third.getMetadataObjName());
                thitdCopy.setMetadataFieldName(third.getMetadataFieldName() == null ? "" : third.getMetadataFieldName());
                thitdCopy.setIsExtension(third.getIsExtension() == null ? null : third.getIsExtension());
                thitdCopy.setExtensionName(third.getExtensionName() == null ? "" : third.getExtensionName());
                if (null != third.getFieldtypeExtendMoney()) {
                    thitdCopy.setShowThousands(third.getFieldtypeExtendMoney().getShowThousands() == null ? null : third.getFieldtypeExtendMoney().getShowThousands());
                    thitdCopy.setSaveDecimalsPlaces(third.getFieldtypeExtendMoney().getSaveDecimalsPlaces() == null ? null : third.getFieldtypeExtendMoney().getSaveDecimalsPlaces());
                    thitdCopy.setUnitTransformName(third.getFieldtypeExtendMoney().getUnitTransformName() == null ? "" : third.getFieldtypeExtendMoney().getUnitTransformName());
                    thitdCopy.setUnitShownameCn(third.getFieldtypeExtendMoney().getUnitShownameCn() == null ? "" : third.getFieldtypeExtendMoney().getUnitShownameCn());
                    thitdCopy.setUnitShownameEn(third.getFieldtypeExtendMoney().getUnitShownameEn() == null ? "" : third.getFieldtypeExtendMoney().getUnitShownameEn());
                    thitdCopy.setUnitShowlocationName(third.getFieldtypeExtendMoney().getUnitShowlocationName() == null ? "" : third.getFieldtypeExtendMoney().getUnitShowlocationName());
                    thitdCopy.setMoneyScopeMin(third.getFieldtypeExtendMoney().getMoneyScopeMin() == null ? "" : third.getFieldtypeExtendMoney().getMoneyScopeMin());
                    thitdCopy.setMoneyScopeMax(third.getFieldtypeExtendMoney().getMoneyScopeMax() == null ? "" : third.getFieldtypeExtendMoney().getMoneyScopeMax());
                    thitdCopy.setDefaultValueMoney(third.getFieldtypeExtendMoney().getDefaultValue() == null ? "" : third.getFieldtypeExtendMoney().getDefaultValue());
                    thitdCopy.setIsMustMoney(third.getFieldtypeExtendMoney().getIsMust() == null ? null : third.getFieldtypeExtendMoney().getIsMust());
                    thitdCopy.setIsEmployMoney(third.getFieldtypeExtendMoney().getIsEmploy() == null ? null : third.getFieldtypeExtendMoney().getIsEmploy());
                    thitdCopy.setIsShowCurrency(third.getFieldtypeExtendMoney().getIsShowCurrency() == null ? null : third.getFieldtypeExtendMoney().getIsShowCurrency());
                }
                rowData1.add(thitdCopy);
            } else if (third.getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_VALUE)) {
                //数值
                ThirdFieldExcelimportCopy thitdCopy = new ThirdFieldExcelimportCopy();
                thitdCopy.setIdNum(String.valueOf(index1++));
                thitdCopy.setFieldName(third.getFieldName() == null ? "" : third.getFieldName());
                thitdCopy.setShowNameCn(third.getShowNameCn() == null ? "" : third.getShowNameCn());
                thitdCopy.setShowNameEn(third.getShowNameEn() == null ? "" : third.getShowNameEn());
                thitdCopy.setMetadataObjName(third.getMetadataObjName() == null ? "" : third.getMetadataObjName());
                thitdCopy.setMetadataFieldName(third.getMetadataFieldName() == null ? "" : third.getMetadataFieldName());
                thitdCopy.setIsExtension(third.getIsExtension() == null ? null : third.getIsExtension());
                thitdCopy.setExtensionName(third.getExtensionName() == null ? "" : third.getExtensionName());
                if (null != third.getFieldtypeExtendValue()) {
                    thitdCopy.setShowThousandsValue(third.getFieldtypeExtendValue().getShowThousands() == null ? null : third.getFieldtypeExtendValue().getShowThousands());
                    thitdCopy.setSaveDecimalsPlacesValue(third.getFieldtypeExtendValue().getSaveDecimalsPlaces() == null ? null : third.getFieldtypeExtendValue().getSaveDecimalsPlaces());
                    thitdCopy.setUnitShownameCnValue(third.getFieldtypeExtendValue().getUnitShownameCn() == null ? "" : third.getFieldtypeExtendValue().getUnitShownameCn());
                    thitdCopy.setUnitShownameEnValue(third.getFieldtypeExtendValue().getUnitShownameEn() == null ? "" : third.getFieldtypeExtendValue().getUnitShownameEn());
                    thitdCopy.setUnitShowlocationNameValue(third.getFieldtypeExtendValue().getUnitShowlocationName() == null ? "" : third.getFieldtypeExtendValue().getUnitShowlocationName());
                    thitdCopy.setDefaultValueValue(third.getFieldtypeExtendValue().getDefaultValue() == null ? "" : third.getFieldtypeExtendValue().getDefaultValue());
                    thitdCopy.setIsMustValue(third.getFieldtypeExtendValue().getIsMust() == null ? null : third.getFieldtypeExtendValue().getIsMust());
                    thitdCopy.setIsEmployValue(third.getFieldtypeExtendValue().getIsEmploy() == null ? null : third.getFieldtypeExtendValue().getIsEmploy());
                    thitdCopy.setMultipleTransformValue(third.getFieldtypeExtendValue().getMultipleTransform() == null ? "" : third.getFieldtypeExtendValue().getMultipleTransform());
                    thitdCopy.setNumberScopeMinValue(third.getFieldtypeExtendValue().getNumberScopeMin() == null ? "" : third.getFieldtypeExtendValue().getNumberScopeMin());
                    thitdCopy.setNumberScopeMaxValue(third.getFieldtypeExtendValue().getNumberScopeMax() == null ? "" : third.getFieldtypeExtendValue().getNumberScopeMax());
                }
                rowData2.add(thitdCopy);
            } else if (third.getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_PERCENT)) {
                ThirdFieldExcelimportCopy thitdCopy = new ThirdFieldExcelimportCopy();
                //百分比
                thitdCopy.setIdNum(String.valueOf(index2++));
                thitdCopy.setFieldName(third.getFieldName() == null ? "" : third.getFieldName());
                thitdCopy.setShowNameCn(third.getShowNameCn() == null ? "" : third.getShowNameCn());
                thitdCopy.setShowNameEn(third.getShowNameEn() == null ? "" : third.getShowNameEn());
                thitdCopy.setMetadataObjName(third.getMetadataObjName() == null ? "" : third.getMetadataObjName());
                thitdCopy.setMetadataFieldName(third.getMetadataFieldName() == null ? "" : third.getMetadataFieldName());
                thitdCopy.setIsExtension(third.getIsExtension() == null ? null : third.getIsExtension());
                thitdCopy.setExtensionName(third.getExtensionName() == null ? "" : third.getExtensionName());
                if (null != third.getFieldtypeExtendPercent()) {
                    thitdCopy.setInputNumberWayName(third.getFieldtypeExtendPercent().getInputNumberWayName() == null ? "" : third.getFieldtypeExtendPercent().getInputNumberWayName());
                    thitdCopy.setSaveDecimalsPlacesPercent(third.getFieldtypeExtendPercent().getSaveDecimalsPlaces() == null ? null : third.getFieldtypeExtendPercent().getSaveDecimalsPlaces());
                    thitdCopy.setNumberScopeMin(third.getFieldtypeExtendPercent().getNumberScopeMin() == null ? "" : third.getFieldtypeExtendPercent().getNumberScopeMin());
                    thitdCopy.setNumberScopeMax(third.getFieldtypeExtendPercent().getNumberScopeMax() == null ? "" : third.getFieldtypeExtendPercent().getNumberScopeMax());
                    thitdCopy.setDefaultValuePercent(third.getFieldtypeExtendPercent().getDefaultValue() == null ? "" : third.getFieldtypeExtendPercent().getDefaultValue());
                    thitdCopy.setIsMustPercent(third.getFieldtypeExtendPercent().getIsMust() == null ? null : third.getFieldtypeExtendPercent().getIsMust());
                    thitdCopy.setIsEmployPercent(third.getFieldtypeExtendPercent().getIsEmploy() == null ? null : third.getFieldtypeExtendPercent().getIsEmploy());
                }
                rowData3.add(thitdCopy);
            } else if (third.getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_TEXT)) {
                ThirdFieldExcelimportCopy thitdCopy = new ThirdFieldExcelimportCopy();
                //文本
                thitdCopy.setIdNum(String.valueOf(index3++));
                thitdCopy.setFieldName(third.getFieldName() == null ? "" : third.getFieldName());
                thitdCopy.setShowNameCn(third.getShowNameCn() == null ? "" : third.getShowNameCn());
                thitdCopy.setShowNameEn(third.getShowNameEn() == null ? "" : third.getShowNameEn());
                thitdCopy.setMetadataObjName(third.getMetadataObjName() == null ? "" : third.getMetadataObjName());
                thitdCopy.setMetadataFieldName(third.getMetadataFieldName() == null ? "" : third.getMetadataFieldName());
                thitdCopy.setIsExtension(third.getIsExtension() == null ? null : third.getIsExtension());
                thitdCopy.setExtensionName(third.getExtensionName() == null ? "" : third.getExtensionName());
                if (null != third.getFieldtypeExtendText()) {
                    thitdCopy.setLineTypeName(third.getFieldtypeExtendText().getLineTypeName() == null ? "" : third.getFieldtypeExtendText().getLineTypeName());
                    thitdCopy.setMaxNumberLimit(third.getFieldtypeExtendText().getMaxNumberLimit() == null ? "" : third.getFieldtypeExtendText().getMaxNumberLimit());
                    thitdCopy.setFormatValidataName(third.getFieldtypeExtendText().getFormatValidataName() == null ? "" : third.getFieldtypeExtendText().getFormatValidataName());
                    thitdCopy.setDefaultValueText(third.getFieldtypeExtendText().getDefaultValue() == null ? "" : third.getFieldtypeExtendText().getDefaultValue());
                    thitdCopy.setIsMustText(third.getFieldtypeExtendText().getIsMust() == null ? null : third.getFieldtypeExtendText().getIsMust());
                    thitdCopy.setIsEmployText(third.getFieldtypeExtendText().getIsEmploy() == null ? null : third.getFieldtypeExtendText().getIsEmploy());
                }
                rowData4.add(thitdCopy);
            } else if (third.getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_DT)) {
                ThirdFieldExcelimportCopy thitdCopy = new ThirdFieldExcelimportCopy();
                //时间/日期
                thitdCopy.setIdNum(String.valueOf(index4++));
                thitdCopy.setFieldName(third.getFieldName() == null ? "" : third.getFieldName());
                thitdCopy.setShowNameCn(third.getShowNameCn() == null ? "" : third.getShowNameCn());
                thitdCopy.setShowNameEn(third.getShowNameEn() == null ? "" : third.getShowNameEn());
                thitdCopy.setMetadataObjName(third.getMetadataObjName() == null ? "" : third.getMetadataObjName());
                thitdCopy.setMetadataFieldName(third.getMetadataFieldName() == null ? "" : third.getMetadataFieldName());
                thitdCopy.setIsExtension(third.getIsExtension() == null ? null : third.getIsExtension());
                thitdCopy.setExtensionName(third.getExtensionName() == null ? "" : third.getExtensionName());
                if (null != third.getFieldtypeExtendDt()) {
                    thitdCopy.setIsDate(third.getFieldtypeExtendDt().getIsDate() == null ? null : third.getFieldtypeExtendDt().getIsDate());
                    thitdCopy.setIsTime(third.getFieldtypeExtendDt().getIsTime() == null ? null : third.getFieldtypeExtendDt().getIsTime());
                    thitdCopy.setDateFormatName(third.getFieldtypeExtendDt().getDateFormatName() == null ? "" : third.getFieldtypeExtendDt().getDateFormatName());
                    thitdCopy.setTimeFormatName(third.getFieldtypeExtendDt().getTimeFormatName() == null ? "" : third.getFieldtypeExtendDt().getTimeFormatName());
                    thitdCopy.setStartTimeScope(third.getFieldtypeExtendDt().getStartTimeScope() == null ? "" : third.getFieldtypeExtendDt().getStartTimeScope());
                    thitdCopy.setEndTimeScope(third.getFieldtypeExtendDt().getEndTimeScope() == null ? "" : third.getFieldtypeExtendDt().getEndTimeScope());
                    thitdCopy.setIsMustDt(third.getFieldtypeExtendDt().getIsMust() == null ? null : third.getFieldtypeExtendDt().getIsMust());
                    thitdCopy.setIsEmployDt(third.getFieldtypeExtendDt().getIsEmploy() == null ? null : third.getFieldtypeExtendDt().getIsEmploy());
                    //2.0版本没有加
//                  rowData5.add(third.getFieldtypeExtendDt().getIsWeek() == null ? "" : third.getFieldtypeExtendDt().getIsWeek());
//                  rowData5.add(third.getFieldtypeExtendDt().getWeekFormatName() == null ? "" : third.getFieldtypeExtendDt().getWeekFormatName());

                }
                rowData5.add(thitdCopy);
            } else {
                //除了有高级配置以外的字段
                ThirdFieldExcelimportCopy thitdCopy = new ThirdFieldExcelimportCopy();
                thitdCopy.setIdNum(String.valueOf(index5++));
                thitdCopy.setFieldName(third.getFieldName() == null ? "" : third.getFieldName());
                thitdCopy.setFieldTypeName(third.getFieldTypeName() == null ? "" : third.getFieldTypeName());
                thitdCopy.setShowNameCn(third.getShowNameCn() == null ? "" : third.getShowNameCn());
                thitdCopy.setShowNameEn(third.getShowNameEn() == null ? "" : third.getShowNameEn());
                thitdCopy.setMetadataObjName(third.getMetadataObjName() == null ? "" : third.getMetadataObjName());
                thitdCopy.setMetadataFieldName(third.getMetadataFieldName() == null ? "" : third.getMetadataFieldName());
                thitdCopy.setIsExtension(third.getIsExtension() == null ? null : third.getIsExtension());
                thitdCopy.setExtensionName(third.getExtensionName() == null ? "" : third.getExtensionName());
                rowData.add(thitdCopy);
            }
        }
        data.add(0,rowData);
        data.add(1,rowData1);
        data.add(2,rowData2);
        data.add(3,rowData3);
        data.add(4,rowData4);
        data.add(5,rowData5);
    }

    /**
     * 下载业务字段导入的错误报告
     *
     * @param response 响应
     * @throws IOException ioexception
     */
    @RequestMapping(path = "/getThirdFieldImportDis", method = RequestMethod.GET)
    public void getThirdFieldImportDis(HttpServletResponse response, @RequestParam(value = "fieldId") String fieldId) throws IOException {
        log.error("文件id入参："+fieldId+"************************************");
        InputStream thirdFieldImportDis = thirdExcelService.getThirdFieldImportDis(fieldId);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + "thirdErrorReport" + ".xls");
        ServletUtil.write(response, thirdFieldImportDis);
    }
}
