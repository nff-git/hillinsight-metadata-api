package hillinsight.metadata.api.mappers.web;

import hillinsight.metadata.api.dto.web.PageGroupSysResult;
import hillinsight.metadata.api.dto.web.TemplateConfigInfoFieldResult;
import hillinsight.metadata.api.dto.web.TemplateConfigInfoResult;
import hillinsight.metadata.api.mappers.web.provider.PageConfigProvider;
import hillinsight.metadata.api.mappers.web.provider.ThirdInfoProvider;
import hillinsight.metadata.api.web.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName PageConfigMapper
 * @Description TODO
 * @Author wcy
 * @Date 2020/11/26
 * @Version 1.0
 */
@Repository
public interface PageConfigMapper {


    //添加业务对象信息
    @InsertProvider(type = PageConfigProvider.class, method = "addGroupInfo")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Integer addGroupInfo(MetaDataGroupInfo metaDataGroupInfo);

    //修改业务对象信息
    @UpdateProvider(type = PageConfigProvider.class, method = "updateGroupInfo")
    void updateGroupInfo(MetaDataGroupInfo metaDataGroupInfo);

    //根据不同条件获取分组信息
    @SelectProvider(type = PageConfigProvider.class, method = "getGroupListByCriteria")
    List<MetaDataGroupInfo> getGroupListByCriteria(String pageGroup, String sourceId,String developerId);

    //添加分组绑定的开发者
    @InsertProvider(type = PageConfigProvider.class, method = "addDeveloperInfo")
    void addDeveloperInfo(MetaDataDeveloperInfo metaDataDeveloperInfo);

    //根据分组id删除开发者
    @DeleteProvider(type = PageConfigProvider.class, method = "deleteDeveloperByGroupId")
    void deleteDeveloperByGroupId(Integer id);

    //根据分组id获取开发者列表
    @SelectProvider(type = PageConfigProvider.class, method = "getDeveloperListByGroupId")
    List<MetaDataDeveloperInfo> getDeveloperListByGroupId(Integer id);

    //添加页面
    @InsertProvider(type = PageConfigProvider.class, method = "addPage")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addPage(MetadataPageInfo metadataPageInfo);

    //修改页面
    @UpdateProvider(type = PageConfigProvider.class, method = "updatePage")
    void updatePage(MetadataPageInfo metadataPageInfo);

    //根据条件获取页面列表
    @SelectProvider(type = PageConfigProvider.class, method = "getPageListByCriteria")
    List<MetadataPageInfo> getPageListByCriteria(Integer groupId, String criteria);

    //删除页面
    @DeleteProvider(type = PageConfigProvider.class, method = "delPage")
    void delPage(Integer pageId);

    //添加页面模板信息
    @InsertProvider(type = PageConfigProvider.class, method = "addPageTemplateInfo")
    void addPageTemplateInfo(PageTemplate pageTemplate);


    //更新自定义字段释义
    @UpdateProvider(type = PageConfigProvider.class, method = "updCustomFieldShow")
    void updCustomFieldShow(PageTemplate pageTemplate);

    //根据id获取分组信息
    @SelectProvider(type = PageConfigProvider.class, method = "getGroupInfoById")
    MetaDataGroupInfo getGroupInfoById(Integer groupId);


    //根据id获取分组信息
    @SelectProvider(type = PageConfigProvider.class, method = "getThirdObjIdByObjIdGroup")
    List<TemplateConfigInfoResult> getThirdObjIdByObjIdGroup(Integer pageId);

    //根据业务对象id获取页面模板配置的字段详情
    @SelectProvider(type = PageConfigProvider.class, method = "getPageTempFieldDetailByThirdObjName")
    List<TemplateConfigInfoFieldResult> getPageTempFieldDetailByThirdObjName(String thirdObjName,Integer pageId);


    //根据页面id获取页面信息
    @SelectProvider(type = PageConfigProvider.class, method = "getPageInfoById")
    MetadataPageInfo getPageInfoById(Integer id);


    //根据页面id获取页面模板集合
    @SelectProvider(type = PageConfigProvider.class, method = "getPageTemplateListByPageId")
    List<PageTemplate> getPageTemplateListByPageId(Integer pageId);

