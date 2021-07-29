package hillinsight.metadata.api.service.web;

import com.alibaba.fastjson.JSONObject;
import focus.core.PagedResult;
import hillinsight.metadata.api.dto.web.*;
import hillinsight.metadata.api.dto.web.req.*;
import hillinsight.metadata.api.web.*;

import java.util.List;

/**
 * @ClassName PageConfigService
 * @Description TODO
 * @Author wcy
 * @Date 2020/11/26
 * @Version 1.0
 */
public interface PageConfigService {

    String addOrUpdateGroup(GroupAddOrUpdReq groupAddOrUpdReq);

    List<MetaDataGroupInfo> getGroupListByCriteria(GroupListRep groupListRep);

    String addOrUpdatePage(PageAddOrUpdReq pageAddOrUpdReq);

    PagedResult<MetadataPageInfo> getPageListByCriteria(PageListReq pageListReq);

    String delPage(DelPageInfoReq delPageInfoReq);

    String addPageTemplateInfo(PageTemplateAddReq pageTemplateAddReq);

    String updCustomFieldShow(PageTemplateAddReq pageTemplateAddReq);

    PageTemplateFieldDelResult getPageTempFieldDetail(PageTempFieldDetailReq pageTemplateAddReq);

    List<TemplateConfigInfoResult> getTemplateConfigInfo(DelPageInfoReq delPageInfoReq);

    List<ThirdObjectInfo> getThirdObjListForPageConfig(DelPageInfoReq delPageInfoReq);

    String delPageTemplateFieldConfig(DelPageInfoReq delPageInfoReq);

    List<ThirdFieldInfo> getThirdFieListForPageConfig(PageTempThirdFieldListReq pageTempThirdFieldListReq);

    List<PageGroupSysResult> getPageGroupSystem(String userCode);

    Integer addOrUpdateMsgGroupInfo(MessageGroupReq messageGroupReq);

    String addOrUpdateMsgGrpTempInfo(MessageGroupTempReq messageGroupTempReq);

    String deleteMsgGroupInfo(DelMsgGroupReq delMsgGroupReq);

    String updateMsgGrpTempConfig(UpdateMsgGrpTempConfigReq updateMsgGrpTempConfigReq);

    List<MsgGroupTempThirdListResult> getMsgGroupTempThirdList(MsgGroupTempThirdListReq msgGroupTempThirdListReq);

    List<MessageGroup> getMsgGroupTempList(DelPageInfoReq delPageInfoReq);

    String updateMsgGroupTempOrder(MsgGroupTempOrderReq msgGroupTempOrderReq);

    String getMsgGroupTempDetail(MsgGroupTempDetailReq msgGroupTempDetailReq);

    String deleteMsgGroupTemp(MsgGroupTempDetailReq msgGroupTempDetailReq);

    void importPage(Integer groupId, MetadataPageInfo metadataPageInfo, PageConfigImportResult pageConfigImportResult, boolean flag);

    boolean validatedImportPageInfo(PageConfigImportResult pageConfigImportResult, MetadataPageInfo metadataPageInfo, String sourceId);
}
