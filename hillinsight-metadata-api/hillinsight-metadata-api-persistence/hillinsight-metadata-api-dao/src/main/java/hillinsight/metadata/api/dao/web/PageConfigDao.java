package hillinsight.metadata.api.dao.web;

import hillinsight.metadata.api.dto.web.PageGroupSysResult;
import hillinsight.metadata.api.dto.web.TemplateConfigInfoFieldResult;
import hillinsight.metadata.api.dto.web.TemplateConfigInfoResult;
import hillinsight.metadata.api.web.*;

import java.util.List;

public interface PageConfigDao {
    Integer addGroupInfo(MetaDataGroupInfo metaDataGroupInfo);

    void updateGroupInfo(MetaDataGroupInfo metaDataGroupInfo);

    List<MetaDataGroupInfo> getGroupListByCriteria(String pageGroup, String sourceId,String developerId);

    void addDeveloperInfo(MetaDataDeveloperInfo metaDataDeveloperInfo);

    void deleteDeveloperByGroupId(Integer id);

    List<MetaDataDeveloperInfo> getDeveloperListByGroupId(Integer id);

    void addPage(MetadataPageInfo metadataPageInfo);

    void updatePage(MetadataPageInfo metadataPageInfo);

    List<MetadataPageInfo> getPageListByCriteria(Integer groupId, String criteria);

    MetadataPageInfo getPageInfoById(Integer id);

    void delPage(Integer pageId);

    void addPageTemplateInfo(PageTemplate pageTemplate);

    void updCustomFieldShow(PageTemplate pageTemplate);

    MetaDataGroupInfo getGroupInfoById(Integer groupId);

    List<TemplateConfigInfoResult> getThirdObjIdByObjIdGroup(Integer pageId);

    List<TemplateConfigInfoFieldResult> getPageTempFieldDetailByThirdObjName(String thirdObjName,Integer pageId );

    List<PageTemplate> getPageTemplateListByPageId(Integer pageId);

    MetadataPageInfo getPageCondfigByGroupIdAndPageKey(Integer groupId, String pageKey);

    MetadataPageInfo getPageCondfigByDiffGroupIdAndPageKey(Integer groupId, String pageKey);

    PageTemplate getpageTempByPageIdAndObjNameAndFieName(Integer id, String thirdObjName, String thirdFieldName);

    void updatePageForImportByPageKey(MetadataPageInfo metadataPageInfo);

    void updatePageTemplateInfo(PageTemplate pageTemplate);

    List<ThirdObjectInfo> getThirdObjListForPageConfig(Integer pageId,String sourceId);

    void delPageTemplateFieldConfig(Integer id);

    List<ThirdFieldInfo> getThirdFieListForPageConfig(Integer pageId, String thirdObjName,Integer thirdObjId);

    List<PageGroupSysResult> getgroupListByDevelopId(String userCode);

    void delAllPageTemplateByPageId(Integer pageId);

    PageTemplate getPageTemplateByCriteria(PageTemplate pageTemplate);

    MetadataPageInfo getPageCondfigBySourceIdAndPageKey(String sourceId, String pageKey);

    List<PageTemplate> getPageTemplateListByPageIdAndThirdObjId(Integer pageId,Integer thirdObjId);

    List<PageTemplate> getPageTemplateListByPageIdAndThirdObjName(Integer pageId,String thirdObjName);

    List<PageTemplate> getPageTempByObjNameAndFieName(String thirdObjName, String thirdFieldName);

    MessageGroup getMsgGroupByKeyAndPageId(String messageGroupKey);

    List<MessageGroup> getMsgGroupListByPageId(Integer pageId);

    List<MessageGroupTemplate> getMsgGroupTempListByGroupKey(String messageGroupKey);

    void addMessageGroupInfo(MessageGroup messageGroup);

    void updateMessageGroupInfo(MessageGroup messageGroup);

    void deleteMsgGroupTemplateByKey(String messageGroupKey);

    MessageGroup getMsgGroupByKey(String messageGroupKey);

    void addMsgGroupTemplateInfo(MessageGroupTemplate messageGroupTemplate);

    void deleteMsgGroupByKeyAndPageId(String messageGroupKey, Integer pageId);

    void updateMsgGrpTempConfig(Integer id, String configJson);

    List<MessageGroup> getMsgGroupByPageId(Integer pageId,String messageGroupKey);

    List<MessageGroupTemplate> getMsgGroupTempByKey(String messageGroupKey);

    List<MessageGroupTemplate> getMsgGroupTempByIds(int idOne, int idTwo);

    void updateMsgGrpTempOrder(int idTwo, Integer orderNum);

    MessageGroupTemplate getMsgGroupTempById(int id);

    void deleteMsgGroupTempById(int tempId);

    MessageGroupTemplate getMsgTempByMsgGroupKeyAndObjNameAndFieName(String msgGroupKey, String thirdObjName, String thirdFieldName);

    void updateMsgGrpTemp(MessageGroupTemplate messageGroupTemplate);

    MessageGroupTemplate getMsgGroupTempByKeyAndOrderNum(String messageGroupKey,Integer orderNum);

    List<MessageGroupTemplate> getMsgGroupTempByThridObjAndField(String objectName, String fieldName);
}