    //根据分组id和页面配置id查询是否存在页面
    @SelectProvider(type = PageConfigProvider.class, method = "getPageCondfigByGroupIdAndPageKey")
    MetadataPageInfo getPageCondfigByGroupIdAndPageKey(Integer groupId, String pageKey);

    //根据分组id和页面配置id查询是否存在页面(不同分组下的页面)
    @SelectProvider(type = PageConfigProvider.class, method = "getPageCondfigByDiffGroupIdAndPageKey")
    MetadataPageInfo getPageCondfigByDiffGroupIdAndPageKey(Integer groupId, String pageKey);


    //根据页面id 业务对象名称  业务字段名称获取页面模板信息
    @SelectProvider(type = PageConfigProvider.class, method = "getpageTempByPageIdAndObjNameAndFieName")
    PageTemplate getpageTempByPageIdAndObjNameAndFieName(Integer id, String thirdObjName, String thirdFieldName);

    //导入时修改页面配置数据 根据 pageKey
    @SelectProvider(type = PageConfigProvider.class, method = "updatePageForImportByPageKey")
    void updatePageForImportByPageKey(MetadataPageInfo metadataPageInfo);


    //更新页面模板信息 导入时
    @UpdateProvider(type = PageConfigProvider.class, method = "updatePageTemplateInfo")
    void updatePageTemplateInfo(PageTemplate pageTemplate);


    //获取业务对象列表（页面模板配置 添加对象时使用）
    @SelectProvider(type = PageConfigProvider.class, method = "getThirdObjListForPageConfig")
    List<ThirdObjectInfo> getThirdObjListForPageConfig(Integer pageId,String sourceId);

    //删除页面模板 业务字段信息（支持多字段删除）
    @DeleteProvider(type = PageConfigProvider.class, method = "delPageTemplateFieldConfig")
    void delPageTemplateFieldConfig(Integer id);

    //获取业务字段列表（页面模板配置 选择业务对象下的业务字段时使用）
    @SelectProvider(type = PageConfigProvider.class, method = "getThirdFieListForPageConfig")
    List<ThirdFieldInfo> getThirdFieListForPageConfig(Integer pageId, String thirdObjName,Integer thirdObjId);

    //根据开发者id获取分组列表
    @SelectProvider(type = PageConfigProvider.class, method = "getgroupListByDevelopId")
    List<PageGroupSysResult> getgroupListByDevelopId(String userCode);

    //根据页面id删除全量的模板配置信息
    @SelectProvider(type = PageConfigProvider.class, method = "delAllPageTemplateByPageId")
    void delAllPageTemplateByPageId(Integer pageId);

    //根据 页面id，业务对象id 业务字段id查询是否存在
    @SelectProvider(type = PageConfigProvider.class, method = "getPageTemplateByCriteria")
    PageTemplate getPageTemplateByCriteria(PageTemplate pageTemplate);


    //根据系统id和pageKey 获取页面信息
    @SelectProvider(type = PageConfigProvider.class, method = "getPageCondfigBySourceIdAndPageKey")
    MetadataPageInfo getPageCondfigBySourceIdAndPageKey(String sourceId, String pageKey);

    //根据pageId和业务对象id获取页面配置信息
    @SelectProvider(type = PageConfigProvider.class, method = "getPageTemplateListByPageIdAndThirdObjId")
    List<PageTemplate> getPageTemplateListByPageIdAndThirdObjId(Integer pageId, Integer thirdObjId);

    //根据pageId和业务对象名称获取页面配置信息
    @SelectProvider(type = PageConfigProvider.class, method = "getPageTemplateListByPageIdAndThirdObjName")
    List<PageTemplate> getPageTemplateListByPageIdAndThirdObjName(Integer pageId, String thirdObjName);

    //根据业务对象名称和业务字段名称获取页面配置信息
    @SelectProvider(type = PageConfigProvider.class, method = "getPageTempByObjNameAndFieName")
    List<PageTemplate> getPageTempByObjNameAndFieName(String thirdObjName, String thirdFieldName);

    //根据信息分组key和页面id获取信息分组
    @SelectProvider(type = PageConfigProvider.class, method = "getMsgGroupByKeyAndPageId")
    MessageGroup getMsgGroupByKeyAndPageId(String messageGroupKey);

    //根据页面id获取信息分组
    @SelectProvider(type = PageConfigProvider.class, method = "getMsgGroupListByPageId")
    List<MessageGroup> getMsgGroupListByPageId(Integer pageId);

