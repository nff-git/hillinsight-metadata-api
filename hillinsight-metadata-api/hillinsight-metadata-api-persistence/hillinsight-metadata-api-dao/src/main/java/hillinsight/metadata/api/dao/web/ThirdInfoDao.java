package hillinsight.metadata.api.dao.web;

import hillinsight.metadata.api.dto.web.ThirdFieldResult;
import hillinsight.metadata.api.models.vo.ThirdFieldExcelimportVo;
import hillinsight.metadata.api.web.ThirdFieldInfo;
import hillinsight.metadata.api.web.ThirdObjectInfo;

import java.util.List;

public interface ThirdInfoDao {


    void addThirdObjectInfo(ThirdObjectInfo thirdObjectInfo);

    void updateThirdObjectInfo(ThirdObjectInfo thirdObjectInfo);

    void addThirdFieldInfo(ThirdFieldInfo thirdFieldInfo);

    ThirdObjectInfo getThirdObjectInfoById(Integer thirdObjectId);

    ThirdObjectInfo getThirdObjectInfoByIdAndName(Integer thirdObjectId,String objectName);

    void updateThirdFieldInfo(ThirdFieldInfo thirdFieldInfo);

    List<ThirdFieldInfo> getFieldListByObjId(Integer thirdObjectId,String criteria);

    ThirdFieldInfo getFieldDetailById(Integer thirdFieldId);

    void thirdfieldBlockUp(ThirdFieldInfo thirdFieldInfo);

    ThirdFieldResult getFieldSysListByMetaFieldId(Integer id);

    ThirdObjectInfo getThirdObjInfoByNameAndSourceId(String objectName, String sourceId);

    ThirdFieldInfo getThirdFieldByName(String fieldName, Integer thirdObjectId);

    List<ThirdObjectInfo> getThirdObjectList(String sourceId,String criteria);

    List<ThirdFieldInfo> getThirdFieldByObjId(Integer thirdObjectId);

    ThirdFieldInfo getThirdFieldByObjIdAndExt(Integer thirdObjId, String extensionName);

    ThirdFieldInfo getThirdFieldByIdAndObjId(Integer thirdFieldjId, Integer thirdObjId);

    List<ThirdObjectInfo> getThirdParentObjList(String sourceId);

    void thirdObjUseOrBlockUp(ThirdObjectInfo thirdObjectInfo);

    ThirdFieldInfo getThirdFieldByThirdObjIdAndMetaFeildId(Integer thirdObjId, Integer id);

    ThirdObjectInfo getThirdObjectInfoByNameAndSourceId(String thirdObjName, String sourceId);

    ThirdFieldInfo getThirdFieldByNameAndObjId(String thirdFieldName, Integer thirdObjId);

    ThirdFieldInfo getFieldInfoByNameAndSourceIdAndObjId(String thirdFieldName, String sourceId,Integer thirdObjId);

    void deleteFieldById(Integer id);

    ThirdFieldExcelimportVo getThirdDataInfoById(Integer id);

    List<ThirdFieldExcelimportVo> getThirdDataInfoByUsercode(Integer thirdObjectId,String sourceId);

    List<ThirdFieldInfo> getThirdFieldInfoByMetaFieldId(Integer metaDataFieldId);

    List<ThirdObjectInfo> getThirdObjectInfoBySourceId(String sourceId);
}
