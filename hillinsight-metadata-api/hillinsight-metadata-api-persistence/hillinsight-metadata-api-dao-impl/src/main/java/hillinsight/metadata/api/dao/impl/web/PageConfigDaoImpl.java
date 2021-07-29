package hillinsight.metadata.api.dao.impl.web;

import hillinsight.metadata.api.dao.web.PageConfigDao;
import hillinsight.metadata.api.dto.web.PageGroupSysResult;
import hillinsight.metadata.api.dto.web.TemplateConfigInfoFieldResult;
import hillinsight.metadata.api.dto.web.TemplateConfigInfoResult;
import hillinsight.metadata.api.mappers.web.PageConfigMapper;
import hillinsight.metadata.api.web.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName PageConfigDaoImpl
 * @Description TODO
 * @Author wcy
 * @Date 2020/11/26
 * @Version 1.0
 */
@Repository
public class PageConfigDaoImpl implements PageConfigDao
{

    @Autowired
    private PageConfigMapper pageConfigMapper;

    @Override
    public Integer addGroupInfo(MetaDataGroupInfo metaDataGroupInfo) {
        return pageConfigMapper.addGroupInfo(metaDataGroupInfo);
    }

    @Override
    public void updateGroupInfo(MetaDataGroupInfo metaDataGroupInfo) {
        pageConfigMapper.updateGroupInfo(metaDataGroupInfo);
    }

    @Override
    public List<MetaDataGroupInfo> getGroupListByCriteria(String pageGroup, String sourceId,String developerId) {
        return pageConfigMapper.getGroupListByCriteria(pageGroup,sourceId,developerId);
    }

    @Override
    public void addDeveloperInfo(MetaDataDeveloperInfo metaDataDeveloperInfo) {
        pageConfigMapper.addDeveloperInfo(metaDataDeveloperInfo);
    }

    @Override
    public void deleteDeveloperByGroupId(Integer id) {
        pageConfigMapper.deleteDeveloperByGroupId(id);
    }

    @Override
    public List<MetaDataDeveloperInfo> getDeveloperListByGroupId(Integer id) {
        return pageConfigMapper.getDeveloperListByGroupId(id);
    }

    @Override
    public void addPage(MetadataPageInfo metadataPageInfo) {
        pageConfigMapper.addPage(metadataPageInfo);
    }

    @Override
    public void updatePage(MetadataPageInfo metadataPageInfo) {
        pageConfigMapper.updatePage(metadataPageInfo);
    }

    @Override
    public List<MetadataPageInfo> getPageListByCriteria(Integer groupId, String criteria) {
        return pageConfigMapper.getPageListByCriteria(groupId,criteria);
    }

    @Override
    public MetadataPageInfo getPageInfoById(Integer id) {
        return pageConfigMapper.getPageInfoById(id);
    }

    @Override
    public void delPage(Integer pageId) {
        pageConfigMapper.delPage(pageId);
    }

    @Override
    public void addPageTemplateInfo(PageTemplate pageTemplate) {
        pageConfigMapper.addPageTemplateInfo(pageTemplate);
    }

    @Override
    public void updCustomFieldShow(PageTemplate pageTemplate) {
        pageConfigMapper.updCustomFieldShow(pageTemplate);
    }

    @Override
    public MetaDataGroupInfo getGroupInfoById(Integer groupId) {
        return pageConfigMapper.getGroupInfoById(groupId);
    }

    @Override
    public List<TemplateConfigInfoResult> getThirdObjIdByObjIdGroup(Integer pageId) {
        return pageConfigMapper.getThirdObjIdByObjIdGroup(pageId);
    }

    @Override
    public List<TemplateConfigInfoFieldResult> getPageTempFieldDetailByThirdObjName(String  thirdObjName,Integer pageId ) {
        return pageConfigMapper.getPageTempFieldDetailByThirdObjName(thirdObjName,pageId);
    }

    @Override
    public List<PageTemplate> getPageTemplateListByPageId(Integer pageId) {
        return pageConfigMapper.getPageTemplateListByPageId(pageId);
    }

    @Override
    public MetadataPageInfo getPageCondfigByGroupIdAndPageKey(Integer groupId, String pageKey) {
        return pageConfigMapper.getPageCondfigByGroupIdAndPageKey(groupId,pageKey);
    }

    @Override
    public MetadataPageInfo getPageCondfigByDiffGroupIdAndPageKey(Integer groupId, String pageKey) {
        return pageConfigMapper.getPageCondfigByDiffGroupIdAndPageKey(groupId,pageKey);
    }

    @Override
    public PageTemplate getpageTempByPageIdAndObjNameAndFieName(Integer id,String thirdObjName, String thirdFieldName) {
        return pageConfigMapper.getpageTempByPageIdAndObjNameAndFieName(id,thirdObjName,thirdFieldName);
    }

    @Override
    public void updatePageForImportByPageKey(MetadataPageInfo metadataPageInfo) {
        pageConfigMapper.updatePageForImportByPageKey(metadataPageInfo);
    }

    @Override
    public void updatePageTemplateInfo(PageTemplate pageTemplate) {
        pageConfigMapper.updatePageTemplateInfo(pageTemplate);
    }

    @Override
    public List<ThirdObjectInfo> getThirdObjListForPageConfig(Integer pageId,String sourceId) {
        return pageConfigMapper.getThirdObjListForPageConfig(pageId, sourceId);
    }

    @Override
    public void delPageTemplateFieldConfig(Integer id) {
        pageConfigMapper.delPageTemplateFieldConfig(id);
    }

    @Override
    public List<ThirdFieldInfo> getThirdFieListForPageConfig(Integer pageId, String thirdObjName,Integer thirdObjId) {
        return pageConfigMapper.getThirdFieListForPageConfig(pageId,thirdObjName, thirdObjId);
    }

