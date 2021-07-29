package hillinsight.metadata.api.service.impl.web;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import focus.core.PagedResult;
import hillinsight.acs.api.sdk.RoleInfoResult;
import hillinsight.metadata.api.dao.web.MetaDataInfoDao;
import hillinsight.metadata.api.dao.web.ThirdInfoDao;
import hillinsight.metadata.api.dto.web.*;
import hillinsight.metadata.api.dto.web.req.*;
import hillinsight.metadata.api.models.enums.StatusEnum;
import hillinsight.metadata.api.service.dict.MetaDataDictService;
import hillinsight.metadata.api.service.web.MetaDataInfoService;
import hillinsight.metadata.api.utils.convention.Constant;
import hillinsight.metadata.api.utils.convention.PageTransferUtils;
import hillinsight.metadata.api.web.MetaDataFieldInfo;
import hillinsight.metadata.api.web.MetaDataObjectInfo;
import hillinsight.metadata.api.web.ThirdFieldInfo;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
public class MetaDataInfoServiceImpl implements MetaDataInfoService {


    @Autowired
    private MetaDataInfoDao metaDataInfoDao;

    @Autowired
    private ThirdInfoDao thirdInfoDao;

    @Autowired
    private MetaDataDictService metaDataDictService;


    /**
     * 添加或更新元数据对象信息
     *
     * @param metaAddOrUpdObjReq 元数据对象请求参数
     * @return {@link String}
     */
    @Override
    public String addOrUpdateObjectInfo(@Validated MetaAddOrUpdObjReq metaAddOrUpdObjReq) {
        MetaDataObjectInfo metaDataObjectInfo = metaAddOrUpdObjReq.getMetaDataObjectInfo();
        if (null == metaDataObjectInfo.getId()) {//添加
            MetaDataObjectInfo metaDataObjectInfoVo = metaDataInfoDao.getmetaDataObjInfoByName(metaDataObjectInfo.getObjectName());
            if (null != metaDataObjectInfoVo) {
                throw new BuilderException("对象名在数据库已存在，请重新填写！");
            }
            metaDataObjectInfo.setStatus(Constant.METADATA_ONE);//默认启用
            metaDataInfoDao.addObjectInfo(metaDataObjectInfo);
        } else {
            if (null == metaDataObjectInfo.getStatus()) {
                throw new BuilderException("对象状态不能为空！");
            }
            metaDataInfoDao.updateObjectInfo(metaDataObjectInfo);
        }
        return Constant.METADATA_SUCCESS_STRING;
    }


