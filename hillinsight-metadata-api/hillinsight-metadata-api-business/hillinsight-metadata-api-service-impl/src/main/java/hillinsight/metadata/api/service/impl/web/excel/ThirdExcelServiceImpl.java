package hillinsight.metadata.api.service.impl.web.excel;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import hillinsight.metadata.api.dao.web.FieldTypeExtendDao;
import hillinsight.metadata.api.dao.web.MetaDataInfoDao;
import hillinsight.metadata.api.dao.web.ThirdInfoDao;
import hillinsight.metadata.api.dto.web.MetaAndThirdImportResult;
import hillinsight.metadata.api.dto.web.req.MetaDataDictReq;
import hillinsight.metadata.api.dto.web.ThirdAddOrUpdFieReq;
import hillinsight.metadata.api.dto.web.req.ThirdObjectExcelExportReq;
import hillinsight.metadata.api.models.MetadataDict;
import hillinsight.metadata.api.models.vo.ThirdFieldExcelimportCopy;
import hillinsight.metadata.api.models.vo.ThirdFieldExcelimportVo;
import hillinsight.metadata.api.service.dict.MetaDataDictService;
import hillinsight.metadata.api.service.web.ThirdInfoService;
import hillinsight.metadata.api.service.web.excel.ThirdExcelService;
import hillinsight.metadata.api.utils.convention.Constant;
import hillinsight.metadata.api.utils.convention.PageTransferUtils;
import hillinsight.metadata.api.utils.convention.RegularUtils;
import hillinsight.metadata.api.utils.convention.UniqueUtils;
import hillinsight.metadata.api.utils.excel.ExcelExportUtil;
import hillinsight.metadata.api.utils.excel.FileUtils;
import hillinsight.metadata.api.utils.s3.AwsS3Util;
import hillinsight.metadata.api.utils.s3.S3Config;
import hillinsight.metadata.api.web.*;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ClassName ThirdExcelServiceImpl
 * @Description TODO
 * @Author wcy
 * @Date 2020/11/24
 * @Version 1.0
 */

@Service
public class ThirdExcelServiceImpl implements ThirdExcelService {


    @Autowired
    private ThirdInfoDao thirdInfoDao;

    @Autowired
    private MetaDataDictService metaDataDictService;

    @Autowired
    private MetaDataInfoDao metaDataInfoDao;

    @Autowired
    private ThirdInfoService thirdInfoService;

    @Autowired
    private S3Config s3Config;

    @Autowired
    private FieldTypeExtendDao fieldTypeExtendDao;

    /**
     * 业务字段导入
     *
     * @param thirdFieldExcelimportVoslist 业务字段导入vo
     * @param thirdObjId               业务对象id
     * @return {@link MetaAndThirdImportResult}
     */
    @Override
    public MetaAndThirdImportResult thirdExcelimport(List<List<ThirdFieldExcelimportVo>> thirdFieldExcelimportVoslist,
                                                     Integer thirdObjId, InputStream stream, Integer isSaveDb) {
        if (null == thirdObjId) throw new BuilderException("对象id不能为空!");
        else if (null == isSaveDb) throw new BuilderException("导入状态不能为空");
        MetaAndThirdImportResult metaFieldImportResult = new MetaAndThirdImportResult();
        //判断导入信息是否没有错误数据 有则返回错误报告 没有则成功
        List<ThirdFieldExcelimportVo> vos = checkoutThirdExcelInfo(metaFieldImportResult, thirdFieldExcelimportVoslist, thirdObjId);
        if (null != metaFieldImportResult.getTotalCount() &&
                null != metaFieldImportResult.getSuccessCount() &&
                metaFieldImportResult.getTotalCount() == metaFieldImportResult.getSuccessCount()) {
            if (isSaveDb == 2) {
                for (ThirdFieldExcelimportVo vo : vos) {
                    if (null != vo.getThirdFieldInfo()) {
                        //根据sheet值插入各个扩展表
                        ThirdAddOrUpdFieReq thirdAddOrUpdFieReq = getThirdAddOrUpdFieReq(vo);
                        thirdInfoService.addOrUpdateThirdField(thirdAddOrUpdFieReq);
                    }
                }
            }
            metaFieldImportResult.setExSuccess(true);
        } else {
            //复制业务字段导入实体
            List<List<ThirdFieldExcelimportCopy>> thirdFieldExcelimportCopies = copyThirdFieldExInfo(thirdFieldExcelimportVoslist, metaFieldImportResult);
            //校验出错误数据  生成错误报告  存入文件服务器
            File thirdFieldExFile = ExcelExportUtil.writeExcel(stream, thirdFieldExcelimportCopies, metaFieldImportResult);
            //TODO 将导出文件放入到文件服务器中
            String fileKey = s3Config.getUploadKey() + UniqueUtils.getUUID();
            if (!AwsS3Util.uploadFileToS3(
                    s3Config.getBucket(), fileKey, FileUtils.getBytesByFile(thirdFieldExFile)))
                throw new BuilderException("上传失败");
            metaFieldImportResult.setFieldId(fileKey);//返回给前端 文件存储id
        }
        return metaFieldImportResult;
    }

    /**
     * 根据sheet值插入对应各个扩展表
     * @param vo 业务字段导入vo
     * @return {@link MetaAndThirdImportResult}
     */
    private ThirdAddOrUpdFieReq getThirdAddOrUpdFieReq(ThirdFieldExcelimportVo vo) {
        ThirdAddOrUpdFieReq thirdAddOrUpdFieReq = new ThirdAddOrUpdFieReq();
        //业务对象字段
        thirdAddOrUpdFieReq.setThirdFieldInfo(vo.getThirdFieldInfo());
        //金额  sheet字段名称与对象字段名称不一样的进行赋值
        vo.getFieldtypeExtendMoney().setDefaultValue(vo.getFieldtypeExtendMoney().getDefaultValueMoney());
        vo.getFieldtypeExtendMoney().setIsMust(vo.getFieldtypeExtendMoney().getIsMustMoney());
        vo.getFieldtypeExtendMoney().setIsEmploy(vo.getFieldtypeExtendMoney().getIsEmployMoney());
        vo.getFieldtypeExtendMoney().setFieldId(vo.getFieldtypeExtendMoney().getFieldIdMoney());
        vo.getFieldtypeExtendMoney().setIsShowCurrency(vo.getFieldtypeExtendMoney().getIsShowCurrency());
        thirdAddOrUpdFieReq.setFieldtypeExtendMoney(vo.getFieldtypeExtendMoney());

        //数值  sheet字段名称与对象字段名称不一样的进行赋值
        vo.getFieldtypeExtendValue().setShowThousands(vo.getFieldtypeExtendValue().getShowThousandsValue());
        vo.getFieldtypeExtendValue().setSaveDecimalsPlaces(vo.getFieldtypeExtendValue().getSaveDecimalsPlacesValue());
        vo.getFieldtypeExtendValue().setUnitShownameCn(vo.getFieldtypeExtendValue().getUnitShownameCnValue());
        vo.getFieldtypeExtendValue().setUnitShownameEn(vo.getFieldtypeExtendValue().getUnitShownameEnValue());
        vo.getFieldtypeExtendValue().setUnitShowlocationName(vo.getFieldtypeExtendValue().getUnitShowlocationNameValue());
        vo.getFieldtypeExtendValue().setDefaultValue(vo.getFieldtypeExtendValue().getDefaultValueValue());
        vo.getFieldtypeExtendValue().setIsMust(vo.getFieldtypeExtendValue().getIsMustValue());
        vo.getFieldtypeExtendValue().setIsEmploy(vo.getFieldtypeExtendValue().getIsEmployValue());
        vo.getFieldtypeExtendValue().setMultipleTransform(vo.getFieldtypeExtendValue().getMultipleTransformValue());
        vo.getFieldtypeExtendValue().setNumberScopeMin(vo.getFieldtypeExtendValue().getNumberScopeMinValue());
        vo.getFieldtypeExtendValue().setNumberScopeMax(vo.getFieldtypeExtendValue().getNumberScopeMaxValue());
        vo.getFieldtypeExtendValue().setFieldId(vo.getFieldtypeExtendValue().getFieldIdValue());
        thirdAddOrUpdFieReq.setFieldtypeExtendValue(vo.getFieldtypeExtendValue());

        //百分比 sheet字段名称与对象字段名称不一样的进行赋值
        vo.getFieldtypeExtendPercent().setSaveDecimalsPlaces(vo.getFieldtypeExtendPercent().getSaveDecimalsPlacesPercent());
        vo.getFieldtypeExtendPercent().setDefaultValue(vo.getFieldtypeExtendPercent().getDefaultValuePercent());
        vo.getFieldtypeExtendPercent().setIsMust(vo.getFieldtypeExtendPercent().getIsMustPercent());
        vo.getFieldtypeExtendPercent().setIsEmploy(vo.getFieldtypeExtendPercent().getIsEmployPercent());
        vo.getFieldtypeExtendPercent().setFieldId(vo.getFieldtypeExtendPercent().getFieldIdPercent());
        thirdAddOrUpdFieReq.setFieldtypeExtendPercent(vo.getFieldtypeExtendPercent());

        //文本 sheet字段名称与对象字段名称不一样的进行赋值
        vo.getFieldtypeExtendText().setDefaultValue(vo.getFieldtypeExtendText().getDefaultValueText());
        vo.getFieldtypeExtendText().setIsMust(vo.getFieldtypeExtendText().getIsMustText());
        vo.getFieldtypeExtendText().setIsEmploy(vo.getFieldtypeExtendText().getIsEmployText());
        vo.getFieldtypeExtendText().setFieldId(vo.getFieldtypeExtendText().getFieldIdText());
        thirdAddOrUpdFieReq.setFieldtypeExtendText(vo.getFieldtypeExtendText());

        //时间 sheet字段名称与对象字段名称不一样的进行赋值
        vo.getFieldtypeExtendDt().setIsMust(vo.getFieldtypeExtendDt().getIsMustDt());
        vo.getFieldtypeExtendDt().setIsEmploy(vo.getFieldtypeExtendDt().getIsEmployDt());
        vo.getFieldtypeExtendDt().setFieldId(vo.getFieldtypeExtendDt().getFieldIdDt());
        thirdAddOrUpdFieReq.setFieldtypeExtendDt(vo.getFieldtypeExtendDt());
        return thirdAddOrUpdFieReq;
    }


