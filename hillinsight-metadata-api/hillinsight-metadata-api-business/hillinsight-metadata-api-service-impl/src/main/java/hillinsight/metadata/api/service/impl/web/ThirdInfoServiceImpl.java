package hillinsight.metadata.api.service.impl.web;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import focus.core.PagedResult;
import hillinsight.metadata.api.dao.web.FieldTypeExtendDao;
import hillinsight.metadata.api.dao.web.MetaDataInfoDao;
import hillinsight.metadata.api.dao.web.PageConfigDao;
import hillinsight.metadata.api.dao.web.ThirdInfoDao;
import hillinsight.metadata.api.dto.web.*;
import hillinsight.metadata.api.dto.web.req.*;
import hillinsight.metadata.api.models.MetadataDict;
import hillinsight.metadata.api.models.enums.StatusEnum;
import hillinsight.metadata.api.service.dict.MetaDataDictService;
import hillinsight.metadata.api.service.web.ThirdInfoService;
import hillinsight.metadata.api.utils.convention.Constant;
import hillinsight.metadata.api.utils.convention.PageTransferUtils;
import hillinsight.metadata.api.web.*;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class ThirdInfoServiceImpl implements ThirdInfoService {

    @Autowired
    private ThirdInfoDao thirdInfoDao;

    @Autowired
    private MetaDataInfoDao metaDataInfoDao;

    @Autowired
    private PageConfigDao pageConfigDao;

    @Autowired
    private MetaDataDictService metaDataDictService;

    @Autowired
    private FieldTypeExtendDao fieldTypeExtendDao;

    /**
     * 添加/修改业务对象信息
     *
     * @param thirdAddOrUpdObjReq
     * @return
     */
    @Override
    public String addOrUpdateThirdObjInfo(@Validated ThirdAddOrUpdObjReq thirdAddOrUpdObjReq) {
        ThirdObjectInfo thirdObjectInfo = thirdAddOrUpdObjReq.getThirdObjectInfo();
        if (null == thirdObjectInfo.getId()) {//添加
            //校验对象名是否存在
            ThirdObjectInfo thirdObjectInfoVa = thirdInfoDao.getThirdObjInfoByNameAndSourceId(thirdObjectInfo.getObjectName(),
                    thirdObjectInfo.getSourceId());
            if (null != thirdObjectInfoVa) {
                throw new BuilderException("对象名在数据库已存在，请重新填写！");
            }
            thirdObjectInfo.setStatus(Constant.METADATA_ONE);
            thirdInfoDao.addThirdObjectInfo(thirdObjectInfo);
        } else {//修改
            if (null == thirdObjectInfo.getStatus()) {
                throw new BuilderException("对象状态不能为空！");
            }
            thirdInfoDao.updateThirdObjectInfo(thirdObjectInfo);
        }
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 判断对象中属性值是否全为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 添加/修改业务字段信息
     *
     * @param thirdAddOrUpdFieReq
     * @return
     */
    @Override
    @Transactional
    public String addOrUpdateThirdField(@Validated ThirdAddOrUpdFieReq thirdAddOrUpdFieReq) {
        ThirdFieldInfo thirdFieldInfo = thirdAddOrUpdFieReq.getThirdFieldInfo();
        //清除字段名的首尾空格
        thirdFieldInfo.setFieldName(thirdFieldInfo.getFieldName().trim());
        //获取字典列表
        MetadataDict dict = metaDataDictService.
                getDictByCode(thirdFieldInfo.getFieldTypeCode(), Constant.DICT_FIELD_TYPE);
        if (null == dict) throw new BuilderException("字段类型在字典中不存在！");
        if (null == thirdFieldInfo.getId()) {//添加
            if (1 == thirdFieldInfo.getIsExtension() && StrUtil.isEmpty(thirdFieldInfo.getExtensionName()))
                throw new BuilderException("如选择自定义字段，字段名称不能为空！");
            else if (!dict.getName().equals("占位符") && null == thirdFieldInfo.getMetadataFieldId())
                throw new BuilderException("元数据字段id不能为空！");
            //校验新增字段名是否存在
            ThirdFieldInfo thirdFieldInfoVa = thirdInfoDao.getThirdFieldByName(thirdFieldInfo.getFieldName(),
                    thirdFieldInfo.getThirdObjectId());
            if (null != thirdFieldInfoVa) {
                throw new BuilderException("字段名在数据库已存在，请重新填写！");
            }
            thirdFieldInfo.setStatus(Constant.METADATA_ONE);
            thirdInfoDao.addThirdFieldInfo(thirdFieldInfo);
        } else {
            //修改
            thirdInfoDao.updateThirdFieldInfo(thirdFieldInfo);
        }
        //判断字段类型并做相应处理
        if ("金额".equals(dict.getName())) {
            FieldtypeExtendMoney fieldtypeExtendMoney = thirdAddOrUpdFieReq.getFieldtypeExtendMoney();
            // flag true代表对象及所有属性为空 false则代表有值
            boolean flag = checkObjAllFieldsIsNull(fieldtypeExtendMoney);
            if (!flag) {
                fieldtypeExtendMoney.setFieldId(thirdFieldInfo.getId());
                if (fieldtypeExtendMoney.getId() == null) {//新增金额扩展
                    fieldTypeExtendDao.insertMoney(fieldtypeExtendMoney);
                } else {//修改金额扩展
                    fieldTypeExtendDao.updateMoney(fieldtypeExtendMoney);
                }
            }
        } else if ("数值".equals(dict.getName())) {
            FieldtypeExtendValue fieldtypeExtendValue = thirdAddOrUpdFieReq.getFieldtypeExtendValue();
            // flag true代表对象及所有属性为空 false则代表有值
            boolean flag = checkObjAllFieldsIsNull(fieldtypeExtendValue);
            if (!flag) {
                fieldtypeExtendValue.setFieldId(thirdFieldInfo.getId());
                if (fieldtypeExtendValue.getId() == null) {//添加数值扩展表
                    fieldTypeExtendDao.insertValue(fieldtypeExtendValue);
                } else {//修改数值扩展表
                    fieldTypeExtendDao.updateValue(fieldtypeExtendValue);
                }
            }
        } else if ("百分比".equals(dict.getName())) {
            FieldtypeExtendPercent fieldtypeExtendPercent = thirdAddOrUpdFieReq.getFieldtypeExtendPercent();
            // flag true代表对象及所有属性为空 false则代表有值
            boolean flag = checkObjAllFieldsIsNull(fieldtypeExtendPercent);
            if (!flag) {
                fieldtypeExtendPercent.setFieldId(thirdFieldInfo.getId());
                if (fieldtypeExtendPercent.getId() == null) {//添加百分比扩展表
                    fieldTypeExtendDao.insertPercent(fieldtypeExtendPercent);
                } else {//修改百分比扩展表
                    fieldTypeExtendDao.updatePercent(fieldtypeExtendPercent);
                }
            }
        } else if ("文本".equals(dict.getName())) {
            FieldtypeExtendText fieldtypeExtendText = thirdAddOrUpdFieReq.getFieldtypeExtendText();
            // flag true代表对象及所有属性为空 false则代表有值
            boolean flag = checkObjAllFieldsIsNull(fieldtypeExtendText);
            if (!flag) {
                fieldtypeExtendText.setFieldId(thirdFieldInfo.getId());
                if (fieldtypeExtendText.getId() == null) {//添加文本扩展表
                    fieldTypeExtendDao.insertText(fieldtypeExtendText);
                } else {//修改文本扩展表
                    fieldTypeExtendDao.updateText(fieldtypeExtendText);
                }
            }
        } else if ("日期时间".equals(dict.getName())) {
            FieldtypeExtendDt fieldtypeExtendDt = thirdAddOrUpdFieReq.getFieldtypeExtendDt();
            // flag true代表对象及所有属性为空 false则代表有值
            boolean flag = checkObjAllFieldsIsNull(fieldtypeExtendDt);
            if (!flag) {
                fieldtypeExtendDt.setFieldId(thirdFieldInfo.getId());
                if (fieldtypeExtendDt.getId() == null) {//添加日期时间扩展表
                    fieldTypeExtendDao.insertDt(fieldtypeExtendDt);
                } else {//修改日期时间扩展表
                    fieldTypeExtendDao.updateDt(fieldtypeExtendDt);

                }
            }
        }

        return Constant.METADATA_SUCCESS_STRING;
    }


    /**
     * 根据业务对象id查询字段列表
     *
     * @param thirdFieListByObjIdReq 业务对象id
     * @return {@link List<ThirdFieldInfo>}
     */
    @Override
    public PagedResult<ThirdFieldInfo> getFieldListByObjId(@Validated ThirdFieListByObjIdReq thirdFieListByObjIdReq) {
        String criteria = thirdFieListByObjIdReq.getCriteria();
        if (StrUtil.isNotEmpty(criteria)) {
            criteria = "%" + criteria + "%";
        }
        List<ThirdFieldInfo> thirdFieldInfoList;
        //分页
        PageHelper.startPage(thirdFieListByObjIdReq.getPageIndex(), thirdFieListByObjIdReq.getPageSize());
        thirdFieldInfoList = thirdInfoDao.getFieldListByObjId(thirdFieListByObjIdReq.getThirdObjectId(), criteria);
        //获取元数据字段信息
        for (ThirdFieldInfo thirdFieldInfo : thirdFieldInfoList) {
            if (null != thirdFieldInfo.getMetadataFieldId()) {
                //获取绑定的元数据字段名
                MetaDataFieldInfo metaDataFieldInfo = getMetaDataFieldInfo(thirdFieldInfo.
                        getMetadataFieldId());
                thirdFieldInfo.setMetadataFieldName(metaDataFieldInfo.getFieldName());
            }
            //填充状态名称
            if (null != thirdFieldInfo.getStatus()) {
                thirdFieldInfo.setStatusName(StatusEnum.getValue(String.valueOf(thirdFieldInfo.getStatus())));
            }
        }
        return PageTransferUtils.pageInfoTransferResult(new PageInfo<ThirdFieldInfo>(thirdFieldInfoList));
    }


    /**
     * 获取业务字段详情
     *
     * @param thirdFieDetailReq 业务字段id
     * @return {@link ThirdFieldDetailResult}
     */
    @Override
    public ThirdFieldDetailResult getFieldDetailById(@Validated ThirdFieDetailReq thirdFieDetailReq) {
        Integer thirdFieldId = thirdFieDetailReq.getThirdFieldId();
        ThirdFieldInfo thirdFieldInfo = thirdInfoDao.getFieldDetailById(thirdFieldId);
        ThirdFieldDetailResult thirdFieldDetailResult = new ThirdFieldDetailResult();
        if (null != thirdFieldInfo) {
            //对象转换
            String thirdFieldResultStr = JSONObject.toJSON(thirdFieldInfo).toString();
            thirdFieldDetailResult = JSONObject.parseObject(thirdFieldResultStr, ThirdFieldDetailResult.class);
            if (null != thirdFieldInfo.getMetadataFieldId()) {
                //获取元数据字段信息
                MetaDataFieldInfo metaDataFieldInfo = getMetaDataFieldInfo(thirdFieldInfo.getMetadataFieldId());
                thirdFieldDetailResult.setMetaFieldName(metaDataFieldInfo.getFieldName());
                thirdFieldDetailResult.setMetaShowNameCn(metaDataFieldInfo.getFieldShowCn());
                thirdFieldDetailResult.setMetahSowNameEn(metaDataFieldInfo.getFieldShowEn());
                thirdFieldDetailResult.setDataOwnerId(metaDataFieldInfo.getDataOwnerId());
                thirdFieldDetailResult.setDataOwnerName(metaDataFieldInfo.getDataOwnerName());
            }
            //获取业务对象信息
            ThirdObjectInfo thirdObjectInfo = thirdInfoDao.getThirdObjectInfoById(
                    thirdFieldInfo.getThirdObjectId());
            thirdFieldDetailResult.setThirdObjectName(thirdObjectInfo.getObjectName());
            //获取高级配置信息
            List<String> extendCodeList = PageTransferUtils.buildExtendTableType();
            for (String s : extendCodeList) {
                if (s.equals(thirdFieldInfo.getFieldTypeCode())) {
                    JSONObject o = fieldTypeExtendDao.selectOneByTableName(thirdFieldInfo.getId(), thirdFieldInfo.getFieldTypeCode());
                    thirdFieldDetailResult.setFieldTypeExtendMap(PageTransferUtils.jsonCamelCasing(o));
                    break;
                }
            }
        }

        return thirdFieldDetailResult;
    }


    /**
     * 业务字段停用
     *
     * @param thirdFieldBlockUpReq 请求参数
     * @return
     */
    @Override
    public String thirdfieldBlockUp(@Validated ThirdFieldBlockUpReq thirdFieldBlockUpReq) {
        //实体转换
        String thirdFieldBlockUpReqStr = JSONObject.toJSON(thirdFieldBlockUpReq).toString();
        ThirdFieldInfo thirdFieldInfo = JSONObject.parseObject(thirdFieldBlockUpReqStr, ThirdFieldInfo.class);
        thirdInfoDao.thirdfieldBlockUp(thirdFieldInfo);
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 查询可用的自定义字段列表
     *
     * @param thirdFieListByObjIdReq
     * @return
     */
    @Override
    public List<String> getExtensionListByObjId(@Validated ThirdFieListByObjIdReq thirdFieListByObjIdReq) {
        List<String> resultList = new ArrayList<>();//返回结果信息
        List<ThirdFieldInfo> fieldInfos = thirdInfoDao.getFieldListByObjId(thirdFieListByObjIdReq.getThirdObjectId(), "");
        String[] extensionStr = Constant.EXTENSION_INFO.split(",");
        for (int i = 0; i < extensionStr.length; i++) {
            boolean flag = false;
            for (int j = 0; j < fieldInfos.size(); j++) {
                ThirdFieldInfo thirdFieldInfo = fieldInfos.get(j);
                if (StrUtil.isNotEmpty(thirdFieldInfo.getExtensionName()) &&
                        extensionStr[i].contentEquals(thirdFieldInfo.getExtensionName())) {
                    flag = true;
                    break;
                } else {
                    continue;
                }
            }
            if (!flag) {
                resultList.add(extensionStr[i]);
            }
        }
        return resultList;
    }

    /**
     * 获取业务对象列表
     *
     * @param thirdObjectListReq
     * @return
     */
    @Override
    public List<ThirdObjectInfo> getThirdObjectList(@Validated ThirdObjectListReq thirdObjectListReq) {
        String criteria = thirdObjectListReq.getCriteria();
        if (StrUtil.isNotEmpty(criteria)) {
            criteria = "%" + criteria + "%";
        }
        List<ThirdObjectInfo> thirdObjectList = thirdInfoDao.getThirdObjectList(thirdObjectListReq.getSourceId(), criteria);
        for (ThirdObjectInfo thirdObjectInfo : thirdObjectList) {
            //填充元数据对象信息
            MetaDataObjectInfo metaDataObjectInfo = metaDataInfoDao.getObjectById(thirdObjectInfo.getMetadataObjectId());
            if (null != metaDataObjectInfo) {
                thirdObjectInfo.setMetadataObjectName(metaDataObjectInfo.getObjectName());
                thirdObjectInfo.setMetadataObjectShowName(metaDataObjectInfo.getShowName());
            }
            //填充业务对象父对象
            ThirdObjectInfo thirdObjectInfoById = thirdInfoDao.getThirdObjectInfoById(thirdObjectInfo.getParentId());
            if (null != thirdObjectInfoById) {
                thirdObjectInfo.setParentObjName(thirdObjectInfoById.getObjectName());
                thirdObjectInfo.setParentObjShowNameCn(thirdObjectInfoById.getShowNameCn());
                thirdObjectInfo.setParentObjShowNameEn(thirdObjectInfoById.getShowNameEn());
            }
        }
        return thirdObjectList;
    }

    /**
     * 根据业务对象Id查询业务字段信息
     *
     * @param thirdObjectListReq
     * @return
     */
    @Override
    public List<ThirdFieldInfo> getThirdFieldByObjId(ThirdObjectListReq thirdObjectListReq) {

        return thirdInfoDao.getThirdFieldByObjId(thirdObjectListReq.getThirdObjectId());
    }

    /**
     * 获取业务系统父对象列表（新增业务对象时 绑定父对象使用）
     *
     * @return {@link List<ThirdObjectInfo>}
     */
    @Override
    public List<ThirdObjectInfo> getThirdParentObjList(@Validated ThirdObjectListReq thirdObjectListReq) {

        return thirdInfoDao.getThirdParentObjList(thirdObjectListReq.getSourceId());
    }


    /**
     * 业务对象启用/停用
     *
     * @param thirdObjBlockUpReq 第三obj阻碍点播
     * @return {@link String}
     */
    @Override
    public String ThirdObjUseOrBlockUp(ThirdObjBlockUpReq thirdObjBlockUpReq) {
        ThirdObjectInfo thirdObjectInfo = thirdObjBlockUpReq.getThirdObjectInfo();
        if (null == thirdObjectInfo.getId() || null == thirdObjectInfo.getStatus()) {
            throw new BuilderException("缺少参数！");
        } else if (0 != thirdObjectInfo.getStatus() && 1 != thirdObjectInfo.getStatus()) {
            throw new BuilderException("对象状态不正确");
        }
        thirdInfoDao.thirdObjUseOrBlockUp(thirdObjectInfo);
        return Constant.METADATA_SUCCESS_STRING;
    }


    /**
     * 删除的id字段
     *
     * @param thirdFieDetailReq 删除字段id
     * @return {@link String}
     */
    @Override
    public String deleteFieldById(@Validated ThirdFieDetailReq thirdFieDetailReq) {
        //校验删除字段是否绑定了 页面配置信息
        ThirdFieldInfo fieldDetailById1 = thirdInfoDao.getFieldDetailById(thirdFieDetailReq.getThirdFieldId());
        if (null == fieldDetailById1) throw new BuilderException("字段不存在！");
        ThirdObjectInfo thirdObjectInfoById = thirdInfoDao.getThirdObjectInfoById(fieldDetailById1.getThirdObjectId());
        if (null == thirdObjectInfoById) throw new BuilderException("字段所在的业务对象不存在");
        List<MessageGroupTemplate> messageGroupTemplates = pageConfigDao.getMsgGroupTempByThridObjAndField(thirdObjectInfoById.getObjectName(), fieldDetailById1.getFieldName());
        if (null != messageGroupTemplates && messageGroupTemplates.size() > 0)
            throw new BuilderException("该字段在页面配置中被引用！");
        //删除字段
        thirdInfoDao.deleteFieldById(thirdFieDetailReq.getThirdFieldId());
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 获取元数据字段信息
     *
     * @param metadataFieldId
     * @return
     */
    private MetaDataFieldInfo getMetaDataFieldInfo(Integer metadataFieldId) {
        return metaDataInfoDao.getFieldetailById(metadataFieldId);
    }
}
