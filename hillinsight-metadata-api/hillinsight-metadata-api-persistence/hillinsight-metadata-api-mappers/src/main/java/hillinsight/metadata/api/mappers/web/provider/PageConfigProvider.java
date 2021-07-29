package hillinsight.metadata.api.mappers.web.provider;

import cn.hutool.core.util.StrUtil;
import hillinsight.metadata.api.web.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Struct;

/**
 * @ClassName PageConfigProvider
 * @Description TODO
 * @Author wcy
 * @Date 2020/11/26
 * @Version 1.0
 */
public class PageConfigProvider {


    /**
     * 添加组信息
     *
     * @param metaDataGroupInfo 元数据组信息
     * @return {@link String}
     */
    public String addGroupInfo(MetaDataGroupInfo metaDataGroupInfo) {

        return new SQL()
                .INSERT_INTO("t_metadata_group")
                .VALUES("page_group," +
                                "source_id, " +
                                "source_name," +
                                "describe," +
                                "status," +
                                "creator_id," +
                                "creator_name," +
                                "creator_time," +
                                "updator_id," +
                                "updator_name," +
                                "update_time",
                        "#{pageGroup}," +
                                "#{sourceId}, " +
                                "#{sourceName}," +
                                "#{describe}," +
                                "#{status}," +
                                "#{creatorId}," +
                                "#{creatorName}," +
                                "#{creatorTime},#{updatorId},#{updatorName},#{updateTime}").toString();
    }


    /**
     * 更新组信息
     *
     * @param metaDataGroupInfo 元数据组信息
     * @return {@link String}
     */
    public String updateGroupInfo(MetaDataGroupInfo metaDataGroupInfo) {
        return new SQL() {{
            UPDATE("t_metadata_group");
            SET("page_group = #{pageGroup}," +
                    "describe = #{describe}," +
                    "updator_id = #{updatorId}," +
                    "updator_name = #{updatorName}," +
                    "update_time = #{updateTime}");
            WHERE("id = #{id}");
        }}.toString();

    }


    /**
     * 根据不同条件获取分组信息
     *
     * @param pageGroup   页面组
     * @param sourceId    源id
     * @param developerId 开发者id
     * @return {@link String}
     */
    public String getGroupListByCriteria(String pageGroup, String sourceId, String developerId) {
        return new SQL() {{
            SELECT("f.id," +
                    "f.page_group," +
                    "f.source_id, " +
                    "f.source_name," +
                    "f.describe," +
                    "f.status," +
                    "f.creator_id," +
                    "f.creator_name," +
                    "f.creator_time," +
                    "f.updator_id," +
                    "f.updator_name," +
                    "f.update_time");
            FROM("t_metadata_group f");
            WHERE("f.source_id = #{sourceId} and f.status != 0 " +
                    " and f.id in (select de.group_id from t_metadata_developer de where de.developer_id = #{developerId})");
            if (StrUtil.isNotEmpty(pageGroup)) {
                WHERE("f.page_group like #{pageGroup}");
            }
            ORDER_BY("f.update_time desc");
        }}.toString();
    }


    /**
     * 添加开发人员信息
     *
     * @param metaDataDeveloperInfo 元数据开发人员信息
     * @return {@link String}
     */
    public String addDeveloperInfo(MetaDataDeveloperInfo metaDataDeveloperInfo) {

        return new SQL()
                .INSERT_INTO("t_metadata_developer")
                .VALUES("developer_id," +
                                "developer_name, " +
                                "group_id",
                        "#{developerId}," +
                                "#{developerName}, " +
                                "#{groupId}").toString();
    }


    /**
     * 删除开发者
     *
     * @param id id
     * @return {@link String}
     */
    public String deleteDeveloperByGroupId(Integer id) {

        return new SQL() {{
            DELETE_FROM("t_metadata_developer");
            WHERE("group_id = #{id}");
        }}.toString();

    }