    /**
     * 复制业务字段导入实体
     *
     * @param thirdFieldExcelimportVos 第三场excelimport vos
     * @param metaFieldImportResult    元领域导入结果
     * @return {@link List<ThirdFieldExcelimportCopy>}
     */
    private List<List<ThirdFieldExcelimportCopy>> copyThirdFieldExInfo(List<List<ThirdFieldExcelimportVo>> thirdFieldExcelimportVos, MetaAndThirdImportResult metaFieldImportResult) {
        Map<String, List<Map<String, Object>>> failureList = metaFieldImportResult.getFailureList();//错误集合
        List<List<ThirdFieldExcelimportCopy>> thirdFieldExcelimportCopiesList = new ArrayList<>();//copy为List套list
        for (List<ThirdFieldExcelimportVo> thirdFieldExcelimportVoList : thirdFieldExcelimportVos) {
            List<ThirdFieldExcelimportCopy> thirdFieldExcelimportCopies = new ArrayList<>();
            for (ThirdFieldExcelimportVo thirdFieldExcelimportVo : thirdFieldExcelimportVoList) {
                ThirdFieldExcelimportCopy thirdFieldExcelimportCopy = new ThirdFieldExcelimportCopy();
                BeanUtils.copyProperties(thirdFieldExcelimportVo,thirdFieldExcelimportCopy);
                //向本次循环copy的对象中插入错误信息
                insertErrorMsg(failureList, thirdFieldExcelimportVo);
                thirdFieldExcelimportCopies.add(thirdFieldExcelimportCopy);
            }
            thirdFieldExcelimportCopiesList.add(thirdFieldExcelimportCopies);
        }
        return thirdFieldExcelimportCopiesList;
    }

    /**
     * 向循环copy的对象中插入错误信息
     *
     * @param failureList 错误集合
     * @param thirdFieldExcelimportVo
     */
    private void insertErrorMsg(Map<String, List<Map<String, Object>>> failureList, ThirdFieldExcelimportVo thirdFieldExcelimportVo) {
        if (StrUtil.isEmpty(thirdFieldExcelimportVo.getIdNum())) {//如果idNUm为空表示  idnum错误 直接插入
            thirdFieldExcelimportVo.setMisTakeMsg("idNum 为空值;");
        } else {
            for (Map<String, Object> map: failureList.get("thirdInfo")) {
                for (String s : map.keySet()) {
                    if (thirdFieldExcelimportVo.getIdNum().equals(s)) {//如果copy对象中的idNUm 可以匹配上错误集合中的map的key值  插入错误信息
                        thirdFieldExcelimportVo.setMisTakeMsg(map.get(s).toString());
                    }
                }
            }
            for (Map<String, Object> map : failureList.get("金额")) {
                for (String s : map.keySet()) {
                    if (thirdFieldExcelimportVo.getIdNum().equals(s)) {//如果copy对象中的idNUm 可以匹配上错误集合中的map的key值  插入错误信息
                        thirdFieldExcelimportVo.setMisTakeMsgMoney(map.get(s).toString());
                    }
                }
            }
            for (Map<String, Object> map : failureList.get("数值")) {
                for (String s : map.keySet()) {
                    if (thirdFieldExcelimportVo.getIdNum().equals(s)) {//如果copy对象中的idNUm 可以匹配上错误集合中的map的key值  插入错误信息
                        thirdFieldExcelimportVo.setMisTakeMsgValue(map.get(s).toString());
                    }
                }
            }
            for (Map<String, Object> map : failureList.get("百分比")) {
                for (String s : map.keySet()) {
                    if (thirdFieldExcelimportVo.getIdNum().equals(s)) {//如果copy对象中的idNUm 可以匹配上错误集合中的map的key值  插入错误信息
                        thirdFieldExcelimportVo.setMisTakeMsgPercent(map.get(s).toString());
                    }
                }
            }
            for (Map<String, Object> map : failureList.get("文本")) {
                for (String s : map.keySet()) {
                    if (thirdFieldExcelimportVo.getIdNum().equals(s)) {//如果copy对象中的idNUm 可以匹配上错误集合中的map的key值  插入错误信息
                        thirdFieldExcelimportVo.setMisTakeMsgText(map.get(s).toString());
                    }
                }
            }
            for (Map<String, Object> map : failureList.get("日期时间")) {
                for (String s : map.keySet()) {
                    if (thirdFieldExcelimportVo.getIdNum().equals(s)) {//如果copy对象中的idNUm 可以匹配上错误集合中的map的key值  插入错误信息
                        thirdFieldExcelimportVo.setMisTakeMsgDt(map.get(s).toString());
                    }
                }
            }
        }
    }