    //根据信息分组key获取配置信息
    @SelectProvider(type = PageConfigProvider.class, method = "getMsgGroupTempListByGroupKey")
    List<MessageGroupTemplate> getMsgGroupTempListByGroupKey(String messageGroupKey);

    //添加页面信息分组
    @InsertProvider(type = PageConfigProvider.class, method = "addMessageGroupInfo")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addMessageGroupInfo(MessageGroup messageGroup);

    //修改页面信息分组
    @UpdateProvider(type = PageConfigProvider.class, method = "updateMessageGroupInfo")
    void updateMessageGroupInfo(MessageGroup messageGroup);

    //删除页面模板 业务字段信息（支持多字段删除）
    @DeleteProvider(type = PageConfigProvider.class, method = "deleteMsgGroupTemplateByKey")
    void deleteMsgGroupTemplateByKey(String messageGroupKey);

    //根据信息分组key获取信息分组
    @SelectProvider(type = PageConfigProvider.class, method = "getMsgGroupByKey")
    MessageGroup getMsgGroupByKey(String messageGroupKey);

    //添加页面信息分组模板
    @InsertProvider(type = PageConfigProvider.class, method = "addMsgGroupTemplateInfo")
    void addMsgGroupTemplateInfo(MessageGroupTemplate messageGroupTemplate);

    //删除信息分组
    @DeleteProvider(type = PageConfigProvider.class, method = "deleteMsgGroupByKeyAndPageId")
    void deleteMsgGroupByKeyAndPageId(String messageGroupKey, Integer pageId);

    //修改信息分组模板配置
    @UpdateProvider(type = PageConfigProvider.class, method = "updateMsgGrpTempConfig")
    void updateMsgGrpTempConfig(Integer id, String configJson);

    //根据页面id获取信息分组
    @SelectProvider(type = PageConfigProvider.class, method = "getMsgGroupByPageId")
    List<MessageGroup> getMsgGroupByPageId(Integer pageId,String messageGroupKey);

    //根据MsgKey获取信息分组模板列表
    @SelectProvider(type = PageConfigProvider.class, method = "getMsgGroupTempByKey")
    List<MessageGroupTemplate> getMsgGroupTempByKey(String messageGroupKey);

    //根据ids获取信息分组模板
    @SelectProvider(type = PageConfigProvider.class, method = "getMsgGroupTempByIds")
    List<MessageGroupTemplate> getMsgGroupTempByIds(int idOne, int idTwo);

    //更新信息分组模板 排序
    @UpdateProvider(type = PageConfigProvider.class, method = "updateMsgGrpTempOrder")
    void updateMsgGrpTempOrder(int id, Integer orderNum);

    //获取信息分组模板配置详情
    @SelectProvider(type = PageConfigProvider.class, method = "getMsgGroupTempById")
    MessageGroupTemplate getMsgGroupTempById(int id);

    //删除信息分组模板根据id
    @DeleteProvider(type = PageConfigProvider.class, method = "deleteMsgGroupTempById")
    void deleteMsgGroupTempById(int tempId);

    //根据信息分组key 业务对象名称  业务字段名称获取页面模板信息
    @SelectProvider(type = PageConfigProvider.class, method = "getMsgTempByMsgGroupKeyAndObjNameAndFieName")
    MessageGroupTemplate getMsgTempByMsgGroupKeyAndObjNameAndFieName(String msgGroupKey, String thirdObjName, String thirdFieldName);

    //更新信息分组模板
    @UpdateProvider(type = PageConfigProvider.class, method = "updateMsgGrpTemp")
    void updateMsgGrpTemp(MessageGroupTemplate messageGroupTemplate);

    //根据信息分组key和排序num查询
    @SelectProvider(type = PageConfigProvider.class, method = "getMsgGroupTempByKeyAndOrderNum")
    MessageGroupTemplate getMsgGroupTempByKeyAndOrderNum(String messageGroupKey,Integer orderNum);

    //根据业务对象名和字段名查询配置信息
    @SelectProvider(type = PageConfigProvider.class, method = "getMsgGroupTempByThridObjAndField")
    List<MessageGroupTemplate> getMsgGroupTempByThridObjAndField(String objectName, String fieldName);
}