    /**
     * 添加或更新字段信息
     *
     * @param metaAddOrUpdFieReq 元数据字段入参
     * @return {@link String}
     */
    @Override
    public String addOrUpdateFieldInfo(@Validated MetaAddOrUpdFieReq metaAddOrUpdFieReq) {
        MetaDataFieldInfo metaDataFieldInfo = metaAddOrUpdFieReq.getMetaDataFieldInfo();
        //清除字段名的首尾空格
        metaDataFieldInfo.setFieldName(metaDataFieldInfo.getFieldName().trim());
        if (null == metaDataFieldInfo.getId()) {//添加
            //校验新增字段名是否存在
            MetaDataFieldInfo metaDataFieldInfoVo = metaDataInfoDao.getMetaFieldByName(metaDataFieldInfo.getFieldName(),
                    metaDataFieldInfo.getObjectId());
            if (null != metaDataFieldInfoVo) {
                throw new BuilderException("字段名在数据库已存在，请重新填写！");
            }
            metaDataFieldInfo.setStatus(Constant.METADATA_ONE);//使用
            metaDataInfoDao.addFieldInfo(metaDataFieldInfo);
        } else {
            metaDataInfoDao.updateFieldInfo(metaDataFieldInfo);
        }
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 模糊查询元数据对象列表
     *
     * @param metaObjListFuzzyReq
     * @return
     */
    @Override
    public List<MetaDataObjectInfo> getObjectList(MetaObjListFuzzyReq metaObjListFuzzyReq) {
        String objectName = metaObjListFuzzyReq.getObjectName();
        if (StrUtil.isNotEmpty(objectName)) {
            objectName = "%" + objectName + "%";
        }
        List<MetaDataObjectInfo> metaDataObjectInfoList = metaDataInfoDao.getObjectList(objectName);
        for (MetaDataObjectInfo metaDataObjectInfo : metaDataObjectInfoList) {
            //填充父对象信息
            MetaDataObjectInfo metaObjParentInfo = metaDataInfoDao.getObjectById(metaDataObjectInfo.getParentId());
            if(null != metaObjParentInfo){
                metaDataObjectInfo.setParentObjName(metaObjParentInfo.getObjectName());
                metaDataObjectInfo.setParentObjShowName(metaObjParentInfo.getShowName());
            }
        }
        return metaDataObjectInfoList;
    }


    /**
     * 查询元数据字段列表
     *
     * @param metaFieListByObjIdReq 获取字段列表请求参入
     * @return {@link List<MetaDataFieldInfo>}
     */
    @Override
    public PagedResult<MetaDataFieldInfo> getFieldListByObjectId(@Validated MetaFieListByObjIdReq metaFieListByObjIdReq) {
        String criteria = metaFieListByObjIdReq.getCriteria();
        List<RoleInfoResult> roles = metaFieListByObjIdReq.getRoles();
        if (null == roles || roles.size() < 1) {
            throw new BuilderException("该用户暂未分配角色，请到权限系统分配角色！");
        } else if (StrUtil.isNotEmpty(criteria)) {
            criteria = "%" + criteria + "%";
        }
        //分页
        PageHelper.startPage(metaFieListByObjIdReq.getPageIndex(), metaFieListByObjIdReq.getPageSize());
        //根据对象id获取字段列表
        List<MetaDataFieldInfo> metaDataFieldInfoList = metaDataInfoDao.
                getFieldListByObjectId(metaFieListByObjIdReq.getObjectId(), criteria);
       /* // 根据用户所拥有的角色 过滤数据
        for (int i = 0; i < metaDataFieldInfoList.size(); i++) {
            MetaDataFieldInfo metaDataFieldInfo = metaDataFieldInfoList.get(i);
            boolean flag = false;//不存在
            for (RoleInfoResult role : roles) {
                if (StrUtil.isEmpty(metaDataFieldInfo.getDataOwnerId()) || role.getRoleCode().equals(metaDataFieldInfo.getDataOwnerId())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                //删除列表数据
                metaDataFieldInfoList.remove(i);
                i--;
            }
        }*/
        for (MetaDataFieldInfo metaDataFieldInfo : metaDataFieldInfoList) {
            //根据字段id查询业务字段中绑定元数据字段并返回所使用的系统
            ThirdFieldResult thirdFieldResult = thirdInfoDao.
                    getFieldSysListByMetaFieldId(metaDataFieldInfo.getId());
            if (!StrUtil.isBlankIfStr(thirdFieldResult)) {
                metaDataFieldInfo.setSource(thirdFieldResult.getItems());
            }
            //补充状态名称
            if (null != metaDataFieldInfo.getStatus()) {
                metaDataFieldInfo.setStatusName(StatusEnum.getValue(String.valueOf(metaDataFieldInfo.getStatus())));
            }
        }
        if (metaDataFieldInfoList.size() < 1) {
            PagedResult<MetaDataFieldInfo> pagedResult = new PagedResult<>();
            pagedResult.setPageIndex(metaFieListByObjIdReq.getPageIndex());
            pagedResult.setPageSize(metaFieListByObjIdReq.getPageSize());
            return pagedResult;
        }
        return PageTransferUtils.pageInfoTransferResult(new PageInfo<>(metaDataFieldInfoList));

    }

    /**
     * 查询字段详情
     *
     * @param metaFieDetailReq
     * @return
     */
    @Override
    public MetaDataFieldInfo getFieldetailById(@Validated MetaFieDetailReq metaFieDetailReq) {
        Integer fieldId = metaFieDetailReq.getFieldId();
        MetaDataFieldInfo metaDataFieldInfo = metaDataInfoDao.getFieldetailById(fieldId);
        //通过字段id查询 此字段在业务字段表中 的使用系统
        ThirdFieldResult thirdFieldResult = thirdInfoDao.getFieldSysListByMetaFieldId(fieldId);
        if (!StrUtil.isBlankIfStr(thirdFieldResult)) {
            metaDataFieldInfo.setSource(thirdFieldResult.getItems());
        }
        //填充字段状态名称
        if (!StrUtil.isBlankIfStr(metaDataFieldInfo) && null != metaDataFieldInfo.getStatus()) {
            metaDataFieldInfo.setStatusName(StatusEnum.getValue(String.valueOf(metaDataFieldInfo.getStatus())));
        }
        //填充对应的对象名称
        MetaDataObjectInfo metaDataObjectInfo = metaDataInfoDao.getObjectById(metaDataFieldInfo.getObjectId());
        if(null != metaDataObjectInfo){
            metaDataFieldInfo.setObjectName(metaDataObjectInfo.getObjectName());
            metaDataFieldInfo.setObjStatus(metaDataObjectInfo.getStatus());
        }
        return metaDataFieldInfo;
    }

    /**
     * 字段停用
     *
     * @param metaFieldBlockUpReq
     * @return
     */
    @Override
    public String fieldBlockUp(@Validated MetaFieldBlockUpReq metaFieldBlockUpReq) {
        //对象转换
        String metaFieldBlockUpReqStr = JSONObject.toJSON(metaFieldBlockUpReq).toString();
        MetaDataFieldInfo metaDataFieldInfo = JSONObject.parseObject(metaFieldBlockUpReqStr, MetaDataFieldInfo.class);
        metaDataInfoDao.fieldBlockUp(metaDataFieldInfo);
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 查询元数据字段列表（业务字段绑定元数据字段时使用）
     *
     * @param metaObjListFuzzyReq thirdObjid sourceId
     * @return {@link List<MetaFieldListResult>}
     */
    @Override
    public List<MetaFieldListResult> getMetaFieldList(MetaObjListFuzzyReq metaObjListFuzzyReq) {
        if (StrUtil.isEmpty(metaObjListFuzzyReq.getSourceId())) {
            throw new BuilderException("系统来源不能为空！");
        } else if (null == metaObjListFuzzyReq.getThirdObjId()) {
            throw new BuilderException("业务对象id不能为空！");
        }else if (null == metaObjListFuzzyReq.getMetaDataObjId()) {
            throw new BuilderException("元数据对象id不能为空！");
        }
        return metaDataInfoDao.getMetaFieldList(metaObjListFuzzyReq.getSourceId(), metaObjListFuzzyReq.getThirdObjId(),metaObjListFuzzyReq.getMetaDataObjId());
    }

    /**
     * 获取元数据对象列表 （业务对象新增时绑定元数据对象使用）
     *
     * @return {@link List<MetaDataObjectInfo>}
     */
    @Override
    public List<MetaDataObjectInfo> getMetaObjListForThirdObj(MetaObjListFuzzyReq metaObjListFuzzyReq) {
        if (StrUtil.isEmpty(metaObjListFuzzyReq.getSourceId())) {
            throw new BuilderException("系统来源不能为空！");
        }
        return metaDataInfoDao.getMetaObjListForThirdObj(metaObjListFuzzyReq.getSourceId());
    }

    /**
     * 获取元数据父对象列表
     *
     * @return {@link List<MetaDataObjectInfo>}
     */
    @Override
    public List<MetaDataObjectInfo> getMetaParentList() {
        return metaDataInfoDao.getMetaParentList();
    }


    /**
     * 对象启用/停用
     *
     * @param metaDataStatusReq 元数据状态要求
     * @return {@link String}
     */
    @Override
    public String ObjectUseOrBlockUp(MetaDataStatusReq metaDataStatusReq) {
        MetaDataObjectInfo metaDataObjectInfo = metaDataStatusReq.getMetaDataObjectInfo();
        if (null == metaDataObjectInfo.getId() || null == metaDataObjectInfo.getStatus()) {
            throw new BuilderException("缺少参数！");
        } else if (0 != metaDataObjectInfo.getStatus() && 1 != metaDataObjectInfo.getStatus()) {
            throw new BuilderException("对象状态不正确");
        }
        metaDataInfoDao.ObjectUseOrBlockUp(metaDataObjectInfo);
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 获取元数据对象列表
     * //TODO 业务字段新增绑定元数据字段时使用
     *
     * @return {@link Object}
     */
    @Override
    public List<MetaDataObjectInfo> getMetaObjListForThirdField(MetaObjListFuzzyReq metaObjListFuzzyReq) {
        if(null == metaObjListFuzzyReq.getThirdObjId()){
            throw  new BuilderException("业务对象id不能为空！");
        }else if(null == metaObjListFuzzyReq.getSourceId()){
            throw  new BuilderException("系统来源id不能为空！");
        }
        return metaDataInfoDao.getMetaObjListForThirdField(metaObjListFuzzyReq.getSourceId(),metaObjListFuzzyReq.getThirdObjId());
    }

    /**
     * 删除的id字段
     *
     * @param metaFieDetailReq 元5细节要求
     * @return {@link String}
     */
    @Override
    public String deleteFieldById(@Validated MetaFieDetailReq metaFieDetailReq) {
        //校验要删除的字段 是否被 业务对象中使用
        List<ThirdFieldInfo> thirdFieldInfoByMetaFieldId = thirdInfoDao.getThirdFieldInfoByMetaFieldId(metaFieDetailReq.getFieldId());
        if(null != thirdFieldInfoByMetaFieldId && thirdFieldInfoByMetaFieldId.size() > 0 ) throw new BuilderException("该字段已在业务对象中被引用！");
        //删除字段
        metaDataInfoDao.deleteFieldById(metaFieDetailReq.getFieldId());

        return Constant.METADATA_SUCCESS_STRING;
    }

}