    /**
     * 校验业务字段 模板导入信息
     *
     * @param metaFieldImportResult    元领域导入结果
     * @param thirdFieldExcelimportVos 第三场excelimport vos
     * @return {@link List<ThirdFieldInfo>}
     */
    private List<ThirdFieldExcelimportVo> checkoutThirdExcelInfo(MetaAndThirdImportResult metaFieldImportResult, List<List<ThirdFieldExcelimportVo>> thirdFieldExcelimportVos,
                                                                 Integer thirdObjId) {
        List<ThirdFieldExcelimportVo> vos = new ArrayList<>();//验证通过数据 插入数据库集合
        //失败返回错误信息集合(包括所有sheet)
        Map<String, List<Map<String, Object>>>  failureMap = new HashMap<>();
        List<Map<String, Object>> failureList = new ArrayList<>();//失败返回错误信息集合
        List<Map<String, Object>> failureListMoney = new ArrayList<>();//失败返回错误信息集合 金额
        List<Map<String, Object>> failureListValue = new ArrayList<>();//失败返回错误信息集合 数值
        List<Map<String, Object>> failureListPercent = new ArrayList<>();//失败返回错误信息集合 百分比
        List<Map<String, Object>> failureListText = new ArrayList<>();//失败返回错误信息集合 文本
        List<Map<String, Object>> failureListDt = new ArrayList<>();//失败返回错误信息集合  日期时间
        //存储idNum的map
        Map<String, ThirdFieldExcelimportVo> idNumMap = new HashMap<>();
        Map<String, ThirdFieldExcelimportVo> idNumMapMoney = new HashMap<>();
        Map<String, ThirdFieldExcelimportVo> idNumMapValue = new HashMap<>();
        Map<String, ThirdFieldExcelimportVo> idNumMapPercent = new HashMap<>();
        Map<String, ThirdFieldExcelimportVo> idNumMapText = new HashMap<>();
        Map<String, ThirdFieldExcelimportVo> idNumMapDt = new HashMap<>();
        Integer thirdVoListSize = 0;//导入总条数
        Integer successCount = 0;//导入成功条数
        //所有的数据放入一个list用于校验
        List<ThirdFieldExcelimportVo> list = new ArrayList<>();

        //代表是那个sheet下的数据
        int index = 0;
        for (List<ThirdFieldExcelimportVo> thirdVoList : thirdFieldExcelimportVos) {
            //4元数据字段名校验查看是否存在（校验excel中的数据）
            list.addAll(thirdVoList);
            List<ThirdFieldExcelimportVo> repeatList = new ArrayList<>();//用于存放重复的元素的list
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = list.size() - 1; j > i; j--) {
                    if (list.get(j).getMetadataObjName().equals(list.get(i).getMetadataObjName()) && list.get(j).getMetadataFieldName().equals(list.get(i).getMetadataFieldName())) {
                        if(null != list.get(j)){
                            repeatList.add(list.get(j));//把相同元素加入list(找出相同的)
                        }
                    }
                }
            }
            if (!com.alibaba.excel.util.CollectionUtils.isEmpty(repeatList)) {
                throw new BuilderException("metadataFieldName 该元数据字段名称在excel中只能绑定一次");
            }
            index = index+1;
            if (thirdVoList.size() != 0) {
                for (ThirdFieldExcelimportVo thirdVo : thirdVoList) {
                    ThirdFieldExcelimportVo vo = new ThirdFieldExcelimportVo();//验证通过数据 插入数据库集合
                    //set字段类型
                    if (index == 2) {
                        thirdVo.setFieldTypeName("金额");
                    } else if (index == 3) {
                        thirdVo.setFieldTypeName("数值");
                    } else if (index == 4) {
                        thirdVo.setFieldTypeName("百分比");
                    } else if (index == 5) {
                        thirdVo.setFieldTypeName("文本");
                    } else if (index == 6) {
                        thirdVo.setFieldTypeName("日期时间");
                    }

                    //通过字典路径查询是否有该字典值
                    MetaDataDictReq metaDataDictReq = new MetaDataDictReq();
                    metaDataDictReq.setDictPath(Constant.UNIT_SHOW_LOCATIONINPUT);
                    Map<String, List<MetadataDict>> dictValue = metaDataDictService.getFTExpertConfigDictListByPath(metaDataDictReq);

                    //通过字典路径查询是否有该字典值
                    MetaDataDictReq reqValidata = new MetaDataDictReq();
                    reqValidata.setDictPath(Constant.FORMAT_VALIDATA);
                    Map<String, List<MetadataDict>> dictValueValidata = metaDataDictService.getFTExpertConfigDictListByPath(reqValidata);

                    //通过字典路径查询是否有该字典值
                    MetaDataDictReq unitTransformReq = new MetaDataDictReq();
                    unitTransformReq.setDictPath(Constant.UNIT_TRANSFORM);
                    Map<String, List<MetadataDict>> unitTransformValue = metaDataDictService.getFTExpertConfigDictListByPath(unitTransformReq);

                    //通过字典路径查询是否有该字典值
                    MetaDataDictReq numberWayReq = new MetaDataDictReq();
                    numberWayReq.setDictPath(Constant.INPUT_NUMBER_WAY);
                    Map<String, List<MetadataDict>> numberWayValue = metaDataDictService.getFTExpertConfigDictListByPath(numberWayReq);

                    //通过字典路径查询是否有该字典值
                    MetaDataDictReq lineTypeReq = new MetaDataDictReq();
                    lineTypeReq.setDictPath(Constant.LINE_TYPE);
                    Map<String, List<MetadataDict>> lineTypeValue = metaDataDictService.getFTExpertConfigDictListByPath(lineTypeReq);

                    //通过字典路径查询是否有该字典值
                    MetaDataDictReq dateFormatReq = new MetaDataDictReq();
                    dateFormatReq.setDictPath(Constant.DATETIMEFORMAT);
                    Map<String, List<MetadataDict>> dateFormatValue = metaDataDictService.getFTExpertConfigDictListByPath(dateFormatReq);

                    //通过字典路径查询是否有该字典值
                    MetaDataDictReq timeFormatReq = new MetaDataDictReq();
                    timeFormatReq.setDictPath(Constant.TIMEFORMAT);
                    Map<String, List<MetadataDict>> timeFormatValue = metaDataDictService.getFTExpertConfigDictListByPath(timeFormatReq);

                    //通过字典路径查询是否有该字典值
                    MetaDataDictReq weekFormatReq = new MetaDataDictReq();
                    weekFormatReq.setDictPath(Constant.WEEKFORMAT);
                    Map<String, List<MetadataDict>> weekFormatValue = metaDataDictService.getFTExpertConfigDictListByPath(weekFormatReq);

                    boolean pass = true;//true表示通过 false 未通过
                    StringBuffer errorMsg = new StringBuffer();
                    MetadataDict metadataDict = null;
                    MetaDataFieldInfo metaDataFieldInfo = null;//获取元数据字段id使用
                    //校验编号是否重复
                    if (index == 2) {
                        pass = isPassNum(idNumMapMoney, thirdVo, pass, errorMsg);
                    } else if (index == 3) {
                        pass = isPassNum(idNumMapValue, thirdVo, pass, errorMsg);
                    } else if (index == 4) {
                        pass = isPassNum(idNumMapPercent, thirdVo, pass, errorMsg);
                    } else if (index == 5) {
                        pass = isPassNum(idNumMapText, thirdVo, pass, errorMsg);
                    } else if (index == 6) {
                        pass = isPassNum(idNumMapDt, thirdVo, pass, errorMsg);
                    } else if (index == 1) {
                        pass = isPassNum(idNumMap, thirdVo, pass, errorMsg);
                    }
                    //编号校验通过后再校验其他
                    if (pass) {
                        if (StrUtil.isEmpty(thirdVo.getIdNum())) {
                            errorMsg.append("idNum 为空值;");
                            pass = false;
                        } else if (StrUtil.isEmpty(thirdVo.getFieldName())) {
                            errorMsg.append("fieldName 为空值;");
                            pass = false;
                        } else if (StrUtil.isEmpty(thirdVo.getShowNameCn())) {
                            errorMsg.append("showNameCn 为空值;");
                            pass = false;
                        } else if (StrUtil.isEmpty(thirdVo.getShowNameEn())) {
                            errorMsg.append("showNameEn 为空值;");
                            pass = false;
                        } else if (index == 1 && StrUtil.isEmpty(thirdVo.getFieldTypeName())) {
                            errorMsg.append("fieldTypeName 为空值;");
                            pass = false;
                        } else if (!thirdVo.getFieldTypeName().equals("占位符") && StrUtil.isEmpty(thirdVo.getMetadataObjName())) {
                            errorMsg.append("metadataObjName 为空值;");
                            pass = false;
                        } else if (!thirdVo.getFieldTypeName().equals("占位符") && StrUtil.isEmpty(thirdVo.getMetadataFieldName())) {
                            errorMsg.append("metadataFieldName 为空值;");
                            pass = false;
                        } else if (null == thirdVo.getIsExtension()) {
                            errorMsg.append("isExtension 为空值;");
                            pass = false;
                        } else if (thirdVo.getIsExtension() == 1 && StrUtil.isEmpty(thirdVo.getExtensionName())) {
                            errorMsg.append("extensionName isExtension 为1时 extensionName必填;");
                            pass = false;
                        } else if (thirdVo.getFieldTypeName().equals("金额")) {//校验高级配置信息
                            pass = isPassMoney(thirdVo, dictValue, unitTransformValue, errorMsg);
                        } else if (thirdVo.getFieldTypeName().equals("数值")) {
                            pass = isPassValue(thirdVo, dictValue, errorMsg);
                        } else if (thirdVo.getFieldTypeName().equals("百分比")) {
                            pass = isPassPercent(thirdVo, numberWayValue, errorMsg);
                        } else if (thirdVo.getFieldTypeName().equals("文本")) {
                            pass = isPassText(thirdVo, dictValueValidata, lineTypeValue, errorMsg);
                        } else if (thirdVo.getFieldTypeName().equals("日期时间")) {
                            pass = isPassDt(thirdVo, dateFormatValue, timeFormatValue, weekFormatValue, errorMsg);
                        } else {
                            pass = true;
                        }

                        //去除 字段名和自定义字段首尾空格
                        if (StrUtil.isNotEmpty(thirdVo.getFieldName())) thirdVo.setFieldName(thirdVo.getFieldName().trim());
                        //2校验字段名是否重复
                        ThirdFieldInfo thirdFieldInfoVo = thirdInfoDao.getThirdFieldByName(thirdVo.getFieldName(),
                                thirdObjId);
                        if (null != thirdFieldInfoVo) {
                            errorMsg.append("fieldName 字段名在数据库已存在;");
                            pass = false;
                        }
                        //3校验字段类型
                        if (StrUtil.isNotEmpty(thirdVo.getFieldTypeName())) {
                            metadataDict = metaDataDictService.getDictByName(thirdVo.getFieldTypeName(),
                                    Constant.DICT_FIELD_TYPE);
                            if (null == metadataDict) {
                                errorMsg.append("fieldTypeName 未查询到此字段类型名称;");
                                pass = false;
                            }
                        }
//                        //4元数据字段名校验查看是否存在（校验excel中的数据）
//                        list.addAll(thirdVoList);
//                        Map<Object, Long> collect2 = list.stream().filter(item->StrUtil.isNotEmpty(item.getMetadataFieldName())).collect(
//                                Collectors.groupingBy(ThirdFieldExcelimportVo:: getMetadataFieldName, Collectors.counting()));
//                        //筛出excel文件中metadataFieldName有重复的
//                        List<Object> collect3 = collect2.keySet().stream().
//                                filter(key -> collect2.get(key) > 1).collect(Collectors.toList());
//                        //校验导入的excel文件中metadataFieldName是否重复
//                        if (!com.alibaba.excel.util.CollectionUtils.isEmpty(collect3)) {
//                            errorMsg.append("metadataFieldName 该元数据字段名称在excel中只能绑定一次");
//                            pass = false;
//                        }
                        //4元数据字段名校验查看是否存在（跟数据库的数据对比）
                        if (StrUtil.isNotEmpty(thirdVo.getMetadataFieldName()) && StrUtil.isNotEmpty(thirdVo.getMetadataObjName())) {
                            //获取元数据对象
                            MetaDataObjectInfo metaDataObjectInfo = metaDataInfoDao.getmetaDataObjInfoByName(thirdVo.getMetadataObjName());
                            if (null == metaDataObjectInfo) {
                                errorMsg.append("metadataObjName 未查询到此元数据对象;");
                                pass = false;
                            } else {
                                //获取元数据字段
                                metaDataFieldInfo = metaDataInfoDao.getMetaFieldByName(
                                        thirdVo.getMetadataFieldName(), metaDataObjectInfo.getId());
                                if (null == metaDataFieldInfo) {
                                    errorMsg.append("metadataFieldName 未查询到此元数据字段;");
                                    pass = false;
                                } else {
                                    //校验此元数据字段是否已经绑定了 业务字段
                                    ThirdFieldInfo thirdFieldInfoVailResult = thirdInfoDao.getThirdFieldByThirdObjIdAndMetaFeildId
                                            (thirdObjId, metaDataFieldInfo.getId());
                                    if (null != thirdFieldInfoVailResult) {
                                        errorMsg.append("metadataFieldName 该元数据字段已经被绑定了;");
                                        pass = false;
                                    }
                                }
                            }
                        }
                        //5校验自定义字段是否存在
                        if (null != thirdVo.getIsExtension() && thirdVo.getIsExtension() == 1 && StrUtil.isNotEmpty(thirdVo.getExtensionName())) {
                            thirdVo.setExtensionName(thirdVo.getExtensionName().trim());
                            ThirdFieldInfo thirdFieldInfo = thirdInfoDao.getThirdFieldByObjIdAndExt(thirdObjId, thirdVo.getExtensionName());
                            if (null != thirdFieldInfo) {
                                errorMsg.append("extensionName 自定义字段在数据库已存在;");
                                pass = false;
                            }
                        }
                        //6校验单行和多行文本长度
                        if (StrUtil.isNotEmpty(thirdVo.getFieldName()) && thirdVo.getFieldName().length() > 64) {
                            errorMsg.append("fieldName 字段名称长度最长为64个字符;");
                            pass = false;
                        } else if (StrUtil.isNotEmpty(thirdVo.getShowNameCn()) && thirdVo.getShowNameCn().length() > 64) {
                            errorMsg.append("showNameCn 字段显示名中文长度最长为64个字符;");
                            pass = false;
                        } else if (StrUtil.isNotEmpty(thirdVo.getShowNameEn()) && thirdVo.getShowNameEn().length() > 64) {
                            errorMsg.append("showNameEn 字段显示名英文长度最长为64个字符;");
                            pass = false;
                        }
                        //7校验元数据对象是否为停用状态
                        if (StrUtil.isNotEmpty(thirdVo.getMetadataObjName())) {
                            MetaDataObjectInfo metaDataObjectInfo = metaDataInfoDao.
                                    getmetaDataObjInfoByName(thirdVo.getMetadataObjName());
                            if (null != metaDataObjectInfo && null != metaDataObjectInfo.getStatus() && metaDataObjectInfo.getStatus() == 0) {
                                errorMsg.append("metadataObjName 绑定的元数据对象已停用;");
                                pass = false;
                            }
                        }
                    }
                    //校验是否通过
                    if (pass) {
                        //向通过集合中插入 数据
                        String thirdimportVoStr = JSONObject.toJSON(thirdVo).toString();
                        //不同的sheet数据转成不同的对象
                        ThirdFieldInfo thirdFieldInfo = JSONObject.parseObject(thirdimportVoStr, ThirdFieldInfo.class);
                        FieldtypeExtendMoney fieldtypeExtendMoney = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendMoney.class);
                        FieldtypeExtendValue fieldtypeExtendValue = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendValue.class);
                        FieldtypeExtendPercent fieldtypeExtendPercent = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendPercent.class);
                        FieldtypeExtendText fieldtypeExtendText = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendText.class);
                        FieldtypeExtendDt fieldtypeExtendDt = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendDt.class);
                        //插入各个sheet中的code字段
                        addCode(fieldtypeExtendMoney, fieldtypeExtendValue, fieldtypeExtendPercent, fieldtypeExtendText, fieldtypeExtendDt);
                        //插入前做字段名是否已存在的数据校验
                        boolean isRepetitionFieldName = false;//是否为重复字段名
                        if (null != vos || vos.size() > 0) {
                            for (ThirdFieldExcelimportVo fieldInfo : vos) {
                                if (StrUtil.isNotEmpty(fieldInfo.getFieldName() == null ? "": fieldInfo.getFieldName()) &&
                                        StrUtil.isNotEmpty(thirdFieldInfo.getFieldName()) &&
                                        fieldInfo.getFieldName().equals(thirdFieldInfo.getFieldName())) {
                                    //如果通过校验的数据集合中存在相同的字段名 则不插入此集合中
                                    isRepetitionFieldName = true;
                                    break;
                                }
                            }
                        }
                        if (!isRepetitionFieldName) {
                            ThirdObjectInfo thirdObject = thirdInfoDao.getThirdObjectInfoById(thirdObjId);//获取业务对象
                            thirdFieldInfo.setFieldTypeCode(metadataDict.getCode());
                            thirdFieldInfo.setSourceId(thirdObject.getSourceId());
                            thirdFieldInfo.setSourceName(thirdObject.getSourceName());
                            if (StrUtil.isNotEmpty(thirdVo.getMetadataFieldName())) {
                                thirdFieldInfo.setMetadataFieldId(metaDataFieldInfo.getId());
                            }
                            thirdFieldInfo.setStatus(Constant.METADATA_ONE);
                            thirdFieldInfo.setThirdObjectId(thirdObjId);
                            thirdFieldInfo.setIsExtension(thirdVo.getIsExtension());
                            thirdFieldInfo.setExtensionName(StrUtil.isEmpty(thirdVo.getExtensionName()) ? null : thirdVo.getExtensionName());
                            vo.setThirdFieldInfo(thirdFieldInfo);
                            //插入是为了字段名是否已存在的数据校验
                            vo.setFieldName(thirdFieldInfo.getFieldName());
                            vo.setFieldtypeExtendMoney(fieldtypeExtendMoney);
                            vo.setFieldtypeExtendValue(fieldtypeExtendValue);
                            vo.setFieldtypeExtendPercent(fieldtypeExtendPercent);
                            vo.setFieldtypeExtendText(fieldtypeExtendText);
                            vo.setFieldtypeExtendDt(fieldtypeExtendDt);
                            if (null != vo.getThirdFieldInfo()) {
                                successCount = successCount + 1;
                            }

                        } else {
                            //存在相同的字段名，向错误集合中插入此问题数据
                            Map<String, Object> errorMap = new HashMap<>();
                            errorMap.put(thirdVo.getIdNum() == null ? "未知行号" : thirdVo.getIdNum(), "fieldName 字段名在数据库已存在;");
                            if (index == 2) {
                                failureListMoney.add(errorMap);
                            } else if (index == 3) {
                                failureListValue.add(errorMap);
                            } else if (index == 4) {
                                failureListPercent.add(errorMap);
                            } else if (index == 5) {
                                failureListText.add(errorMap);
                            } else if (index == 6) {
                                failureListDt.add(errorMap);
                            } else if (index == 1) {
                                failureList.add(errorMap);
                            }
                        }
                    } else {
                        //向错误信息集合中插入问题数据
                        Map<String, Object> errorMap = new HashMap<>();
                        errorMap.put(thirdVo.getIdNum() == null ? "未知行号" : thirdVo.getIdNum(), errorMsg);
                        if (index == 2) {
                            failureListMoney.add(errorMap);
                        } else if (index == 3) {
                            failureListValue.add(errorMap);
                        } else if (index == 4) {
                            failureListPercent.add(errorMap);
                        } else if (index == 5) {
                            failureListText.add(errorMap);
                        } else if (index == 6) {
                            failureListDt.add(errorMap);
                        } else if (index == 1) {
                            failureList.add(errorMap);
                        }
                    }
                    vos.add(vo);
                }
                //验证成功的数据条数
                thirdVoListSize = (thirdVoListSize + thirdVoList.size());
                metaFieldImportResult.setTotalCount(thirdVoListSize);
            }
            failureMap.put("thirdInfo",failureList);
            failureMap.put("金额",failureListMoney);
            failureMap.put("数值",failureListValue);
            failureMap.put("百分比",failureListPercent);
            failureMap.put("文本",failureListText);
            failureMap.put("日期时间",failureListDt);
            metaFieldImportResult.setFailureList(failureMap);
        }
        metaFieldImportResult.setSuccessCount(successCount);
        return vos;
    }

    //校验字段类型为百分比的数据
    private boolean isPassPercent(ThirdFieldExcelimportVo thirdVo, Map<String, List<MetadataDict>> numberWayValue, StringBuffer errorMsg) {
        boolean pass;
        pass = true;
        //校验 保存小数位数 是否是0到10之间的整数
        boolean decimalsPlacesFlag = RegularUtils.numberIsInTheRange(thirdVo.getSaveDecimalsPlacesPercent() == null ? -1 : thirdVo.getSaveDecimalsPlacesPercent(),0,10);
        //校验 金额范围
        boolean numberScopeMin = RegularUtils.isResult(StrUtil.isEmpty(thirdVo.getNumberScopeMin()) ? "-1" : thirdVo.getNumberScopeMin());
        //校验 金额范围
        boolean numberScopeMax = RegularUtils.isResult(StrUtil.isEmpty(thirdVo.getNumberScopeMax()) ? "-1" : thirdVo.getNumberScopeMax());
        //校验 默认值
        boolean isDefaultValue = RegularUtils.isResult(StrUtil.isEmpty(thirdVo.getDefaultValuePercent()) ? "-1" : thirdVo.getDefaultValuePercent());
        if (null != thirdVo.getSaveDecimalsPlacesPercent() && !decimalsPlacesFlag) {
            errorMsg.append("saveDecimalsPlacesPercent 值必须是0到10之间的整数;");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getInputNumberWayName()) && !numberWayValue.get(Constant.INPUT_NUMBER_WAY).stream()
                .filter(item->item.getName()
                        .equals(thirdVo.getInputNumberWayName()))
                .findAny()
                .isPresent()) {//单位显示位置
            errorMsg.append("inputNumberWayName 未查询到此输入数值方式;");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getNumberScopeMin()) && !numberScopeMin) {
            errorMsg.append("numberScopeMin 值必须是正整数(包括0,小数保留两位);");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getNumberScopeMax()) && !numberScopeMax) {
            errorMsg.append("numberScopeMax 值必须是正整数(包括0,小数保留两位);");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getNumberScopeMin()) && StrUtil.isNotEmpty(thirdVo.getNumberScopeMax()) && (new BigDecimal(thirdVo.getNumberScopeMin()).compareTo(new BigDecimal(thirdVo.getNumberScopeMax())) == 1)) {
            errorMsg.append("numberScopeMin 或numberScopeMax值必须是正整数(包括0,小数保留两位)且numberScopeMin小于numberScopeMax;");
            pass = false;
        } else if (StrUtil.isNotEmpty( thirdVo.getDefaultValuePercent()) && !isDefaultValue) {
            errorMsg.append("defaultValuePercent 值必须是正整数(包括0,小数保留两位);");
            pass = false;
        } else if (null != thirdVo.getIsEmployPercent() && (thirdVo.getIsEmployPercent() != 0 && thirdVo.getIsEmployPercent() != 1)) {
            errorMsg.append("isEmployPercent 值必须是0或者1;");
            pass = false;
        }
        return pass;
    }
    //校验字段类型为日期时间的数据
    private boolean isPassDt(ThirdFieldExcelimportVo thirdVo, Map<String, List<MetadataDict>> dateFormatValue, Map<String, List<MetadataDict>> timeFormatValue, Map<String, List<MetadataDict>> weekFormatValue, StringBuffer errorMsg) {
        boolean pass;
        pass = true;
       if (null != thirdVo.getIsDate() && (thirdVo.getIsDate() != 0 && thirdVo.getIsDate() != 1)) {
            errorMsg.append("isDate 值必须是0或者1;");
            pass = false;
        } else if (null != thirdVo.getIsTime() && (thirdVo.getIsTime() != 0 && thirdVo.getIsTime() != 1)) {
            errorMsg.append("isTime 值必须是0或者1;");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getDateFormatName()) && !dateFormatValue.get(Constant.DATETIMEFORMAT).stream()
                .filter(item -> item.getName()
                        .equals(thirdVo.getDateFormatName()))
                .findAny()
                .isPresent()) {//单位显示位置
            errorMsg.append("dateFormatName 未查询到此日期格式;");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getTimeFormatName()) && !timeFormatValue.get(Constant.TIMEFORMAT).stream()
                .filter(item -> item.getName()
                        .equals(thirdVo.getTimeFormatName()))
                .findAny()
                .isPresent()) {//单位显示位置
            errorMsg.append("timeFormatName 未查询到此时间格式;");
            pass = false;
        } else if (null != thirdVo.getIsEmployDt() && (thirdVo.getIsEmployDt() != 0 && thirdVo.getIsEmployDt() != 1)) {
            errorMsg.append("isEmployDt 值必须是0或者1;");
            pass = false;
        }
        //2.0版本不实现
        /*else if (null != thirdVo.getIsWeek() && (thirdVo.getIsWeek() != 0 && thirdVo.getIsWeek() != 1)) {
            errorMsg.append("isWeek 值必须是0或者1;");
            pass = false;
        }
        else if (StrUtil.isNotEmpty(thirdVo.getWeekFormatName()) && !weekFormatValue.get(Constant.WEEKFORMAT).stream()
                .filter(item -> item.getName()
                        .equals(thirdVo.getWeekFormatName()))
                .findAny()
                .isPresent()) {//单位显示位置
            errorMsg.append("weekFormatName 未查询到此星期格式;");
            pass = false;
        }*/
        return pass;
    }
    //校验字段类型为文本的数据
    private boolean isPassText(ThirdFieldExcelimportVo thirdVo, Map<String, List<MetadataDict>> dictValueValidata, Map<String, List<MetadataDict>> lineTypeValue, StringBuffer errorMsg) {
        boolean pass;
        pass = true;
        //校验 默认值
        boolean isDefaultValue = RegularUtils.isResult(StrUtil.isEmpty(thirdVo.getDefaultValueText()) ? "-1" : thirdVo.getDefaultValueText());
        //校验 最大字数限制
        boolean maxNumberLimit = RegularUtils.isInteger(StrUtil.isEmpty(thirdVo.getMaxNumberLimit()) ? "-1" : thirdVo.getMaxNumberLimit());
        if (StrUtil.isNotEmpty(thirdVo.getLineTypeName()) && !lineTypeValue.get(Constant.LINE_TYPE).stream()
                .filter(item->item.getName()
                        .equals(thirdVo.getLineTypeName()))
                .findAny()
                .isPresent()) {//单位显示位置
            errorMsg.append("lineTypeName 未查询到此行类型;");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getFormatValidataName()) && !dictValueValidata.get(Constant.FORMAT_VALIDATA).stream()//邮箱
                .filter(item->item.getName()
                        .equals(thirdVo.getFormatValidataName()))
                .findAny()
                .isPresent()) {//单位显示位置
            errorMsg.append("formatValidataName 未查询到此邮箱类型;");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getDefaultValueText()) && !isDefaultValue) {
            errorMsg.append("defaultValueText 值必须是正整数(包括0,小数保留两位);");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getMaxNumberLimit()) && !maxNumberLimit) {
            errorMsg.append("maxNumberLimit 值必须是正整数;");
            pass = false;
        } else if (null != thirdVo.getIsEmployText() && (thirdVo.getIsEmployText() != 0 && thirdVo.getIsEmployText() != 1)) {
            errorMsg.append("isEmployText 值必须是0或者1;");
            pass = false;
        }
        return pass;
    }
    //校验字段类型为数值的数据
    private boolean isPassValue(ThirdFieldExcelimportVo thirdVo, Map<String, List<MetadataDict>> dictValue, StringBuffer errorMsg) {
        boolean pass;
        pass = true;
        //校验 保存小数位数 是否是0到10之间的整数
        boolean decimalsPlacesFlag = RegularUtils.numberIsInTheRange(thirdVo.getSaveDecimalsPlacesValue() == null ? -1 : thirdVo.getSaveDecimalsPlacesValue(),0,10);
        //校验 金额范围
        boolean numberScopeMin = RegularUtils.isResult(StrUtil.isEmpty(thirdVo.getNumberScopeMinValue()) ? "-1" : thirdVo.getNumberScopeMinValue());
        //校验 金额范围
        boolean numberScopeMax = RegularUtils.isResult(StrUtil.isEmpty(thirdVo.getNumberScopeMaxValue()) ? "-1" : thirdVo.getNumberScopeMaxValue());
        //校验 默认值
        boolean isDefaultValue = RegularUtils.isResult(StrUtil.isEmpty(thirdVo.getDefaultValueValue()) ? "-1" : thirdVo.getDefaultValueValue());
        if (null != thirdVo.getSaveDecimalsPlaces() && !decimalsPlacesFlag) {
            errorMsg.append("saveDecimalsPlacesValue 值必须是0到10之间的整数;");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getUnitShowlocationNameValue()) && !dictValue.get(Constant.UNIT_SHOW_LOCATIONINPUT).stream().filter(item->item.getName().equals(thirdVo.getUnitShowlocationNameValue())).findAny().isPresent()) {//单位显示位置
            errorMsg.append("unitShowlocationNameValue 未查询到此单位显示位置名称;");//判断dictValue中是否存在此单位显示位置名称
            pass = false;
        } else if (null != thirdVo.getIsEmployValue() && (thirdVo.getIsEmployValue() != 0 && thirdVo.getIsEmployValue() != 1)) {
            errorMsg.append("isEmployValue 值必须是0或者1;");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getNumberScopeMinValue()) && !numberScopeMin) {
            errorMsg.append("numberScopeMinValue 值必须是正整数(包括0,小数保留两位);");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getNumberScopeMaxValue()) && !numberScopeMax) {
            errorMsg.append("numberScopeMaxValue 值必须是正整数(包括0,小数保留两位);");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getNumberScopeMinValue()) && StrUtil.isNotEmpty(thirdVo.getNumberScopeMaxValue()) && (new BigDecimal(thirdVo.getNumberScopeMinValue()).compareTo(new BigDecimal(thirdVo.getNumberScopeMaxValue())) == 1)) {
            errorMsg.append("numberScopeMinValue 或numberScopeMaxValue值必须是正整数(包括0,小数保留两位)且numberScopeMinValue小于numberScopeMaxValue;");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getDefaultValueValue()) && !isDefaultValue) {
            errorMsg.append("defaultValueValue 值必须是正整数(包括0,小数保留两位);");
            pass = false;
        }
        return pass;
    }
    //校验字段类型为金额的数据
    private boolean isPassMoney(ThirdFieldExcelimportVo thirdVo, Map<String, List<MetadataDict>> dictValue, Map<String, List<MetadataDict>> unitTransformValue, StringBuffer errorMsg) {
        boolean pass;
        pass = true;
        //校验 保存小数位数 是否是0到10之间的整数
        boolean decimalsPlacesFlag = RegularUtils.numberIsInTheRange(thirdVo.getSaveDecimalsPlaces() == null ? -1 : thirdVo.getSaveDecimalsPlaces(), 0, 10);
        //校验 金额范围
        boolean moneyScopeMin = RegularUtils.isResult(StrUtil.isEmpty(thirdVo.getMoneyScopeMin()) ? "-1" : thirdVo.getMoneyScopeMin());
        //校验 金额范围
        boolean moneyScopeMax = RegularUtils.isResult(StrUtil.isEmpty(thirdVo.getMoneyScopeMax()) ? "-1" : thirdVo.getMoneyScopeMax());
        //校验 默认值
        boolean isDefaultValue = RegularUtils.isResult(StrUtil.isEmpty(thirdVo.getDefaultValueMoney()) ? "-1" : thirdVo.getDefaultValueMoney());
        if (null != thirdVo.getSaveDecimalsPlaces() && !decimalsPlacesFlag) {
            errorMsg.append("saveDecimalsPlaces 值必须是0到10之间的整数;");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getUnitTransformName()) && !unitTransformValue.get(Constant.UNIT_TRANSFORM).stream()
                .filter(item->item.getName()
                        .equals(thirdVo.getUnitTransformName()))
                .findAny()
                .isPresent()) {//单位显示位置
            errorMsg.append("unitTransformName 未查询到此单位转换名称;");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getUnitShowlocationName()) && !dictValue.get(Constant.UNIT_SHOW_LOCATIONINPUT).stream()
                .filter(item->item.getName()
                        .equals(thirdVo.getUnitShowlocationName()))
                .findAny()
                .isPresent()) {//单位显示位置
            errorMsg.append("unitShowlocationName 未查询到此单位显示位置名称;");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getMoneyScopeMin()) && !moneyScopeMin) {
            errorMsg.append("moneyScopeMin 值必须是正整数(包括0,小数保留两位);");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getMoneyScopeMax()) && !moneyScopeMax) {
            errorMsg.append("moneyScopeMax 值必须是正整数(包括0,小数保留两位);");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getMoneyScopeMin()) && StrUtil.isNotEmpty(thirdVo.getMoneyScopeMax()) && (new BigDecimal(thirdVo.getMoneyScopeMin()).compareTo(new BigDecimal(thirdVo.getMoneyScopeMax())) == 1)) {
            errorMsg.append("moneyScopeMin 或moneyScopeMax值必须是正整数(包括0,小数保留两位)且moneyScopeMin小于moneyScopeMax;");
            pass = false;
        } else if (StrUtil.isNotEmpty(thirdVo.getDefaultValueMoney()) && !isDefaultValue) {
            errorMsg.append("defaultValueMoney 值必须是正整数(包括0,小数保留两位);");
            pass = false;
        }  else if (null != thirdVo.getIsEmployMoney() && (thirdVo.getIsEmployMoney() != 0 && thirdVo.getIsEmployMoney() != 1)) {
            errorMsg.append("isEmployMoney 值必须是0或者1;");
            pass = false;
        } else if (null != thirdVo.getIsShowCurrency() && (thirdVo.getIsShowCurrency() != 0 && thirdVo.getIsShowCurrency() != 1)) {
            errorMsg.append("isShowCurrency 值必须是0或者1;");
            pass = false;
        }
        return pass;
    }

    /**
     * 赋值code字段
     *
     * @param  fieldtypeExtendMoney
     * @param  fieldtypeExtendValue
     * @param  fieldtypeExtendPercent
     * @param  fieldtypeExtendText
     */
    private void addCode(FieldtypeExtendMoney fieldtypeExtendMoney, FieldtypeExtendValue fieldtypeExtendValue, FieldtypeExtendPercent fieldtypeExtendPercent, FieldtypeExtendText fieldtypeExtendText, FieldtypeExtendDt fieldtypeExtendDt) {
        //添加金额字段中code
        if(StrUtil.isNotEmpty(fieldtypeExtendMoney.getUnitTransformName())){
            if(fieldtypeExtendMoney.getUnitTransformName().equals("千元")){
                fieldtypeExtendMoney.setUnitTransformCode(Constant.DICT_CODE_THOUSAND);
            }
            if(fieldtypeExtendMoney.getUnitTransformName().equals("万元")){
                fieldtypeExtendMoney.setUnitTransformCode(Constant.DICT_CODE_TEN_THOUSAND);
            }
            if(fieldtypeExtendMoney.getUnitTransformName().equals("亿元")){
                fieldtypeExtendMoney.setUnitTransformCode(Constant.DICT_CODE_100_MILLION);
            }
            if (fieldtypeExtendMoney.getUnitTransformName().equals("百万")) {
                fieldtypeExtendMoney.setUnitTransformCode(Constant.DICT_CODE_MILLION);
            }
        }
        if(StrUtil.isNotEmpty(fieldtypeExtendMoney.getUnitShowlocationName())){
            if(fieldtypeExtendMoney.getUnitShowlocationName().equals("前缀")){
                fieldtypeExtendMoney.setUnitShowlocationCode(Constant.DICT_CODE_PREFIX);
            }
            if(fieldtypeExtendMoney.getUnitShowlocationName().equals("后缀")){
                fieldtypeExtendMoney.setUnitShowlocationCode(Constant.DICT_CODE_SUFFIX);
            }
        }
        //添加数值字段中code
        if(StrUtil.isNotEmpty(fieldtypeExtendValue.getUnitShowlocationNameValue())){
            if(fieldtypeExtendValue.getUnitShowlocationNameValue().equals("前缀")){
                fieldtypeExtendValue.setUnitShowlocationCode(Constant.DICT_CODE_PREFIX);
            }
            if(fieldtypeExtendValue.getUnitShowlocationNameValue().equals("后缀")){
                fieldtypeExtendValue.setUnitShowlocationCode(Constant.DICT_CODE_SUFFIX);
            }
        }
        //添加百分比字段中code
        if(StrUtil.isNotEmpty(fieldtypeExtendPercent.getInputNumberWayName())){
            if(fieldtypeExtendPercent.getInputNumberWayName().equals("小数方式")){
                fieldtypeExtendPercent.setInputNumberWayCode(Constant.DICT_CODE_DECIMALS);
            }
            if(fieldtypeExtendPercent.getInputNumberWayName().equals("百分数方式")){
                fieldtypeExtendPercent.setInputNumberWayCode(Constant.DICT_CODE_PERCENT);
            }
        }
        //添加文本字段中code
        if(StrUtil.isNotEmpty(fieldtypeExtendText.getLineTypeName())){
            if(fieldtypeExtendText.getLineTypeName().equals("单行")){
                fieldtypeExtendText.setLineTypeCode(Constant.DICT_CODE_UNILINE);
            }
            if(fieldtypeExtendText.getLineTypeName().equals("多行")){
                fieldtypeExtendText.setLineTypeCode(Constant.DICT_CODE_MULTILINE);
            }
        }
        if(StrUtil.isNotEmpty(fieldtypeExtendText.getFormatValidataName())){
            if(fieldtypeExtendText.getFormatValidataName().equals("邮箱")){
                fieldtypeExtendText.setFormatValidataCode(Constant.DICT_CODE_MAILBOX);
            }
            if(fieldtypeExtendText.getFormatValidataName().equals("大陆手机号")){
                fieldtypeExtendText.setFormatValidataCode(Constant.DICT_CODE_PHONENUMBER);
            }
            if(fieldtypeExtendText.getFormatValidataName().equals("身份证")){
                fieldtypeExtendText.setFormatValidataCode(Constant.DICT_CODE_IDENTITYCARD);
            }
        }
        //添加日期字段中code
        if (StrUtil.isNotEmpty(fieldtypeExtendDt.getDateFormatName())) {
            if (fieldtypeExtendDt.getDateFormatName().equals("YYYY/MM/DD")) {
                fieldtypeExtendDt.setDateFormatCode(Constant.DICT_CODE_DATE_FORMAT_1);
            } else if (fieldtypeExtendDt.getDateFormatName().equals("YY/MM/DD")) {
                fieldtypeExtendDt.setDateFormatCode(Constant.DICT_CODE_DATE_FORMAT_2);
            } else if (fieldtypeExtendDt.getDateFormatName().equals("MM/DD")) {
                fieldtypeExtendDt.setDateFormatCode(Constant.DICT_CODE_DATE_FORMAT_3);
            } else if (fieldtypeExtendDt.getDateFormatName().equals("YYYY-MM-DD")) {
                fieldtypeExtendDt.setDateFormatCode(Constant.DICT_CODE_DATE_FORMAT_4);
            } else if (fieldtypeExtendDt.getDateFormatName().equals("YY-MM-DD")) {
                fieldtypeExtendDt.setDateFormatCode(Constant.DICT_CODE_DATE_FORMAT_5);
            } else if (fieldtypeExtendDt.getDateFormatName().equals("MM-DD")) {
                fieldtypeExtendDt.setDateFormatCode(Constant.DICT_CODE_DATE_FORMAT_6);
            } else if (fieldtypeExtendDt.getDateFormatName().equals("MM/DD/YYYY")) {
                fieldtypeExtendDt.setDateFormatCode(Constant.DICT_CODE_DATE_FORMAT_7);
            } else if (fieldtypeExtendDt.getDateFormatName().equals("MM/DD/YY")) {
                fieldtypeExtendDt.setDateFormatCode(Constant.DICT_CODE_DATE_FORMAT_8);
            } else if (fieldtypeExtendDt.getDateFormatName().equals("YYYY年MM月DD日")) {
                fieldtypeExtendDt.setDateFormatCode(Constant.DICT_CODE_DATE_FORMAT_9);
            } else if (fieldtypeExtendDt.getDateFormatName().equals("YY年MM月DD日")) {
                fieldtypeExtendDt.setDateFormatCode(Constant.DICT_CODE_DATE_FORMAT_10);
            } else if (fieldtypeExtendDt.getDateFormatName().equals("MM月DD日")) {
                fieldtypeExtendDt.setDateFormatCode(Constant.DICT_CODE_DATE_FORMAT_11);
            }
        }
        if (StrUtil.isNotEmpty(fieldtypeExtendDt.getTimeFormatName())) {
            if (fieldtypeExtendDt.getTimeFormatName().equals("HH:mm:ss")) {
                fieldtypeExtendDt.setTimeFormatCode(Constant.DICT_CODE_TIME_FORMAT_1);
            } else if (fieldtypeExtendDt.getTimeFormatName().equals("HH:mm")) {
                fieldtypeExtendDt.setTimeFormatCode(Constant.DICT_CODE_TIME_FORMAT_2);
            } else if (fieldtypeExtendDt.getTimeFormatName().equals("HH时mm分ss秒")) {
                fieldtypeExtendDt.setTimeFormatCode(Constant.DICT_CODE_TIME_FORMAT_3);
            } else if (fieldtypeExtendDt.getTimeFormatName().equals("HH时mm分")) {
                fieldtypeExtendDt.setTimeFormatCode(Constant.DICT_CODE_TIME_FORMAT_4);
            }
        }
        //2.0版本不实现
/*        if (StrUtil.isNotEmpty(fieldtypeExtendDt.getWeekFormatName())) {
            if (fieldtypeExtendDt.getWeekFormatName().equals("Mon")) {
                fieldtypeExtendDt.setWeekFormatCode(Constant.DICT_CODE_WEEK_FORMAT_1);
            } else if (fieldtypeExtendDt.getWeekFormatName().equals("Monday")) {
                fieldtypeExtendDt.setWeekFormatCode(Constant.DICT_CODE_WEEK_FORMAT_2);
            } else if (fieldtypeExtendDt.getWeekFormatName().equals("星期一")) {
                fieldtypeExtendDt.setWeekFormatCode(Constant.DICT_CODE_WEEK_FORMAT_3);
            } else if (fieldtypeExtendDt.getWeekFormatName().equals("周一")) {
                fieldtypeExtendDt.setWeekFormatCode(Constant.DICT_CODE_WEEK_FORMAT_4);
            } else if (fieldtypeExtendDt.getWeekFormatName().equals("礼拜一")) {
                fieldtypeExtendDt.setWeekFormatCode(Constant.DICT_CODE_WEEK_FORMAT_5);
            }
        }*/
    }

    //校验编号是否重复
    private boolean isPassNum(Map<String, ThirdFieldExcelimportVo> idNumMap, ThirdFieldExcelimportVo thirdVo, boolean pass, StringBuffer errorMsg) {
        if (StrUtil.isNotEmpty(thirdVo.getIdNum())) {
            ThirdFieldExcelimportVo idNumMapVo = idNumMap.get(thirdVo.getIdNum());
            if (null != idNumMapVo) {//map中已经存在此key值
                errorMsg.append("idNum为：「" + thirdVo.getIdNum() + "」值重复;");
                pass = false;
            } else idNumMap.put(thirdVo.getIdNum(), thirdVo);
        }
        return pass;
    }

    /**
     * 导出业务对象Excel
     *
     * @param thirdObjectExcelExportReq
     * @return {@link ThirdFieldExcelimportVo}
     */
    @Override
    public List<ThirdFieldExcelimportVo> getThirdDataInfoByIds(ThirdObjectExcelExportReq thirdObjectExcelExportReq) {
        List<ThirdFieldExcelimportVo> thirdFieldList = new ArrayList<>();
        //高级配置字段类型code
        List<String> extendCodeList = PageTransferUtils.buildExtendTableType();//组织高级配置字段类型
        //没有传id时导出用户创建的所有字段
        if (StrUtil.isEmpty(thirdObjectExcelExportReq.getIds()) || thirdObjectExcelExportReq.getIds() == "") {
            if (null == thirdObjectExcelExportReq.getThirdObjectId() || StrUtil.isEmpty(thirdObjectExcelExportReq.getSourceId())) {
                throw new BuilderException("业务对象id或者系统id不能为空");
            }
            List<ThirdFieldExcelimportVo> thirdFieldInfo = thirdInfoDao.getThirdDataInfoByUsercode(thirdObjectExcelExportReq.getThirdObjectId(), thirdObjectExcelExportReq.getSourceId());
            if (CollectionUtils.isEmpty(thirdFieldInfo)) {
                throw new BuilderException("业务对象的字段信息不存在");
            }
            for (int i = 0; i < thirdFieldInfo.size(); i++) {
                ///获取高级配置信息
                for (String s : extendCodeList) {
                    if (s.equals(thirdFieldInfo.get(i).getFieldTypeCode())) {
                        //通过字段id和字段类型code查询高级配置信息
                        JSONObject o = fieldTypeExtendDao.selectOneByTableName(thirdFieldInfo.get(i).getId(), thirdFieldInfo.get(i).getFieldTypeCode());
                        if (null != o) {
                            String thirdimportVoStr = JSONObject.toJSON(o).toString();
                            if (thirdFieldInfo.get(i).getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_TEXT)) {
                                FieldtypeExtendText fieldtypeExtendText = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendText.class);
                                thirdFieldInfo.get(i).setFieldtypeExtendText(fieldtypeExtendText);
                            }
                            if (thirdFieldInfo.get(i).getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_VALUE)) {
                                FieldtypeExtendValue fieldtypeExtendValue = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendValue.class);
                                thirdFieldInfo.get(i).setFieldtypeExtendValue(fieldtypeExtendValue);
                            }
                            if (thirdFieldInfo.get(i).getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_MONEY)) {
                                FieldtypeExtendMoney fieldtypeExtendMoney = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendMoney.class);
                                thirdFieldInfo.get(i).setFieldtypeExtendMoney(fieldtypeExtendMoney);
                            }
                            if (thirdFieldInfo.get(i).getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_DT)) {
                                FieldtypeExtendDt fieldtypeExtendDt = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendDt.class);
                                thirdFieldInfo.get(i).setFieldtypeExtendDt(fieldtypeExtendDt);
                            }
                            if (thirdFieldInfo.get(i).getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_PERCENT)) {
                                FieldtypeExtendPercent fieldtypeExtendPercent = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendPercent.class);
                                thirdFieldInfo.get(i).setFieldtypeExtendPercent(fieldtypeExtendPercent);
                            }
                        }
                        break;
                    }
                }
            }
            return thirdFieldInfo;
        } else {
            String[] metaIds = thirdObjectExcelExportReq.getIds().split(",");
            for (String id : metaIds) {
                //业务对象字段信息
                ThirdFieldExcelimportVo thirdFieldInfo = thirdInfoDao.getThirdDataInfoById(Integer.valueOf(id));
                ///获取高级配置信息
                for (String s : extendCodeList) {
                    if (s.equals(thirdFieldInfo.getFieldTypeCode())) {
                        //通过字段id和字段类型code查询高级配置信息
                        JSONObject o = fieldTypeExtendDao.selectOneByTableName(Integer.valueOf(id), thirdFieldInfo.getFieldTypeCode());
                        if (null != o) {
                            String thirdimportVoStr = JSONObject.toJSON(o).toString();
                            if (thirdFieldInfo.getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_TEXT)) {
                                FieldtypeExtendText fieldtypeExtendText = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendText.class);
                                thirdFieldInfo.setFieldtypeExtendText(fieldtypeExtendText);
                            }
                            if (thirdFieldInfo.getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_VALUE)) {
                                FieldtypeExtendValue fieldtypeExtendValue = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendValue.class);
                                thirdFieldInfo.setFieldtypeExtendValue(fieldtypeExtendValue);
                            }
                            if (thirdFieldInfo.getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_MONEY)) {
                                FieldtypeExtendMoney fieldtypeExtendMoney = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendMoney.class);
                                thirdFieldInfo.setFieldtypeExtendMoney(fieldtypeExtendMoney);
                            }
                            if (thirdFieldInfo.getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_DT)) {
                                FieldtypeExtendDt fieldtypeExtendDt = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendDt.class);
                                thirdFieldInfo.setFieldtypeExtendDt(fieldtypeExtendDt);
                            }
                            if (thirdFieldInfo.getFieldTypeCode().equals(Constant.FILEID_TYPE_CODE_PERCENT)) {
                                FieldtypeExtendPercent fieldtypeExtendPercent = JSONObject.parseObject(thirdimportVoStr, FieldtypeExtendPercent.class);
                                thirdFieldInfo.setFieldtypeExtendPercent(fieldtypeExtendPercent);
                            }
                        }
                        break;
                    }
                }
                if (null == thirdFieldInfo) {
                    throw new BuilderException("业务对象id为:" + Integer.valueOf(id) + "的字段信息不存在");
                }
                thirdFieldList.add(thirdFieldInfo);
            }
            return thirdFieldList;
        }
    }

    /*
     *  获取业务字段导入错误报告
     *
     * @param fieldId 字段id
     */
    @Override
    public InputStream getThirdFieldImportDis(String fieldId) {
        if(StrUtil.isEmpty(fieldId)) throw new BuilderException("文件id不能为空！");
        // 根据文件id获取错误报告
        byte[] bytes = AwsS3Util.downloadFile(s3Config.getBucket(), fieldId);
        if (bytes == null) throw new BuilderException("文件不存在");
        return new ByteArrayInputStream(bytes);

    }
}
