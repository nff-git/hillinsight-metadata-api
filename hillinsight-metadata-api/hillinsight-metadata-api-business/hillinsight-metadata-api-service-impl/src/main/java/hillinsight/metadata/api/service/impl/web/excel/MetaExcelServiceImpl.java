package hillinsight.metadata.api.service.impl.web.excel;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import hillinsight.acs.api.sdk.RoleInfoResult;
import hillinsight.metadata.api.dao.web.MetaDataInfoDao;
import hillinsight.metadata.api.dto.web.req.MetaAddOrUpdFieReq;
import hillinsight.metadata.api.dto.web.MetaAndThirdImportResult;
import hillinsight.metadata.api.dto.web.req.MetaExcelExportReq;
import hillinsight.metadata.api.models.MetadataDict;
import hillinsight.metadata.api.models.vo.MetaFieldExcelimportCopy;
import hillinsight.metadata.api.models.vo.MetaFieldExcelimportVo;
import hillinsight.metadata.api.service.dict.MetaDataDictService;
import hillinsight.metadata.api.service.web.MetaDataInfoService;
import hillinsight.metadata.api.service.web.excel.MetaExcelService;
import hillinsight.metadata.api.utils.convention.Constant;
import hillinsight.metadata.api.utils.convention.UniqueUtils;
import hillinsight.metadata.api.utils.excel.ExcelExportUtil;
import hillinsight.metadata.api.utils.excel.FileUtils;
import hillinsight.metadata.api.utils.s3.AwsS3Util;
import hillinsight.metadata.api.utils.s3.S3Config;
import hillinsight.metadata.api.web.MetaDataFieldInfo;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * @ClassName MetaExcelServiceImpl
 * @Description TODO
 * @Author wcy
 * @Date 2020/11/19
 * @Version 1.0
 */

@Service
public class MetaExcelServiceImpl implements MetaExcelService {

    @Autowired
    private MetaDataDictService metaDataDictService;

    @Autowired
    private MetaDataInfoService metaDataInfoService;

    @Autowired
    private MetaDataInfoDao metaDataInfoDao;

    @Autowired
    private  S3Config s3Config;


    /**
     * 元数据字段导入
     *
     * @param metaFieldExcelimportVos 元数据字段导入vo集合对象
     * @return {@link MetaAndThirdImportResult}
     */
    @Override
    public MetaAndThirdImportResult metaDateExcelimport(List<MetaFieldExcelimportVo> metaFieldExcelimportVos,
                                                        Integer objectId, InputStream stream,Integer isSaveDb) {
        if (null == objectId) throw new BuilderException("对象id不能为空!");
        //isSaveDb 1第一次导入校验  2第二次导入 入库
        else if(null == isSaveDb) throw new BuilderException("导入状态不能为空");
        MetaAndThirdImportResult metaFieldImportResult = new MetaAndThirdImportResult();
        List<MetaDataFieldInfo> metaDataFieldInfos = checkoutExcelInfo(metaFieldImportResult, metaFieldExcelimportVos, objectId);
        //判断导入信息是否没有错误数据 有则返回错误报告 没有则成功
        if(null != metaFieldImportResult.getTotalCount() &&
                null != metaFieldImportResult.getSuccessCount() &&
                metaFieldImportResult.getTotalCount() ==  metaFieldImportResult.getSuccessCount()){
            if(isSaveDb == 2){
                for (MetaDataFieldInfo metaDataFieldInfo : metaDataFieldInfos) {
                    metaDataFieldInfo.setObjectId(objectId);
                    MetaAddOrUpdFieReq metaAddOrUpdFieReq = new MetaAddOrUpdFieReq();
                    metaAddOrUpdFieReq.setMetaDataFieldInfo(metaDataFieldInfo);
                    metaDataInfoService.addOrUpdateFieldInfo(metaAddOrUpdFieReq);
                }
            }
            metaFieldImportResult.setExSuccess(true);
        }else {
            //校验出错误数据  生成错误报告  存入文件服务器
            ExcelExportUtil.build(stream
                    , new String[]{
                            "idNum"
                            , "fieldName"
                            , "fieldShowCn"
                            , "fieldShowEn"
                            , "fieldTypeName"
                            , "fieldParaphraseCn"
                            , "fieldParaphraseEn"
                            , "dataOwnerId"
                            , "fillingExplanation"
                            , "misTakeMsg"
                    }, 5000);
            //copy导入数据
            List<MetaFieldExcelimportCopy> metaFieldExcelimportCopies = copyMetaFieldExInfo(metaFieldExcelimportVos,metaFieldImportResult);
            ExcelExportUtil.writeObject(metaFieldExcelimportCopies,metaFieldImportResult,Constant.META_OBJECT_TYPE);
            File metaFieldExFile = ExcelExportUtil.stop();
            //TODO 将导出文件放入到文件服务器中
            String fileKey = s3Config.getUploadKey() + UniqueUtils.getUUID();
            if (!AwsS3Util.uploadFileToS3(
                    s3Config.getBucket(), fileKey, FileUtils.getBytesByFile(metaFieldExFile))) throw new BuilderException("上传失败");
            metaFieldImportResult.setFieldId(fileKey);//返回给前端 文件存储id
        }
        return metaFieldImportResult;
    }