    @Override
    public List<PageGroupSysResult> getgroupListByDevelopId(String userCode) {
        return pageConfigMapper.getgroupListByDevelopId(userCode);
    }

    @Override
    public void delAllPageTemplateByPageId(Integer pageId) {
        pageConfigMapper.delAllPageTemplateByPageId(pageId);
    }

    @Override
    public PageTemplate getPageTemplateByCriteria(PageTemplate pageTemplate) {
        return pageConfigMapper.getPageTemplateByCriteria(pageTemplate);
    }

    @Override
    public MetadataPageInfo getPageCondfigBySourceIdAndPageKey(String sourceId, String pageKey) {
        return pageConfigMapper.getPageCondfigBySourceIdAndPageKey(sourceId,pageKey);
    }

    @Override
    public List<PageTemplate> getPageTemplateListByPageIdAndThirdObjId(Integer pageId, Integer thirdObjId) {
        return pageConfigMapper.getPageTemplateListByPageIdAndThirdObjId(pageId,thirdObjId);
    }

    @Override
    public List<PageTemplate> getPageTemplateListByPageIdAndThirdObjName(Integer pageId, String thirdObjName) {
        return pageConfigMapper.getPageTemplateListByPageIdAndThirdObjName(pageId,  thirdObjName);
    }

    @Override
    public List<PageTemplate> getPageTempByObjNameAndFieName(String thirdObjName, String thirdFieldName) {
        return pageConfigMapper.getPageTempByObjNameAndFieName(thirdObjName,thirdFieldName);
    }

    @Override
    public MessageGroup getMsgGroupByKeyAndPageId(String messageGroupKey) {
        return pageConfigMapper.getMsgGroupByKeyAndPageId(messageGroupKey);
    }

    @Override
    public List<MessageGroup> getMsgGroupListByPageId(Integer pageId) {
        return pageConfigMapper.getMsgGroupListByPageId(pageId);
    }

    @Override
    public List<MessageGroupTemplate> getMsgGroupTempListByGroupKey(String messageGroupKey) {
        return pageConfigMapper.getMsgGroupTempListByGroupKey(messageGroupKey);
    }

    @Override
    public void addMessageGroupInfo(MessageGroup messageGroup) {
        pageConfigMapper.addMessageGroupInfo(messageGroup);
    }

    @Override
    public void updateMessageGroupInfo(MessageGroup messageGroup) {
        pageConfigMapper.updateMessageGroupInfo(messageGroup);
    }

    @Override
    public void deleteMsgGroupTemplateByKey(String messageGroupKey) {
        pageConfigMapper.deleteMsgGroupTemplateByKey(messageGroupKey);
    }

    @Override
    public MessageGroup getMsgGroupByKey(String messageGroupKey) {
        return pageConfigMapper.getMsgGroupByKey(messageGroupKey);
    }

    @Override
    public void addMsgGroupTemplateInfo(MessageGroupTemplate messageGroupTemplate) {
        pageConfigMapper.addMsgGroupTemplateInfo(messageGroupTemplate);
    }

    @Override
    public void deleteMsgGroupByKeyAndPageId(String messageGroupKey, Integer pageId) {
        pageConfigMapper.deleteMsgGroupByKeyAndPageId(messageGroupKey, pageId);
    }

    @Override
    public void updateMsgGrpTempConfig(Integer id, String configJson) {
        pageConfigMapper.updateMsgGrpTempConfig(id,configJson);
    }

    @Override
    public List<MessageGroup> getMsgGroupByPageId(Integer pageId,String messageGroupKey) {
        return pageConfigMapper.getMsgGroupByPageId(pageId,messageGroupKey);
    }

    @Override
    public List<MessageGroupTemplate> getMsgGroupTempByKey(String messageGroupKey) {
        return pageConfigMapper.getMsgGroupTempByKey(messageGroupKey);
    }

    @Override
    public List<MessageGroupTemplate> getMsgGroupTempByIds(int idOne, int idTwo) {
        return pageConfigMapper.getMsgGroupTempByIds(idOne,idTwo);
    }

    @Override
    public void updateMsgGrpTempOrder(int id, Integer orderNum) {
        pageConfigMapper.updateMsgGrpTempOrder(id,orderNum);
    }

    @Override
    public MessageGroupTemplate getMsgGroupTempById(int id) {
        return pageConfigMapper.getMsgGroupTempById(id);
    }

    @Override
    public void deleteMsgGroupTempById(int tempId) {
        pageConfigMapper.deleteMsgGroupTempById(tempId);
    }

    @Override
    public MessageGroupTemplate getMsgTempByMsgGroupKeyAndObjNameAndFieName(String msgGroupKey, String thirdObjName, String thirdFieldName) {
        return pageConfigMapper.getMsgTempByMsgGroupKeyAndObjNameAndFieName(msgGroupKey, thirdObjName, thirdFieldName);
    }

    @Override
    public void updateMsgGrpTemp(MessageGroupTemplate messageGroupTemplate) {
        pageConfigMapper.updateMsgGrpTemp(messageGroupTemplate);
    }

    @Override
    public MessageGroupTemplate getMsgGroupTempByKeyAndOrderNum(String messageGroupKey, Integer orderNum) {
        return pageConfigMapper.getMsgGroupTempByKeyAndOrderNum(messageGroupKey,orderNum);
    }

    @Override
    public List<MessageGroupTemplate> getMsgGroupTempByThridObjAndField(String objectName, String fieldName) {
        return pageConfigMapper.getMsgGroupTempByThridObjAndField(objectName,fieldName);
    }


}