    /**
     * 根据分组id获取开发者列表
     *
     * @param id id
     * @return {@link String}
     */
    public String getDeveloperListByGroupId(Integer id) {
        return new SQL() {{
            SELECT("f.id," +
                    "f.developer_id," +
                    "f.developer_name, " +
                    "f.group_id");
            FROM("t_metadata_developer f");
            WHERE("f.group_id = #{id}");
        }}.toString();

    }


    /**
     * 添加页面
     *
     * @param metadataPageInfo 元数据页面信息
     * @return {@link String}
     */
    public String addPage(MetadataPageInfo metadataPageInfo) {

        return new SQL()
                .INSERT_INTO("t_metadata_page")
                .VALUES("page_key," +
                                "page_title, " +
                                "page_explain," +
                                "group_id," +
                                "status," +
                                "source_id," +
                                "source_name," +
                                "creator_id," +
                                "creator_name," +
                                "creator_time," +
                                "updator_id," +
                                "updator_name," +
                                "update_time",
                        "#{pageKey}," +
                                "#{pageTitle}, " +
                                "#{pageExplain}," +
                                "#{groupId}," +
                                "#{status}," +
                                "#{sourceId}," +
                                "#{sourceName}," +
                                "#{creatorId}," +
                                "#{creatorName}," +
                                "#{creatorTime},#{updatorId},#{updatorName},#{updateTime}").toString();
    }


    /**
     * 更新页面
     *
     * @param metadataPageInfo 页面元数据信息
     * @return {@link String}
     */
    public String updatePage(MetadataPageInfo metadataPageInfo) {
        return new SQL() {{
            UPDATE("t_metadata_page");
            SET("page_title = #{pageTitle}," +
                    "page_explain = #{pageExplain}," +
                    "group_id = #{groupId}," +
                    "updator_id = #{updatorId}," +
                    "updator_name = #{updatorName}," +
                    "update_time = #{updateTime}");
            WHERE("id = #{id}");
        }}.toString();

    }


    /**
     * 根据条件获取页面列表信息
     *
     * @param groupId  组id
     * @param criteria 标准
     * @return {@link String}
     */
    public String getPageListByCriteria(Integer groupId, String criteria) {
        return new SQL() {{
            SELECT("f.id," +
                    "f.page_key," +
                    "f.page_title, " +
                    "f.page_explain," +
                    "f.group_id," +
                    "f.status," +
                    "f.source_id," +
                    "f.source_name," +
                    "f.creator_id," +
                    "f.creator_name," +
                    "f.creator_time," +
                    "f.updator_id," +
                    "f.updator_name," +
                    "f.update_time");
            FROM("t_metadata_page f");
            WHERE("f.group_id = #{groupId}");
            if (StrUtil.isNotEmpty(criteria)) {
                WHERE("(f.page_key like #{criteria}  or f.page_title like #{criteria})");
            }
//            WHERE("f.status != 0");
            ORDER_BY("f.update_time desc");
        }}.toString();

    }


    /**
     * 删除页面信息
     *
     * @param pageId 页面id
     * @return {@link String}
     */
    public String delPage(Integer pageId) {
        return new SQL() {{
            DELETE_FROM("t_metadata_page ");
            WHERE("id = #{pageId}");
        }}.toString();

    }


    /**
     * 添加页面模板信息
     *
     * @param pageTemplate 页面模板
     * @return {@link String}
     */
    public String addPageTemplateInfo(PageTemplate pageTemplate) {

        return new SQL()
                .INSERT_INTO("t_page_template")
                .VALUES("third_obj_id," +
                                "third_obj_name, " +
                                "third_field_id," +
                                "third_field_name," +
                                "page_id," +
                                "is_custom_field," +
                                "field_paraphrase_cn," +
                                "field_paraphrase_en," +
                                "updator_id," +
                                "updator_name," +
                                "update_time," +
                                "creator_id," +
                                "creator_name," +
                                "creator_time",
                        "#{thirdObjId}," +
                                "#{thirdObjName}, " +
                                "#{thirdFieldId}," +
                                "#{thirdFieldName}," +
                                "#{pageId}," +
                                "#{isCustomField},#{fieldParaphraseCn},#{fieldParaphraseEn},#{updatorId},#{updatorName},#{updateTime}," +
                                "#{creatorId}," +
                                "#{creatorName}," +
                                "#{creatorTime}").toString();
    }


