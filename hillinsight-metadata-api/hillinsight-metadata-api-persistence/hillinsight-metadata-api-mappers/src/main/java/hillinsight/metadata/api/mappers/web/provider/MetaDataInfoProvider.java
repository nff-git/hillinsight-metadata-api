package hillinsight.metadata.api.mappers.web.provider;

import cn.hutool.core.util.StrUtil;
import hillinsight.metadata.api.web.MetaDataFieldInfo;
import hillinsight.metadata.api.web.MetaDataObjectInfo;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.hibernate.validator.internal.util.StringHelper;

import java.awt.peer.FramePeer;
import java.util.Map;

public class MetaDataInfoProvider {

    /**
     * 添加元数据对象信息 SQL
     *
     * @param metaDataObjectInfo
     * @return
     */
    public String addObjectInfo(MetaDataObjectInfo metaDataObjectInfo) {

        return new SQL()
                .INSERT_INTO("t_metadata_object_info")
                .VALUES("show_name,object_name,describe",
                        "#{showName},#{objectName},#{describe}")
                .VALUES("creator_id, creator_name,creator_time,status,parent_id,updator_id,updator_name,update_time",
                        "#{creatorId}, #{creatorName},#{creatorTime},#{status},#{parentId},#{updatorId},#{updatorName},#{updateTime}").toString();
    }

    /**
     * 修改元数据对象信息 SQL
     *
     * @param metaDataObjectInfo
     * @return
     */
    public String updateObjectInfo(MetaDataObjectInfo metaDataObjectInfo) {

        return new SQL() {{
            UPDATE("t_metadata_object_info");
            SET("show_name = #{showName}," +
                    "object_name = #{objectName},describe = #{describe},updator_id = #{updatorId}," +
                    "updator_name = #{updatorName},update_time = #{updateTime}," +
                    "parent_id = #{parentId},status  = #{status}");
            WHERE("id = #{id}");
        }}.toString();
    }

    /**
     * 添加元数据字段信息 SQL
     *
     * @param metaDataFieldInfo
     * @return
     */
    public String addFieldInfo(MetaDataFieldInfo metaDataFieldInfo) {

        return new SQL()
                .INSERT_INTO("t_metadata_field_info")
                .VALUES("field_name, " +
                                "field_show_cn,field_show_en,field_type_code,field_type_name",
                        "#{fieldName}, #{fieldShowCn},#{fieldShowEn},#{fieldTypeCode},#{fieldTypeName}")
                .VALUES("field_paraphrase_cn, field_paraphrase_en,data_owner_id," +
                                "data_owner_name,filling_explanation,status,creator_id," +
                                "creator_name,creator_time,object_id",
                        "#{fieldParaphraseCn}, #{fieldParaphraseEn},#{dataOwnerId},#{dataOwnerName}," +
                                "#{fillingExplanation},#{status},#{creatorId},#{creatorName}," +
                                "#{creatorTime},#{objectId}").toString();
    }

    /**
     * 修改元数据字段信息 SQL
     *
     * @param metaDataFieldInfo
     * @return
     */
    public String updateFieldInfo(MetaDataFieldInfo metaDataFieldInfo) {

        return new SQL() {{
            UPDATE("t_metadata_field_info");
            SET(
                    "field_show_cn = #{fieldShowCn}," +
                            "field_show_en = #{fieldShowEn}," +
                            //"field_type = #{fieldType}," +
                            "field_paraphrase_cn = #{fieldParaphraseCn}," +
                            "field_paraphrase_en = #{fieldParaphraseEn}," +
                            "data_owner_id = #{dataOwnerId}," +
                            "data_owner_name = #{dataOwnerName}," +
                            //"status = #{status}," +
                            "filling_explanation = #{fillingExplanation}," +
                            "updator_id = #{updatorId}," +
                            "updator_name = #{updatorName}," +
                            "update_time = #{updateTime}" );
                            //"object_id = #{objectId}");
            WHERE("id = #{id}");
        }}.toString();
    }