    /**
     * 复制元数据字段信息
     *
     * @param metaFieldExcelimportVos 元场excelimport vos
     * @return {@link List<MetaFieldExcelimportCopy>}
     */
    private List<MetaFieldExcelimportCopy> copyMetaFieldExInfo(List<MetaFieldExcelimportVo> metaFieldExcelimportVos,
                                                               MetaAndThirdImportResult metaFieldImportResult) {
        List<Map<String, Object>> failureList = metaFieldImportResult.getFailureList().get("meta");//错误集合
        List<MetaFieldExcelimportCopy> metaFieldExcelimportCopies = new ArrayList<>();
        for (MetaFieldExcelimportVo metaFieldExcelimportVo : metaFieldExcelimportVos) {
            MetaFieldExcelimportCopy metaFieldExcelimportCopy = new MetaFieldExcelimportCopy();//copy的实体
            BeanUtils.copyProperties(metaFieldExcelimportVo,metaFieldExcelimportCopy);//copy对象
            //向本次循环copy的对象中插入错误信息
            if(StrUtil.isEmpty(metaFieldExcelimportCopy.getIdNum())){//如果idNUm为空表示  idnum错误 直接插入
                metaFieldExcelimportCopy.setMisTakeMsg("idNum 为空值;");
            }else{
                for (Map<String, Object> map : failureList) {
                    for (String s : map.keySet()) {
                        if(metaFieldExcelimportCopy.getIdNum().equals(s)){//如果copy对象中的idNUm 可以匹配上错误集合中的map的key值  插入错误信息
                            metaFieldExcelimportCopy.setMisTakeMsg(map.get(s).toString());
                        }
                    }
                }
            }
            metaFieldExcelimportCopies.add(metaFieldExcelimportCopy);
        }
        return metaFieldExcelimportCopies;
    }


