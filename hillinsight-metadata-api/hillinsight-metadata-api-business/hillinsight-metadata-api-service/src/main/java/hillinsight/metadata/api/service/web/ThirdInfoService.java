package hillinsight.metadata.api.service.web;

import focus.core.PagedResult;
import hillinsight.metadata.api.dto.web.*;
import hillinsight.metadata.api.dto.web.req.*;
import hillinsight.metadata.api.web.ThirdFieldInfo;
import hillinsight.metadata.api.web.ThirdObjectInfo;

import java.util.List;

public interface ThirdInfoService {


    String addOrUpdateThirdObjInfo(ThirdAddOrUpdObjReq thirdAddOrUpdObjReq);

    String addOrUpdateThirdField(ThirdAddOrUpdFieReq thirdAddOrUpdFieReq);

    PagedResult<ThirdFieldInfo> getFieldListByObjId(ThirdFieListByObjIdReq thirdFieListByObjIdReq);

    ThirdFieldDetailResult getFieldDetailById(ThirdFieDetailReq thirdFieDetailReq);

    String thirdfieldBlockUp(ThirdFieldBlockUpReq thirdFieldBlockUpReq);

    List<String> getExtensionListByObjId(ThirdFieListByObjIdReq thirdFieListByObjIdReq);

    List<ThirdObjectInfo> getThirdObjectList(ThirdObjectListReq thirdObjectListReq);

    List<ThirdFieldInfo> getThirdFieldByObjId(ThirdObjectListReq thirdObjectListReq);

    List<ThirdObjectInfo> getThirdParentObjList(ThirdObjectListReq thirdObjectListReq);

    String ThirdObjUseOrBlockUp(ThirdObjBlockUpReq thirdObjBlockUpReq);

    String deleteFieldById(ThirdFieDetailReq thirdFieDetailReq);
}