    /**
     * 模糊查询元数据对象列表
     *
     * @param objectName
     * @return
     */
    public String getObjectList(String objectName) {
        return new SQL() {{
            SELECT("obj.id, obj.show_name, obj.object_name, obj.parent_id," +
                    "obj.updator_id,obj.updator_name,obj.update_time,obj.creator_id,obj.creator_name,obj.creator_time,describe" +
                    ",status");
            FROM("t_metadata_object_info obj");
            //WHERE("obj.status != 0");
            if (StrUtil.isNotEmpty(objectName)) {
                WHERE(" obj.object_name like #{objectName} or obj.show_name like #{objectName}");
            }
           // WHERE("obj.id !=0");
            ORDER_BY("obj.update_time  desc");
        }}.toString();
    }


    /**
     * 根据元数据对象id查询字段列表 SQL
     *
     * @param objectId
     * @param criteria 查询条件
     * @return
     */
    public String getFieldListByObjectId(Integer objectId,String criteria) {
        return new SQL() {{
            SELECT("f.id, f.field_name, f.field_show_cn,f.field_show_en, f.field_type_code,f.field_type_name," +
                    "f.field_paraphrase_cn,f.field_paraphrase_en,f.data_owner_id,f.data_owner_name," +
                    "f.filling_explanation,f.status,f.creator_id,f.creator_name,f.creator_time," +
                    "f.updator_id,f.updator_name,f.update_time,f.object_id");
            FROM("t_metadata_field_info f");
            WHERE("f.object_id = #{objectId}");
            if(StrUtil.isNotEmpty(criteria)){
                WHERE(" (f.field_name like #{criteria} or f.field_show_cn like #{criteria} or f.field_show_en like #{criteria})");
            }
            ORDER_BY("f.creator_time");
        }}.toString();
    }


    /**
     * 根据字段id查询字段详情 SQL
     *
     * @param fieldId
     * @return
     */
    public String getFieldetailById(Integer fieldId) {
        return new SQL() {{
            SELECT("f.id, f.field_name, f.field_show_cn,f.field_show_en, f.field_type_code,f.field_type_name," +
                    "f.field_paraphrase_cn,f.field_paraphrase_en,f.data_owner_id,f.data_owner_name," +
                    "f.filling_explanation,f.status,f.creator_id,f.creator_name,f.creator_time," +
                    "f.updator_id,f.updator_name,f.update_time,f.object_id");
            FROM("t_metadata_field_info f");
            WHERE("f.id = #{fieldId}");
        }}.toString();
    }


    /**
     * 字段停用 SQL
     *
     * @param metaDataFieldInfo
     * @return
     */
    public String fieldBlockUp(MetaDataFieldInfo metaDataFieldInfo) {

        return new SQL() {{
            UPDATE("t_metadata_field_info");
            SET("status = #{status},updator_id = #{updatorId},updator_name = #{updatorName},update_time = #{updateTime}");
            WHERE("id = #{id}");
        }}.toString();
    }