    /**
     * 校验导入数据 内容信息
     *
     * @param metaFieldImportResult   元领域导入结果
     * @param metaFieldExcelimportVos 元场excelimport vos
     * @return {@link List<MetaDataFieldInfo>}
     */
    private List<MetaDataFieldInfo> checkoutExcelInfo(MetaAndThirdImportResult metaFieldImportResult,
                                                      List<MetaFieldExcelimportVo> metaFieldExcelimportVos,
                                                      Integer objectId) {
        List<MetaDataFieldInfo> metaDataFieldInfos = new ArrayList<>();//验证通过数据 插入数据库集合
        Map<String, List<Map<String, Object>>>  failureMap = new HashMap<>();//失败返回错误信息集合
        List<Map<String, Object>> failureList = new ArrayList<>();//失败返回错误信息集合
        Map<String, MetaFieldExcelimportVo> idNumMap = new HashMap<>();//存储idNum的map
        for (MetaFieldExcelimportVo metaVo : metaFieldExcelimportVos) {
            boolean pass = false;//true表示通过 false 未通过
            StringBuffer errorMsg = new StringBuffer();
            MetadataDict metadataDict = null;
            if (StrUtil.isEmpty(metaVo.getIdNum())) {
                errorMsg.append("idNum 为空值;");
            } else if (StrUtil.isEmpty(metaVo.getFieldName())) {
                errorMsg.append("fieldName 为空值;");
            } else if (StrUtil.isEmpty(metaVo.getFieldShowCn())) {
                errorMsg.append("fieldShowCn 为空值;");
            } else if (StrUtil.isEmpty(metaVo.getFieldShowEn())) {
                errorMsg.append("fieldShowEn 为空值;");
            } else if (StrUtil.isEmpty(metaVo.getFieldTypeName())) {
                errorMsg.append("fieldTypeName 为空值;");
            } else if (StrUtil.isEmpty(metaVo.getFieldParaphraseCn())) {
                errorMsg.append("fieldParaphraseCn 为空值;");
            } else if (StrUtil.isEmpty(metaVo.getFieldParaphraseEn())) {
                errorMsg.append("fieldParaphraseEn 为空值;");
            } else {
                pass = true;
            }
            //去除字段名首尾空格
            if(StrUtil.isNotEmpty(metaVo.getFieldName())){
                metaVo.setFieldName(metaVo.getFieldName().trim());
            }
            //校验是否存在重复的编号
            if (StrUtil.isNotEmpty(metaVo.getIdNum())) {
                MetaFieldExcelimportVo idNumMapVo = idNumMap.get(metaVo.getIdNum());
                if (null != idNumMapVo) {//map中已经存在此key值
                    errorMsg.append("idNum为：「" + metaVo.getIdNum() + "」值重复;");
                    pass = false;
                } else idNumMap.put(metaVo.getIdNum(), metaVo);
            }
            //校验字段类型
            if (StrUtil.isNotEmpty(metaVo.getFieldTypeName())) {
                metadataDict = metaDataDictService.getDictByName(metaVo.getFieldTypeName(),
                        Constant.DICT_FIELD_TYPE);
                if (null == metadataDict) {
                    errorMsg.append("fieldTypeName 未查询到此字段类型名称;");
                    pass = false;
                }
            }
            //校验添加字段名是否重复
            MetaDataFieldInfo metaDataFieldInfoVo = metaDataInfoDao.getMetaFieldByName(metaVo.getFieldName(),
                    objectId);
            if (null != metaDataFieldInfoVo) {
                errorMsg.append("fieldName 字段名在数据库已存在;");
                pass = false;
            }
            //如果数据所有者不为空则校验数据所有者  TODO (需要改动调用acs  的 api)调用 权限接口根据id获取角色 判断是否存在此角色
            String dataOwnerName = "";
            if (StrUtil.isNotEmpty(metaVo.getDataOwnerId())) {
                //获取用户角色列表
                List<RoleInfoResult> roleInfoResults = metaVo.getRoleInfoResults();
                boolean roleFlag = false;
                for (RoleInfoResult roleInfoResult : roleInfoResults) {
                    if (null != metaVo.getDataOwnerId() && metaVo.getDataOwnerId().equals(roleInfoResult.getRoleCode())) {
                        dataOwnerName = roleInfoResult.getRoleName();
                        roleFlag = true;
                        break;
                    }
                }
                //判断新增的 角色id是否存在权限系统中
                if (!roleFlag) {
                    //不存在
                    errorMsg.append("dataOwnerId 未在权限系统中查询到此数据所有者id;");
                    pass = false;
                }
            }
            //单行和多行文本长度校验
             if(StrUtil.isNotEmpty(metaVo.getFieldName()) && metaVo.getFieldName().length() > 64){
                 errorMsg.append("fieldName 字段名称长度最长为64个字符;");
                 pass = false;
             }else if(StrUtil.isNotEmpty(metaVo.getFieldShowCn()) && metaVo.getFieldShowCn().length() > 64){
                 errorMsg.append("FieldShowCn 字段显示名中文长度最长为64个字符;");
                 pass = false;
             }else if(StrUtil.isNotEmpty(metaVo.getFieldShowEn()) && metaVo.getFieldShowEn().length() > 64){
                 errorMsg.append("FieldShowEn 字段显示名英文长度最长为64个字符;");
                 pass = false;
             }
//             else if(StrUtil.isNotEmpty(metaVo.getFieldParaphraseCn()) && metaVo.getFieldParaphraseCn().length() > 256){
//                 errorMsg.append("FieldParaphraseCn 字段释义中文长度最长为256个字符;");
//                 pass = false;
//             }
             else if(StrUtil.isNotEmpty(metaVo.getFillingExplanation()) && metaVo.getFillingExplanation().length() > 256){
                 errorMsg.append("FillingExplanation 填写说明长度最长为256个字符;");
                 pass = false;
             }
            //校验是否通过
            if (pass) {
                //向通过集合中插入 数据
                String metaimportVoStr = JSONObject.toJSON(metaVo).toString();
                MetaDataFieldInfo metaDataFieldInfo = JSONObject.parseObject(metaimportVoStr, MetaDataFieldInfo.class);
                //插入前做字段名是否已存在的数据校验
                boolean isRepetitionFieldName = false;//是否为重复字段名
                if (null != metaDataFieldInfos || metaDataFieldInfos.size() > 0) {
                    for (MetaDataFieldInfo dataFieldInfo : metaDataFieldInfos) {
                        if (StrUtil.isNotEmpty(dataFieldInfo.getFieldName()) &&
                                StrUtil.isNotEmpty(metaDataFieldInfo.getFieldName()) &&
                                dataFieldInfo.getFieldName().equals(metaDataFieldInfo.getFieldName())) {
                            //如果通过校验的数据集合中存在相同的字段名 则不插入此集合中
                            isRepetitionFieldName = true;
                            break;
                        }
                    }
                }
                if (!isRepetitionFieldName) {
                    metaDataFieldInfo.setFieldTypeCode(metadataDict.getCode());
                    metaDataFieldInfo.setDataOwnerName(dataOwnerName);//TODO 权限调用接入后 获取角色名称 修改此字段
                    metaDataFieldInfos.add(metaDataFieldInfo);
                } else {
                    //存在相同的字段名，向错误集合中插入此问题数据
                    Map<String, Object> errorMap = new HashMap<>();
                    errorMap.put(metaVo.getIdNum() == null ? "未知行号" : metaVo.getIdNum(), "fieldName 字段名在数据库已存在;");
                    failureList.add(errorMap);
                }
            } else {
                //向错误信息集合中插入数据
                Map<String, Object> errorMap = new HashMap<>();
                errorMap.put(metaVo.getIdNum() == null ? "未知行号" : metaVo.getIdNum(), errorMsg);
                failureList.add(errorMap);
            }
        }
        //构建返回数据
        metaFieldImportResult.setTotalCount(metaFieldExcelimportVos.size());
        metaFieldImportResult.setSuccessCount(metaDataFieldInfos.size());
        failureMap.put("meta",failureList);
        metaFieldImportResult.setFailureList(failureMap);

        return metaDataFieldInfos;
    }