    /**
     * 更新自定义字段释义
     *
     * @param pageTemplate 页面模板
     * @return {@link String}
     */
    public String updCustomFieldShow(PageTemplate pageTemplate) {
        return new SQL() {{
            UPDATE("t_page_template");
            SET("is_custom_field = #{isCustomField}," +
                    "field_paraphrase_cn=#{fieldParaphraseCn}," +
                    "field_paraphrase_en=#{fieldParaphraseEn}," +
                    "updator_id = #{updatorId}," +
                    "updator_name = #{updatorName}," +
                    "update_time = #{updateTime}");
            WHERE("id = #{id}");
        }}.toString();
    }


    /**
     * 通过id获取组信息
     *
     * @param groupId 组id
     * @return {@link String}
     */
    public String getGroupInfoById(Integer groupId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_metadata_group f");
            WHERE("f.id = #{groupId}");
        }}.toString();
    }


    /**
     * 根据业务对象id分组后获得的业务对象id列表
     *
     * @param pageId 页面id
     * @return {@link String}
     */
    public String getThirdObjIdByObjIdGroup(Integer pageId) {
        return new SQL() {{
            SELECT("f.third_obj_name as thirdObjName");
            FROM("t_page_template f");
            WHERE("f.page_id = #{pageId}");
            GROUP_BY("f.third_obj_name");
        }}.toString();
    }


    /**
     * 根据业务对象id获取页面模板配置的字段详情
     *
     * @param thirdObjName 业务对象名称
     * @param pageId     页面id
     * @return {@link String}
     */
    public String getPageTempFieldDetailByThirdObjName(String thirdObjName, Integer pageId) {
        return new SQL() {{
            SELECT("f.id as id,f.third_field_id as thirdFieldId,f.third_field_name as thirdFieldName," +
                    "f.is_custom_field as isCustomField,f.field_paraphrase_cn as fieldParaphraseCn,f.field_paraphrase_en as fieldParaphraseEn");
            FROM("t_page_template f");
            WHERE("f.page_id = #{pageId} and f.third_obj_name = #{thirdObjName}");
        }}.toString();
    }


    /**
     * 通过id获取页面信息
     *
     * @param id id
     * @return {@link String}
     */
    public String getPageInfoById(Integer id) {
        return new SQL() {{
            SELECT("*");
            FROM("t_metadata_page f");
            WHERE("f.id = #{id}");
        }}.toString();

    }

    /**
     * 根据业务对象id分组后获得的业务对象id列表
     *
     * @param pageId 页面id
     * @return {@link String}
     */
    public String getPageTemplateListByPageId(Integer pageId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_page_template f");
            WHERE("f.page_id = #{pageId}");
        }}.toString();
    }


    /**
     * 根据分组id和页面配置id查询是否存在页面
     *
     * @param groupId 组id
     * @param pageKey 页面key
     * @return {@link String}
     */
    public String getPageCondfigByGroupIdAndPageKey(Integer groupId, String pageKey) {
        return new SQL() {{
            SELECT("*");
            FROM("t_metadata_page f");
            WHERE("f.group_id = #{groupId}");
            WHERE("F.page_key = #{pageKey}");
        }}.toString();

    }


    /**
     * 根据分组id和页面配置id查询是否存在页面(不同分组下的页面)
     *
     * @param groupId 组id
     * @param pageKey 页面key
     * @return {@link String}
     */
    public String getPageCondfigByDiffGroupIdAndPageKey(Integer groupId, String pageKey) {
        return new SQL() {{
            SELECT("*");
            FROM("t_metadata_page f");
            WHERE("f.group_id != #{groupId}");
            WHERE("F.page_key = #{pageKey}");
        }}.toString();

    }


    /**
     * 根据页面id 业务对象名称  业务字段名称获取页面模板信息
     *
     * @param id             id
     * @param thirdObjName   第三obj的名字
     * @param thirdFieldName 第三个字段名
     * @return {@link String}
     */
    public String getpageTempByPageIdAndObjNameAndFieName(Integer id, String thirdObjName, String thirdFieldName) {
        return new SQL() {{
            SELECT("*");
            FROM("t_page_template f");
            WHERE("f.page_id = #{id}");
            WHERE("f.third_obj_name = #{thirdObjName}");
            WHERE("f.third_field_name = #{thirdFieldName}");
        }}.toString();
    }


    /**
     * 导入时修改页面配置数据 根据 pageKey
     *
     * @param metadataPageInfo 页面配置信息
     * @return {@link String}
     */
    public String updatePageForImportByPageKey(MetadataPageInfo metadataPageInfo) {
        return new SQL() {{
            UPDATE("t_metadata_page");
            SET("page_title = #{pageTitle}," +
                    "page_explain = #{pageExplain}," +
                    "source_id = #{sourceId}," +
                    "source_name = #{sourceName}," +
                    "updator_id = #{updatorId}," +
                    "updator_name = #{updatorName}," +
                    "update_time = #{updateTime}");
            WHERE("page_key = #{pageKey} and group_id = #{groupId}");
        }}.toString();

    }


    /**
     * 更新页面模板信息
     *
     * @param pageTemplate 页面模板
     * @return {@link String}
     */
    public String updatePageTemplateInfo(PageTemplate pageTemplate) {
        return new SQL() {{
            UPDATE("t_page_template");
            SET("page_id = #{pageId}," +
                    "is_custom_field = #{isCustomField}," +
                    "field_paraphrase_cn = #{fieldParaphraseCn}," +
                    "field_paraphrase_en = #{fieldParaphraseEn}," +
                    "updator_id = #{updatorId}," +
                    "updator_name = #{updatorName}," +
                    "update_time = #{updateTime}");
            WHERE("id = #{id}");
        }}.toString();

    }

    /**
     * 获取业务对象列表（页面模板配置 添加对象时使用）
     *
     * @param pageId 页面id
     * @param sourceId 系统id
     * @return {@link String}
     */
    public String getThirdObjListForPageConfig(Integer pageId,String sourceId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_third_object_info th");
            WHERE("th.status != 0 and th.source_id = #{sourceId}");
            WHERE("th.id not in (select te.third_obj_id from t_page_template te  where te.page_id = #{pageId})");
        }}.toString();
    }


    /**
     * 删除页面模板 业务字段信息（支持多字段删除）
     *
     * @param id     id
     * @return {@link String}
     */
    public String delPageTemplateFieldConfig(Integer id) {
        return new SQL() {{
            DELETE_FROM("t_page_template");
            WHERE("id =#{id} ");
        }}.toString();
    }


    /**
     * 获取业务字段列表（页面模板配置 选择业务对象下的业务字段时使用）
     *
     * @param pageId     页面id
     * @param thirdObjName 业务对象名称
     * @return {@link String}
     */
    public String getThirdFieListForPageConfig(Integer pageId, String thirdObjName,Integer thirdObjId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_third_field_info th");
            WHERE("th.status != 0 and th.third_object_id = #{thirdObjId}");
            WHERE("th.field_name not in (select te.third_field_name from t_page_template te  where " +
                    "te.page_id = #{pageId} and te.third_obj_name =#{thirdObjName})");
        }}.toString();
    }


    /**
     * 根据开发者id获取分组列表
     *
     * @param userCode 用户代码
     * @return {@link String}
     */
    public String getgroupListByDevelopId(String userCode) {
        return new SQL() {{
            SELECT("f.source_id as sourceId,f.source_name as sourceName");
            FROM("t_metadata_group f");
            WHERE("f.id in (select de.group_id from t_metadata_developer de where de.developer_id =#{userCode})");
            GROUP_BY("f.source_id,f.source_name");

        }}.toString();
    }


    /**
     * 根据页面id删除全量的模板配置信息
     *
     * @param pageId 页面id
     * @return {@link String}
     */
    public String delAllPageTemplateByPageId(Integer pageId) {
        return new SQL() {{
            DELETE_FROM("t_page_template");
            WHERE("page_id = #{pageId}");
        }}.toString();

    }


    /**
     * 根据 页面id，业务对象id 业务字段id查询是否存在
     *
     * @param pageTemplate 页面模板
     * @return {@link String}
     */
    public String getPageTemplateByCriteria(PageTemplate pageTemplate) {
        return new SQL() {{
            SELECT("*");
            FROM("t_page_template f");
            WHERE("f.page_id = #{pageId}");
            WHERE("f.third_obj_id = #{thirdObjId}");
            WHERE("f.third_field_id = #{thirdFieldId}");
        }}.toString();
    }

    /**
     * 根据分组id和页面配置id查询是否存在页面
     *
     * @param sourceId 系统id
     * @param pageKey 页面key
     * @return {@link String}
     */
    public String getPageCondfigBySourceIdAndPageKey(String sourceId, String pageKey) {
        return new SQL() {{
            SELECT("*");
            FROM("t_metadata_page f");
            WHERE("f.source_id = #{sourceId}");
            WHERE("f.page_key = #{pageKey} and f.status = 1");
        }}.toString();

    }


    /**
     * 根据pageId和业务对象id获取页面配置信息
     *
     * @param pageId     页面id
     * @param thirdObjId 业务对象 id
     * @return {@link String}
     */
    public String getPageTemplateListByPageIdAndThirdObjId(Integer pageId, Integer thirdObjId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_page_template f");
            WHERE("f.page_id = #{pageId} and f.third_obj_id = #{thirdObjId}");
        }}.toString();
    }


    /**
     * 根据pageId和业务对象名称获取页面配置信息
     *
     * @param pageId       页面id
     * @param thirdObjName 第三obj的名字
     * @return {@link String}
     */
    public String getPageTemplateListByPageIdAndThirdObjName(Integer pageId,  String thirdObjName) {
        return new SQL() {{
            SELECT("*");
            FROM("t_page_template f");
            WHERE("f.page_id = #{pageId} and f.third_obj_name = #{thirdObjName}");
        }}.toString();
    }


    /**
     * 根据业务对象名称和业务字段名称获取页面配置信息
     *
     * @param thirdObjName   第三obj的名字
     * @param thirdFieldName 第三个字段名
     * @return {@link String}
     */
    public String getPageTempByObjNameAndFieName( String thirdObjName, String thirdFieldName) {
        return new SQL() {{
            SELECT("*");
            FROM("t_page_template f");
            WHERE("f.third_obj_name = #{thirdObjName}");
            WHERE("f.third_field_name = #{thirdFieldName}");
        }}.toString();
    }


    /**
     *根据信息分组key和页面id获取信息分组
     *
     * @param messageGroupKey 消息分组key
     * @return {@link String}
     */
    public String getMsgGroupByKeyAndPageId(String messageGroupKey) {
        return new SQL() {{
            SELECT("*");
            FROM("t_message_group f");
            WHERE("f.message_group_key = #{messageGroupKey}");
        }}.toString();

    }

    /**
     *根据页面id获取信息分组
     *
     * @param pageId          消息分组key
     * @return {@link String}
     */
    public String getMsgGroupListByPageId(Integer pageId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_message_group f");
            WHERE("f.page_id = #{pageId}");
        }}.toString();

    }

    /**
     *根据信息分组key获取配置信息
     *
     * @param messageGroupKey          信息分组key
     * @return {@link String}
     */
    public String getMsgGroupTempListByGroupKey(String messageGroupKey) {
        return new SQL() {{
            SELECT("*");
            FROM("t_message_group_template f");
            WHERE("f.message_group_key = #{messageGroupKey}");
        }}.toString();

    }


    /**
     * 添加消息组信息
     *
     * @param messageGroup 消息组
     * @return {@link String}
     */
    public String addMessageGroupInfo(MessageGroup messageGroup) {

        return new SQL()
                .INSERT_INTO("t_message_group")
                .VALUES("message_group_name," +
                                "message_group_key, " +
                                "page_id," +
                                "creator_id," +
                                "creator_name," +
                                "creator_time," +
                                "updator_id," +
                                "updator_name," +
                                "update_time",
                        "#{messageGroupName}," +
                                "#{messageGroupKey}, " +
                                "#{pageId}," +
                                "#{creatorId}," +
                                "#{creatorName}," +
                                "#{creatorTime},#{updatorId},#{updatorName},#{updateTime}").toString();
    }


    /**
     * 更新消息组信息
     *
     * @param messageGroup 消息组
     * @return {@link String}
     */
    public String updateMessageGroupInfo(MessageGroup messageGroup) {
        return new SQL() {{
            UPDATE("t_message_group");
            SET("message_group_name = #{messageGroupName}," +
                    "updator_id = #{updatorId}," +
                    "updator_name = #{updatorName}," +
                    "update_time = #{updateTime}");
            WHERE("id = #{id}");
        }}.toString();

    }


    /**
     * 删除味精集团模板的关键
     *
     * @param messageGroupKey 消息组的关键
     * @return {@link String}
     */
    public String deleteMsgGroupTemplateByKey(String messageGroupKey) {

        return new SQL() {{
            DELETE_FROM("t_message_group_template");
            WHERE("message_group_key = #{messageGroupKey}");
        }}.toString();

    }


    /**
     * 根据信息分组key获取信息分组
     *
     * @param messageGroupKey 消息组的关键
     * @return {@link String}
     */
    public String getMsgGroupByKey(String messageGroupKey) {
        return new SQL() {{
            SELECT("*");
            FROM("t_message_group f");
            WHERE("f.message_group_key = #{messageGroupKey} ");
        }}.toString();

    }

    /**
     * 添加味精组模板信息
     *
     * @param messageGroupTemplate 消息组模板
     * @return {@link String}
     */
    public String addMsgGroupTemplateInfo(MessageGroupTemplate messageGroupTemplate) {

        return new SQL()
                .INSERT_INTO("t_message_group_template")
                .VALUES("third_obj_name," +
                                "third_field_name, " +
                                "message_group_key," +
                                "order_num,config_json,is_update_config",
                        "#{thirdObjName}," +
                                "#{thirdFieldName}, " +
                                "#{messageGroupKey}," +
                                "#{orderNum},#{configJson},#{isUpdateConfig}").toString();
    }


    /**
     * 删除信息分组
     *
     * @param messageGroupKey 消息组的关键
     * @param pageId          页面id
     * @return {@link String}
     */
    public String deleteMsgGroupByKeyAndPageId(String messageGroupKey, Integer pageId) {

        return new SQL() {{
            DELETE_FROM("t_message_group");
            WHERE("message_group_key = #{messageGroupKey} and page_id = #{pageId}");
        }}.toString();

    }


    /**
     * 更新信息分组模板配置
     *
     * @param id         id
     * @param configJson 配置json
     * @return {@link String}
     */
    public String updateMsgGrpTempConfig(Integer id, String configJson) {
        return new SQL() {{
            UPDATE("t_message_group_template");
            SET("config_json = #{configJson},is_update_config = 1");
            WHERE("id = #{id}");
        }}.toString();

    }


    /**
     * 根据页面id获取信息分组
     *
     * @param pageId 页面id
     * @return {@link String}
     */
    public String getMsgGroupByPageId(Integer pageId,String messageGroupKey) {
        return new SQL() {{
            SELECT("*");
            FROM("t_message_group f");
            WHERE("f.page_id = #{pageId}");
            if(StrUtil.isNotEmpty(messageGroupKey)) WHERE("and f.message_group_key = #{messageGroupKey}");
        }}.toString();

    }

    /**
     * 根据页面id获取信息分组
     *
     * @param messageGroupKey 页面信息分组key
     * @return {@link String}
     */
    public String getMsgGroupTempByKey(String messageGroupKey) {
        return new SQL() {{
            SELECT("*");
            FROM("t_message_group_template t");
            WHERE("t.message_group_key = #{messageGroupKey}");
            ORDER_BY("t.order_num");
        }}.toString();

    }

    /**
     * 根据ids获取信息分组模板
     *
     * @param idOne id1
     * @param idTwo id2
     * @return {@link String}
     */
    public String getMsgGroupTempByIds(int idOne, int idTwo) {
        return new SQL() {{
            SELECT("*");
            FROM("t_message_group_template t");
            WHERE("t.id = #{idOne} or t.id = #{idTwo}");
        }}.toString();

    }


    /**
     * 更新信息分组模板配置
     *
     * @param id         id
     * @param orderNum   orderNum
     * @return {@link String}
     */
    public String updateMsgGrpTempOrder(int id, Integer orderNum) {
        return new SQL() {{
            UPDATE("t_message_group_template");
            SET("order_num = #{orderNum}");
            WHERE("id = #{id}");
        }}.toString();

    }


    /**
     * 获取信息分组模板配置详情
     *
     * @param id id
     * @return {@link String}
     */
    public String getMsgGroupTempById(int id) {
        return new SQL() {{
            SELECT("*");
            FROM("t_message_group_template t");
            WHERE("t.id = #{id}");
        }}.toString();

    }

    /**
     * 根据信息分组key 业务对象名称  业务字段名称获取页面模板信息
     *
     * @param msgGroupKey     信息分组key
     * @param thirdObjName   第三obj的名字
     * @param thirdFieldName 第三个字段名
     * @return {@link String}
     */
    public String getMsgTempByMsgGroupKeyAndObjNameAndFieName(String msgGroupKey, String thirdObjName, String thirdFieldName) {
        return new SQL() {{
            SELECT("*");
            FROM("t_message_group_template t");
            WHERE("t.message_group_key = #{msgGroupKey}");
            WHERE("t.third_obj_name = #{thirdObjName}");
            WHERE("t.third_field_name = #{thirdFieldName}");
        }}.toString();
    }

    /**
     * 更新信息分组模板配置
     *
     * @param messageGroupTemplate
     * @return {@link String}
     */
    public String updateMsgGrpTemp(MessageGroupTemplate messageGroupTemplate) {
        return new SQL() {{
            UPDATE("t_message_group_template");
            SET("third_obj_name = #{thirdObjName}");
            SET("third_field_name = #{thirdFieldName}");
            SET("order_num = #{orderNum}");
            SET("config_json = #{configJson}");
            WHERE("message_group_key= #{messageGroupKey} and third_obj_name = #{thirdObjName} and third_field_name = #{thirdFieldName}");
        }}.toString();

    }

    /**
     * 根据信息分组key和排序num查询
     *
     * @param messageGroupKey
     * @param orderNum
     * @return {@link String}
     */
    public String getMsgGroupTempByKeyAndOrderNum(String messageGroupKey,Integer orderNum) {
        return new SQL() {{
            SELECT("*");
            FROM("t_message_group_template t");
            WHERE("t.message_group_key = #{messageGroupKey} and t.order_num = #{orderNum}");
        }}.toString();
    }


    /**
     * 删除信息分组模板
     *
     * @param tempId 临时身份证
     * @return {@link String}
     */
    public String deleteMsgGroupTempById(int tempId) {
        return new SQL() {{
            DELETE_FROM("t_message_group_template");
            WHERE("id = #{tempId}");
        }}.toString();

    }


    /**
     * 根据业务对象名和字段名查询配置信息
     * @param objectName 对象名称
     * @param fieldName  字段名
     * @return {@link String}
     */
    public String getMsgGroupTempByThridObjAndField(String objectName,String fieldName) {
        return new SQL() {{
            SELECT("*");
            FROM("t_message_group_template t");
            WHERE("t.third_obj_name = #{objectName} and t.third_field_name = #{fieldName}");
        }}.toString();
    }





}