    /**
     *  查询元数据字段列表（新增业务字段映射元数据字段时使用） SQL
     *
     * @param sourceId      系统来源id
     * @param thirdObjId    业务对象 id
     * @param metaDataObjId 元数据对象 id
     * @return {@link String}
     */
    public String getMetaFieldList(String sourceId,Integer thirdObjId,Integer metaDataObjId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_metadata_field_info f");
            WHERE("f.status != 0 and f.object_id = #{metaDataObjId} and f.id not in (select th.metadata_field_id from t_third_field_info th " +
                    "where th.source_id = #{sourceId} and th.third_object_id = #{thirdObjId} and th.metadata_field_id is not null)");
        }}.toString();
    }

    /**
     * 根据对象名查询元数据对象信息 SQL
     *
     * @param objectName
     * @return
     */
    public String getmetaDataObjInfoByName(String objectName) {
        return new SQL() {{
            SELECT("*");
            FROM("t_metadata_object_info ");
            WHERE("object_name = #{objectName}");
        }}.toString();
    }

    /**
     * 根据元数据字段名查询字段信息 SQL
     *
     * @param fieldName
     * @param objectId
     * @return
     */
    public String getMetaFieldByName(String fieldName, Integer objectId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_metadata_field_info f");
            WHERE("f.field_name = #{fieldName} and f.object_id =#{objectId} ");
        }}.toString();
    }


    /**
     * 获取元数据对象列表 （业务对象新增时绑定元数据对象使用）
     *
     * @return {@link String}
     */
    public String getMetaObjListForThirdObj(String sourceId) {
        return new SQL() {{
            SELECT("obj.id, obj.show_name, obj.object_name, obj.parent_id," +
                    "obj.updator_id,obj.updator_name,obj.update_time,obj.creator_id,obj.creator_name,obj.creator_time,obj.describe,obj.status");
            FROM("t_metadata_object_info obj");
            WHERE("obj.status != 0");
            WHERE("obj.id not in (select th.metadata_object_id from t_third_object_info th where th.source_id = #{sourceId} and " +
                    "th.metadata_object_id is not null )");
        }}.toString();
    }

    /**
     * 获取元数据父对象列表（新增元数据对象时使用）
     *
     * @return {@link String}
     */
    public String getMetaParentList() {
        return new SQL() {{
            SELECT("*");
            FROM("t_metadata_object_info obj");
            WHERE("obj.status != 0");
            ORDER_BY("obj.id");
        }}.toString();
    }


    /**
     * 对象使用或停用
     *
     * @param metaDataObjectInfo 元数据对象的信息
     * @return {@link String}
     */
    public String ObjectUseOrBlockUp(MetaDataObjectInfo metaDataObjectInfo) {

        return new SQL() {{
            UPDATE("t_metadata_object_info");
            SET("status = #{status}," +
                    "updator_id = #{updatorId}," +
                    "updator_name = #{updatorName}," +
                    "update_time = #{updateTime}");
            WHERE("id = #{id}");
        }}.toString();
    }


    /**
     * 根据对象id获取元数据对象信息
     *
     * @param objectId 对象id
     * @return {@link String}
     */
    public String getObjectById(Integer objectId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_metadata_object_info obj");
            WHERE("obj.id = #{objectId}");
        }}.toString();
    }

    /**
     * 获取元数据对象列表
     * //TODO 业务字段新增绑定元数据字段时使用
     *
     * @return
     */
    public String getMetaObjListForThirdField(String sourceId,Integer thirdObjId) {
        return new SQL() {{
          SELECT("*");
            FROM("t_metadata_object_info me");
            WHERE("me.status != 0 and me.id in (SELECT DISTINCT f.object_id FROM t_metadata_field_info f WHERE f.status != 0 AND f.id NOT IN " +
                    "( SELECT th.metadata_field_id FROM t_third_field_info th  WHERE th.source_id = #{sourceId} AND th.third_object_id = #{thirdObjId} " +
                    "and th.metadata_field_id != null))");
        }}.toString();
    }

    /**
     * 根据id查询对象管理信息
     * @param id 对象id
     * @return
     */
    public String getMetaDataInfoById(Integer id) {
        return new SQL() {{
            SELECT("f.id,f.field_name,f.field_show_cn,f.field_show_en,f.field_type_name,f.field_paraphrase_cn,f.field_paraphrase_en,f.data_owner_id,f.filling_explanation");
            FROM("t_metadata_field_info f");
            WHERE("f.id = #{id}");
        }}.toString();
    }

    /**
     * 查询对象管理信息
     * @return
     */
    public String getMetaDataInfo(Integer metaDataObjId) {
        return new SQL() {{
            SELECT("f.id,f.field_name,f.field_show_cn,f.field_show_en,f.field_type_name,f.field_paraphrase_cn,f.field_paraphrase_en,f.data_owner_id,f.filling_explanation");
            FROM("t_metadata_field_info f");
            WHERE("f.object_id = #{metaDataObjId}");
        }}.toString();
    }


    /**
     * 删除的id字段
     *
     * @param fieldId 字段id
     * @return {@link String}
     */
    public String deleteFieldById(Integer fieldId) {

        return new SQL() {{
           DELETE_FROM("t_metadata_field_info");
           WHERE("id = #{fieldId}");
        }}.toString();
    }




}