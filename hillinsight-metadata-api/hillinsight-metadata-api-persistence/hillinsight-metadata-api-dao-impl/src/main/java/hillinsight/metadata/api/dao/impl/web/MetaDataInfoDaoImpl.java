package hillinsight.metadata.api.dao.impl.web;

import hillinsight.metadata.api.dao.web.MetaDataInfoDao;
import hillinsight.metadata.api.dto.web.MetaFieldListResult;
import hillinsight.metadata.api.mappers.web.MetaDataInfoMapper;
import hillinsight.metadata.api.models.vo.MetaFieldExcelimportVo;
import hillinsight.metadata.api.web.MetaDataFieldInfo;
import hillinsight.metadata.api.web.MetaDataObjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MetaDataInfoDaoImpl implements MetaDataInfoDao {

    @Autowired
    private MetaDataInfoMapper metaDataInfoMapper;

    /**
     * 添加元数据对象信息
     *
     * @param metaDataObjectInfo
     */
    @Override
    public void addObjectInfo(MetaDataObjectInfo metaDataObjectInfo) {
        metaDataInfoMapper.addObjectInfo(metaDataObjectInfo);

    }

    /**
     * 修改元数据对象信息
     *
     * @param metaDataObjectInfo
     */
    @Override
    public void updateObjectInfo(MetaDataObjectInfo metaDataObjectInfo) {
        metaDataInfoMapper.updateObjectInfo(metaDataObjectInfo);
    }

    /**
     * 添加元数据字段信息
     *
     * @param metaDataFieldInfo
     */
    @Override
    public void addFieldInfo(MetaDataFieldInfo metaDataFieldInfo) {
        metaDataInfoMapper.addFieldInfo(metaDataFieldInfo);
    }

    /**
     * 修改元数据字段信息
     *
     * @param metaDataFieldInfo
     */
    @Override
    public void updateFieldInfo(MetaDataFieldInfo metaDataFieldInfo) {
        metaDataInfoMapper.updateFieldInfo(metaDataFieldInfo);
    }

    /**
     * 模糊查询元数据对象列表
     *
     * @param objectName
     * @return
     */
    @Override
    public List<MetaDataObjectInfo> getObjectList(String objectName) {
        return metaDataInfoMapper.getObjectList(objectName);
    }

    /**
     * 根据元数据对象id查询字段列表
     *
     * @param objectId
     * @return
     */
    @Override
    public List<MetaDataFieldInfo> getFieldListByObjectId(Integer objectId,String criteria) {
        return metaDataInfoMapper.getFieldListByObjectId(objectId,criteria);
    }

    /**
     * 查询字段详情
     * @param fieldId
     * @return
     */
    @Override
    public MetaDataFieldInfo getFieldetailById(Integer fieldId) {
        return metaDataInfoMapper.getFieldetailById(fieldId);
    }

    /**
     * 字段停用
     * @param metaDataFieldInfo
     * @return
     */
    @Override
    public void fieldBlockUp(MetaDataFieldInfo metaDataFieldInfo) {
        metaDataInfoMapper.fieldBlockUp(metaDataFieldInfo);
    }
    /**
     * 查询元数据字段列表
     * @return
     */
    @Override
    public List<MetaFieldListResult> getMetaFieldList(String sourceId,Integer thirdObjId,Integer metaDataObjId) {
        return metaDataInfoMapper.getMetaFieldList(sourceId,thirdObjId,metaDataObjId);
    }

    /**
     * 根据对象名查询元数据对象信息
     * @param objectName
     * @return
     */
    @Override
    public MetaDataObjectInfo getmetaDataObjInfoByName(String objectName) {
        return metaDataInfoMapper.getmetaDataObjInfoByName(objectName);
    }

    /**
     * 根据元数据字段名查询字段信息
     * @param fieldName
     * @param objectId
     * @return
     */
    @Override
    public MetaDataFieldInfo getMetaFieldByName(String fieldName, Integer objectId) {
        return metaDataInfoMapper.getMetaFieldByName(fieldName,objectId);
    }

    /**
     * 获取元数据对象列表 （业务对象新增时绑定元数据对象使用）
     *
     * @return {@link List<MetaDataObjectInfo>}
     */
    @Override
    public List<MetaDataObjectInfo> getMetaObjListForThirdObj(String sourceId) {
        return metaDataInfoMapper.getMetaObjListForThirdObj(sourceId);
    }

    /**
     * 获取元数据父对象列表
     *
     * @return {@link List<MetaDataObjectInfo>}
     */
    @Override
    public List<MetaDataObjectInfo> getMetaParentList() {
        return metaDataInfoMapper.getMetaParentList();
    }

    @Override
    public void ObjectUseOrBlockUp(MetaDataObjectInfo metaDataObjectInfo) {
        metaDataInfoMapper.ObjectUseOrBlockUp(metaDataObjectInfo);
    }

    /**
     * 根据对象id获取元数据对象信息
     *
     * @return {@link List<MetaDataObjectInfo>}
     */
    @Override
    public MetaDataObjectInfo getObjectById(Integer objectId) {
        return metaDataInfoMapper.getObjectById(objectId);
    }

    /**
     * 获取元数据对象列表
     * //TODO 业务字段新增绑定元数据字段时使用
     * @return {@link List<MetaDataObjectInfo>}
     */
    @Override
    public List<MetaDataObjectInfo> getMetaObjListForThirdField(String sourceId,Integer thirdObjId) {
        return metaDataInfoMapper.getMetaObjListForThirdField(sourceId, thirdObjId);
    }

    /**
     * 根据id查询对象管理信息
     *
     * @return {@link List<MetaDataObjectInfo>}
     */
    @Override
    public MetaFieldExcelimportVo getMetaDataInfoById(Integer id) {
        return metaDataInfoMapper.getMetaDataInfoById(id);
    }

    /**
     * 查询对象管理信息
     *
     * @return {@link List<MetaDataObjectInfo>}
     */
    @Override
    public List<MetaFieldExcelimportVo> getMetaDataInfo(Integer metaDataObjId) {
        return metaDataInfoMapper.getMetaDataInfo(metaDataObjId);
    }

    /**
     * 删除的id字段
     *
     * @param fieldId 字段id
     */
    @Override
    public void deleteFieldById(Integer fieldId) {
        metaDataInfoMapper.deleteFieldById(fieldId);
    }

}
