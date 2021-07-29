package hillinsight.metadata.api.dao.web;

import hillinsight.metadata.api.dto.web.MetaFieldListResult;
import hillinsight.metadata.api.models.vo.MetaFieldExcelimportVo;
import hillinsight.metadata.api.web.MetaDataFieldInfo;
import hillinsight.metadata.api.web.MetaDataObjectInfo;

import java.util.List;

public interface MetaDataInfoDao {

    void addObjectInfo(MetaDataObjectInfo metaDataObjectInfo);

    void updateObjectInfo(MetaDataObjectInfo metaDataObjectInfo);

    void addFieldInfo(MetaDataFieldInfo metaDataFieldInfo);

    void updateFieldInfo(MetaDataFieldInfo metaDataFieldInfo);

    List<MetaDataObjectInfo> getObjectList(String objectName);

    List<MetaDataFieldInfo> getFieldListByObjectId(Integer objectId,String criteria);

    MetaDataFieldInfo getFieldetailById(Integer fieldId);

    void fieldBlockUp(MetaDataFieldInfo metaDataFieldInfo);

    List<MetaFieldListResult> getMetaFieldList(String sourceId,Integer thirdObjId,Integer metaDataObjId);

    MetaDataObjectInfo getmetaDataObjInfoByName(String objectName);

    MetaDataFieldInfo getMetaFieldByName(String fieldName, Integer objectId);

    List<MetaDataObjectInfo> getMetaObjListForThirdObj(String sourceId);

    List<MetaDataObjectInfo> getMetaParentList();

    void ObjectUseOrBlockUp(MetaDataObjectInfo metaDataObjectInfo);

    MetaDataObjectInfo getObjectById(Integer objectId);

    List<MetaDataObjectInfo> getMetaObjListForThirdField(String sourceId,Integer thirdObjId);

    MetaFieldExcelimportVo getMetaDataInfoById(Integer id);

    List<MetaFieldExcelimportVo> getMetaDataInfo(Integer metaDataObjId);

    void deleteFieldById(Integer fieldId);
}
