package hillinsight.metadata.api.dao.impl.web;

import hillinsight.metadata.api.dao.web.ThirdInfoDao;
import hillinsight.metadata.api.dto.web.ThirdFieldResult;
import hillinsight.metadata.api.mappers.web.ThirdInfoMapper;
import hillinsight.metadata.api.models.vo.ThirdFieldExcelimportVo;
import hillinsight.metadata.api.web.ThirdFieldInfo;
import hillinsight.metadata.api.web.ThirdObjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ThirdInfoDaoImpl implements ThirdInfoDao{

    @Autowired
    private ThirdInfoMapper thirdInfoMapper;


    /**
     * 添加业务对象信息
     * @param thirdObjectInfo
     */
    @Override
    public void addThirdObjectInfo(ThirdObjectInfo thirdObjectInfo) {
        thirdInfoMapper.addThirdObjectInfo(thirdObjectInfo);
    }

    /**
     * 修改业务对象信息
     * @param thirdObjectInfo
     */
    @Override
    public void updateThirdObjectInfo(ThirdObjectInfo thirdObjectInfo) {
        thirdInfoMapper.updateThirdObjectInfo(thirdObjectInfo);

    }

    /**
     * 添加业务字段信息
     * @param thirdFieldInfo
     */
    @Override
    public void addThirdFieldInfo(ThirdFieldInfo thirdFieldInfo) {
        thirdInfoMapper.addThirdFieldInfo(thirdFieldInfo);
    }

    /**
     * 根据id获取业务对象信息
     * @param thirdObjectId
     * @return
     */
    @Override
    public ThirdObjectInfo getThirdObjectInfoById(Integer thirdObjectId) {
        return thirdInfoMapper.getThirdObjectInfoById(thirdObjectId);
    }
    /**
     * 根据id和名称获取业务对象信息
     * @param thirdObjectId
     * @return
     */
    @Override
    public ThirdObjectInfo getThirdObjectInfoByIdAndName(Integer thirdObjectId, String objectName) {
        return thirdInfoMapper.getThirdObjectInfoByIdAndName(thirdObjectId,objectName);
    }

    /**
     * 修改业务字段信息
     * @param thirdFieldInfo
     */
    @Override
    public void updateThirdFieldInfo(ThirdFieldInfo thirdFieldInfo) {
        thirdInfoMapper.updateThirdFieldInfo(thirdFieldInfo);

    }

    /**
     * 查询字段列表
     * @param thirdObjectId
     * @return
     */
    @Override
    public List<ThirdFieldInfo> getFieldListByObjId(Integer thirdObjectId,String criteria) {
        return thirdInfoMapper.getFieldListByObjId(thirdObjectId,criteria);
    }

    /**
     * 查询业务字段详情
     * @param thirdFieldId
     * @return
     */
    @Override
    public ThirdFieldInfo getFieldDetailById(Integer thirdFieldId) {
        return thirdInfoMapper.getFieldDetailById(thirdFieldId);
    }

    /**
     * 业务字段停用
     * @param thirdFieldInfo
     */
    @Override
    public void thirdfieldBlockUp(ThirdFieldInfo thirdFieldInfo) {
        thirdInfoMapper.thirdfieldBlockUp(thirdFieldInfo);
    }

    /**
     * 查询业务字段使用系统集合
     * @param id
     * @return
     */
    @Override
    public ThirdFieldResult getFieldSysListByMetaFieldId(Integer id) {
        return thirdInfoMapper.getFieldSysListByMetaFieldId(id);
    }

    /**
     * 根据业务对象名查询业务对象信息
     * @param objectName
     * @return
     */
    @Override
    public ThirdObjectInfo getThirdObjInfoByNameAndSourceId(String objectName,String sourceId) {
        return thirdInfoMapper.getThirdObjInfoByNameAndSourceId(objectName,sourceId);
    }

    /**
     * 根据业务字段名查询字段信息
     * @param fieldName
     * @param thirdObjectId
     * @return
     */
    @Override
    public ThirdFieldInfo getThirdFieldByName(String fieldName, Integer thirdObjectId) {
        return thirdInfoMapper.getThirdFieldByName(fieldName,thirdObjectId);
    }

    /**
     * 根据业务对象名称查询业务对象列表
     * @param sourceId
     * @return List集合
     */
    @Override
    public List<ThirdObjectInfo> getThirdObjectList(String sourceId,String criteria) {
        return thirdInfoMapper.getThirdObjectList(sourceId,criteria);
    }

    /**
     * 根据业务对象Id查询业务字段信息
     * @param thirdObjectId
     * @return
     */
    @Override
    public List<ThirdFieldInfo> getThirdFieldByObjId(Integer thirdObjectId) {
        return thirdInfoMapper.getThirdFieldByObjId(thirdObjectId);
    }

    /**
     * 根据业务对象Id和自定义字段名查询业务字段信息
     *
     * @param thirdObjId    业务对象id
     * @param extensionName 自定义名称
     * @return {@link ThirdFieldInfo}
     */
    @Override
    public ThirdFieldInfo getThirdFieldByObjIdAndExt(Integer thirdObjId, String extensionName) {
        return thirdInfoMapper.getThirdFieldByObjIdAndExt(thirdObjId, extensionName);
    }

    /**
     * 根据业务字段id和业务对象id查询字段信息
     *
     * @param thirdFieldjId 业务字段id
     * @param thirdObjId    业务对象id
     * @return {@link ThirdFieldInfo}
     */
    @Override
    public ThirdFieldInfo getThirdFieldByIdAndObjId(Integer thirdFieldjId, Integer thirdObjId) {
        return thirdInfoMapper.getThirdFieldByIdAndObjId(thirdFieldjId,thirdObjId);
    }

    /**
     * 获取业务系统父对象列表（新增业务对象时 绑定父对象使用）
     *
     * @return {@link List<ThirdObjectInfo>}
     */
    @Override
    public List<ThirdObjectInfo> getThirdParentObjList(String sourceId) {
        return thirdInfoMapper.getThirdParentObjList(sourceId);
    }

    /**
     * 业务对象启用/停用
     *
     * @param thirdObjectInfo 第三个对象信息
     */
    @Override
    public void thirdObjUseOrBlockUp(ThirdObjectInfo thirdObjectInfo) {
        thirdInfoMapper.thirdObjUseOrBlockUp(thirdObjectInfo);
    }


    /**
     * 根据业务对象id和元数据字段id获取业务字段信息
     *
     * @param thirdObjId 业务对象 id
     * @param id         元数据id
     * @return {@link ThirdFieldInfo}
     */
    @Override
    public ThirdFieldInfo getThirdFieldByThirdObjIdAndMetaFeildId(Integer thirdObjId, Integer id) {
        return thirdInfoMapper.getThirdFieldByThirdObjIdAndMetaFeildId(thirdObjId,id);
    }

    /**
     * 获取业务对象信息根据 对象名称和系统来源
     *
     * @param thirdObjName 第三obj的名字
     * @param sourceId     源id
     * @return {@link ThirdObjectInfo}
     */
    @Override
    public ThirdObjectInfo getThirdObjectInfoByNameAndSourceId(String thirdObjName, String sourceId) {
        return thirdInfoMapper.getThirdObjectInfoByNameAndSourceId(thirdObjName,  sourceId);
    }

    /**
     * 根据业务对象id和字段名称获取业务字段信息
     *
     * @param thirdFieldName 字段名
     * @param thirdObjId            业务对象id
     * @return {@link ThirdFieldInfo}
     */
    @Override
    public ThirdFieldInfo getThirdFieldByNameAndObjId(String thirdFieldName, Integer thirdObjId) {
        return thirdInfoMapper.getThirdFieldByNameAndObjId(thirdFieldName,  thirdObjId);
    }

    /**
     * 跟据业务字段名称和系统来源和业务对象id获取业务字段信息
     *
     * @param thirdFieldName 第三个字段名
     * @param sourceId       源id
     * @return {@link ThirdFieldInfo}
     */
    @Override
    public ThirdFieldInfo getFieldInfoByNameAndSourceIdAndObjId(String thirdFieldName, String sourceId,Integer thirdObjId) {
        return thirdInfoMapper.getFieldInfoByNameAndSourceIdAndObjId(thirdFieldName, sourceId, thirdObjId);
    }

    @Override
    public void deleteFieldById(Integer id) {
        thirdInfoMapper.deleteFieldById(id);
    }

    /**
     * 根据id导出业务对象Excel
     *
     * @param id 第三个字段名
     * @return {@link ThirdFieldExcelimportVo}
     */
    @Override
    public ThirdFieldExcelimportVo getThirdDataInfoById(Integer id) {
        return thirdInfoMapper.getThirdDataInfoById(id);
    }

    /**
     * 导出业务对象Excel
     *
     * @return {@link ThirdFieldExcelimportVo}
     */
    @Override
    public List<ThirdFieldExcelimportVo> getThirdDataInfoByUsercode(Integer thirdObjectId,String sourceId) {
        return thirdInfoMapper.getThirdDataInfoByUsercode(thirdObjectId,sourceId);
    }

    /**
     * 根据元数据字段id获取业务字段信息
     *
     * @param metaDataFieldId 元数据字段id
     * @return {@link List<ThirdFieldInfo>}
     */
    @Override
    public List<ThirdFieldInfo> getThirdFieldInfoByMetaFieldId(Integer metaDataFieldId) {
        return thirdInfoMapper.getThirdFieldInfoByMetaFieldId(metaDataFieldId);
    }

    @Override
    public List<ThirdObjectInfo> getThirdObjectInfoBySourceId(String sourceId) {
        return thirdInfoMapper.getThirdObjectInfoBySourceId(sourceId);
    }


}