    /**
     * 根据id查询对象管理信息
     *
     * @param metaExcelExportReq
     * @return {@link MetaFieldExcelimportVo}
     */
    @Override
    public List<MetaFieldExcelimportVo> getMetaDataInfoByIds(MetaExcelExportReq metaExcelExportReq) {
        List<MetaFieldExcelimportVo> metaFieldList = new ArrayList<>();
        //没有传id时导出所有字段
        if(StrUtil.isEmpty(metaExcelExportReq.getIds()) || metaExcelExportReq.getIds() == ""){
            if(null == metaExcelExportReq.getMetaDataObjId()){
                throw new BuilderException("元数据对象id为空");
            }
            List<MetaFieldExcelimportVo> metaFieldInfo = metaDataInfoDao.getMetaDataInfo(metaExcelExportReq.getMetaDataObjId());
            if (null == metaFieldInfo) {
                throw new BuilderException("对象管理的字段信息不存在");
            }
            return metaFieldInfo;
        }else{
            String[] metaIds = metaExcelExportReq.getIds().split(",");
            for (String id : metaIds) {
                MetaFieldExcelimportVo metaFieldInfo = metaDataInfoDao.getMetaDataInfoById(Integer.valueOf(id));
                if (null == metaFieldInfo) {
                    throw new BuilderException("对象管理id为:" + Integer.valueOf(id) + "的字段信息不存在");
                }
                metaFieldList.add(metaFieldInfo);
            }
            return metaFieldList;
        }

    }


    /**
     * 获取元数据导入错误报告
     *
     * @param fieldId 字段id
     */
    @Override
    public  InputStream getMetaFieldImportDis(String fieldId) {
        if(StrUtil.isEmpty(fieldId)) throw new BuilderException("文件id不能为空！");
        // 根据文件id获取错误报告
        byte[] bytes = AwsS3Util.downloadFile(s3Config.getBucket(), fieldId);
        if (bytes == null) throw new BuilderException("文件不存在");
        return new ByteArrayInputStream(bytes);
    }
}
