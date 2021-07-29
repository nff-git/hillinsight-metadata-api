package hillinsight.metadata.api.service.web;

import focus.core.PagedResult;
import hillinsight.metadata.api.dto.web.*;
import hillinsight.metadata.api.dto.web.req.*;
import hillinsight.metadata.api.web.MetaDataFieldInfo;
import hillinsight.metadata.api.web.MetaDataObjectInfo;

import java.util.List;

public interface MetaDataInfoService {

    String addOrUpdateObjectInfo(MetaAddOrUpdObjReq metaAddOrUpdObjReq);

    String addOrUpdateFieldInfo(MetaAddOrUpdFieReq metaAddOrUpdFieReq);

    List<MetaDataObjectInfo> getObjectList(MetaObjListFuzzyReq metaObjListFuzzyReq);

    PagedResult<MetaDataFieldInfo> getFieldListByObjectId(MetaFieListByObjIdReq metaFieListByObjIdReq);

    MetaDataFieldInfo getFieldetailById(MetaFieDetailReq metaFieDetailReq);

    String fieldBlockUp(MetaFieldBlockUpReq metaFieldBlockUpReq);

    List<MetaFieldListResult> getMetaFieldList(MetaObjListFuzzyReq metaObjListFuzzyReq);

    List<MetaDataObjectInfo> getMetaObjListForThirdObj(MetaObjListFuzzyReq metaObjListFuzzyReq);

    List<MetaDataObjectInfo> getMetaParentList();

    String ObjectUseOrBlockUp(MetaDataStatusReq metaDataStatusReq);

    List<MetaDataObjectInfo> getMetaObjListForThirdField(MetaObjListFuzzyReq metaObjListFuzzyReq);

    String deleteFieldById(MetaFieDetailReq metaFieDetailReq);
}
