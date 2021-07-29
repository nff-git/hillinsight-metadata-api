package hillinsight.metadata.api.mappers.web.provider;

import cn.hutool.core.util.StrUtil;
import hillinsight.metadata.api.web.ThirdFieldInfo;
import hillinsight.metadata.api.web.ThirdObjectInfo;
import org.apache.ibatis.jdbc.SQL;

public class ThirdInfoProvider {

    /**
     * 添加业务对象信息 SQL
     *
     * @param thirdObjectInfo
     * @return
     */
    public String addThirdObjectInfo(ThirdObjectInfo thirdObjectInfo) {

        return new SQL()
                .INSERT_INTO("t_third_object_info")
                .VALUES("object_name," +
                                "show_name_cn, " +
                                "show_name_en," +
                                "source_id," +
                                "source_name," +
                                "parent_id," +
                                "describe," +
                                "metadata_object_id," +
                                "creator_id," +
                                "creator_name," +
                                "creator_time," +
                                "updator_id," +
                                "updator_name," +
                                "update_time," +
                                "status",
                        "#{objectName}," +
                                "#{showNameCn}, " +
                                "#{showNameEn}," +
                                "#{sourceId}," +
                                "#{sourceName}," +
                                "#{parentId}," +
                                "#{describe}," +
                                "#{metadataObjectId}," +
                                "#{creatorId}," +
                                "#{creatorName}," +
                                "#{creatorTime}," +
                                "#{updatorId}," +
                                "#{updatorName}," +
                                "#{updateTime}," +
                                "#{status}").toString();
    }

    public String updateThirdObjectInfo(ThirdObjectInfo thirdObjectInfo) {
        return new SQL() {{
            UPDATE("t_third_object_info");
            SET("object_name = #{objectName}," +
                    "show_name_cn = #{showNameCn}," +
                    "show_name_en = #{showNameEn}," +
                    "parent_id = #{parentId}," +
                    "describe = #{describe}," +
                    "status = #{status}," +
                    "updator_id = #{updatorId}," +
                    "updator_name = #{updatorName}," +
                    "update_time = #{updateTime}");
            WHERE("id = #{id}");
        }}.toString();

    }

    /**
     * 根据业务对象id查询对象信息 SQL
     * @param thirdObjectId
     * @return
     */
    public String getThirdObjectInfoById(Integer thirdObjectId) {
        return new SQL() {{
            SELECT("f.id, f.object_name, f.show_name_cn,f.show_name_en,f.source_id," +
                    "f.source_name,f.parent_id,f.describe,f.metadata_object_id," +
                    "f.creator_id,f.creator_name,f.creator_time," +
                    "f.updator_id,f.updator_name,f.update_time,f.status");
            FROM("t_third_object_info f");
            WHERE("f.id = #{thirdObjectId}");
        }}.toString();

    }


    /**
     * 添加业务对字段信息 SQL
     *
     * @param thirdFieldInfo
     * @return
     */
    public String addThirdFieldInfo(ThirdFieldInfo thirdFieldInfo) {

        return new SQL() {
            {
                INSERT_INTO("t_third_field_info");
                VALUES("field_name," +
                                "source_id, " +
                                "source_name," +
                                "field_type_code," +
                                "field_type_name," +
                                "show_name_cn," +
                                "show_name_en," +
                                "metadata_field_id," +
                                "creator_id," +
                                "creator_name," +
                                "creator_time," +
                                "updator_id," +
                                "updator_name," +
                                "update_time," +
                                "status," +
                                "third_object_id," +
                                "is_extension," +
                                "extension_name",
                        "#{fieldName}," +
                                "#{sourceId}, " +
                                "#{sourceName}," +
                                "#{fieldTypeCode}," +
                                "#{fieldTypeName}," +
                                "#{showNameCn}," +
                                "#{showNameEn}," +
                                "#{metadataFieldId}," +
                                "#{creatorId}," +
                                "#{creatorName}," +
                                "#{creatorTime}," +
                                "#{updatorId}," +
                                "#{updatorName}," +
                                "#{updateTime}," +
                                "#{status},#{thirdObjectId},#{isExtension},#{extensionName}");

            }
        }.toString();


    }

    public String updateThirdFieldInfo(ThirdFieldInfo thirdFieldInfo) {
        return new SQL() {{
            UPDATE("t_third_field_info");
            SET("show_name_cn = #{showNameCn}," +
                    "show_name_en = #{showNameEn}," +
                    "metadata_field_id = #{metadataFieldId}," +
                    "updator_id = #{updatorId}," +
                    "updator_name = #{updatorName}," +
                    "update_time = #{updateTime}");
            WHERE("id = #{id}");
        }}.toString();

    }


    /**
     * 根据业务对象id查询字段列表 SQL
     *
     * @param thirdObjectId
     * @param criteria      查询条件
     * @return
     */
    public String getFieldListByObjId(Integer thirdObjectId, String criteria) {
        return new SQL() {{
            SELECT("f.id, f.field_name, f.field_type_code,f.field_type_name,f.source_id,f.source_name," +
                    "f.show_name_cn,f.show_name_en,f.metadata_field_id," +
                    "f.creator_id,f.creator_name,f.creator_time," +
                    "f.updator_id,f.updator_name,f.update_time,f.status,f.third_object_id,is_extension,extension_name");
            FROM("t_third_field_info f");
            WHERE("f.third_object_id = #{thirdObjectId}");
            if (StrUtil.isNotEmpty(criteria)) {
                WHERE("(f.field_name like #{criteria} or f.show_name_cn like #{criteria} or f.show_name_en like #{criteria})");
            }
            ORDER_BY("f.creator_time");
        }}.toString();
    }


    /**
     * 查询业务字段详情 SQL
     *
     * @param thirdFieldId
     * @return
     */
    public String getFieldDetailById(Integer thirdFieldId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_third_field_info f");
            WHERE("f.id = #{thirdFieldId}");
        }}.toString();
    }


    /**
     * 业务字段启用停用
     *
     * @param thirdFieldInfo
     * @return
     */
    public String thirdfieldBlockUp(ThirdFieldInfo thirdFieldInfo) {
        return new SQL() {{
            UPDATE("t_third_field_info");
            SET("status = #{status},updator_id = #{updatorId},updator_name = #{updatorName},update_time  =#{updateTime}");
            WHERE("id = #{id}");
        }}.toString();

    }


    /**
     * 查询业务字段使用系统集合
     *
     * @param id
     * @return
     */
    public String getFieldSysListByMetaFieldId(Integer id) {
        return new SQL() {{
            SELECT("metadata_field_id as id,stuff((select distinct ';'+source_name from t_third_field_info where a.metadata_field_id=metadata_field_id for xml path('')),1,1,'') AS items");
            FROM("t_third_field_info a");
            WHERE(" metadata_field_id = #{id}");
            GROUP_BY("metadata_field_id");


        }}.toString();

    }


    /**
     * 根据业务对象名和来源查询业务对象信息 SQL
     *
     * @param objectName
     * @param sourceId
     * @return
     */
    public String getThirdObjInfoByNameAndSourceId(String objectName,String sourceId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_third_object_info f");
            WHERE("f.object_name = #{objectName} and f.source_id = #{sourceId}");
        }}.toString();

    }

    /**
     * 根据业务对象名查询业务对象信息 SQL
     *
     * @param fieldName
     * @param thirdObjectId
     * @return
     */
    public String getThirdFieldByName(String fieldName, Integer thirdObjectId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_third_field_info f");
            WHERE("f.field_name = #{fieldName} and f.third_object_id = #{thirdObjectId}");
        }}.toString();

    }


    /**
     * 获取业务对象  支持模糊查询
     *
     * @param sourceId 源id
     * @param criteria 标准
     * @return {@link String}
     */
    public String getThirdObjectList(String sourceId, String criteria) {
        return new SQL() {{
            SELECT("*");
            FROM("t_third_object_info th");
            WHERE(" th.source_id = #{sourceId}");
            if (StrUtil.isNotEmpty(criteria)) {
                WHERE("th.object_name like #{criteria} or th.show_name_cn like #{criteria} or th.show_name_en like #{criteria}");
            }
            ORDER_BY("th.status desc,th.update_time desc");
        }}.toString();
    }


    /**
     * 根据业务对象id查询字段信息
     *
     * @param thirdObjectId 第三个对象id
     * @return {@link String}
     */
    public String getThirdFieldByObjId(Integer thirdObjectId) {

        return new SQL() {{
            SELECT("*");
            FROM("t_third_field_info");
            WHERE("third_object_id = #{thirdObjectId} and status != 0");
        }}.toString();

    }

    /**
     * 根据业务对象id和自定义字段名称查询字段信息
     *
     * @param thirdObjId 第三个对象id
     * @return {@link String}
     */
    public String getThirdFieldByObjIdAndExt(Integer thirdObjId, String extensionName) {

        return new SQL() {{
            SELECT("*");
            FROM("t_third_field_info");
            WHERE("third_object_id = #{thirdObjId}");
            WHERE("extension_name = #{extensionName}");
        }}.toString();

    }


    /**
     * 根据业务字段id和业务对象id查询字段信息
     *
     * @param thirdFieldjId 业务字段id
     * @param thirdObjId    业务对象id
     * @return {@link ThirdFieldInfo}
     */
    public String getThirdFieldByIdAndObjId(Integer thirdFieldjId, Integer thirdObjId) {

        return new SQL() {{
            SELECT("*");
            FROM("t_third_field_info");
            WHERE("id = #{thirdFieldjId}");
            WHERE("third_object_id = #{thirdObjId}");
        }}.toString();

    }


    /**
     * 获取业务系统父对象列表（新增业务对象时 绑定父对象使用）
     *
     * @param sourceId 源id
     * @return {@link String}
     */
    public String getThirdParentObjList(String sourceId) {

        return new SQL() {{
            SELECT("*");
            FROM("t_third_object_info th");
            WHERE("th.status != 0 and th.source_id = #{sourceId}");
            ORDER_BY("th.id");
        }}.toString();

    }

    public String thirdObjUseOrBlockUp(ThirdObjectInfo thirdObjectInfo) {
        return new SQL() {{
            UPDATE("t_third_object_info");
            SET("status = #{status}," +
                    "updator_id = #{updatorId}," +
                    "updator_name = #{updatorName}," +
                    "update_time = #{updateTime}");
            WHERE("id = #{id}");
        }}.toString();

    }


    /**
     * 查询业务对象根据对象id和名称
     *
     * @param thirdObjectId 业务对象id
     * @param objectName    对象名称
     * @return {@link String}
     */
    public String getThirdObjectInfoByIdAndName(Integer thirdObjectId, String objectName) {
        return new SQL() {{
            SELECT("*");
            FROM("t_third_object_info f");
            WHERE("f.id = #{thirdObjectId} and f.object_name =#{objectName} and f.status != 0");
        }}.toString();

    }

    /**
     * 根据业务字段id和业务对象id查询字段信息
     *
     * @param thirdObjId 业务对象id
     * @param id    元数据字段id
     * @return {@link ThirdFieldInfo}
     */
    public String getThirdFieldByThirdObjIdAndMetaFeildId(Integer thirdObjId, Integer id) {
        return new SQL() {{
            SELECT("*");
            FROM("t_third_field_info");
            WHERE("third_object_id = #{thirdObjId}");
            WHERE("metadata_field_id = #{id}");
        }}.toString();

    }


    /**
     * 获取业务对象信息根据 对象名称和系统来源
     *
     * @param thirdObjName 业务对象名称
     * @param sourceId     源id
     * @return {@link String}
     */
    public String getThirdObjectInfoByNameAndSourceId(String thirdObjName, String sourceId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_third_object_info f");
            WHERE(" f.object_name collate Chinese_PRC_CS_AS =#{thirdObjName} and f.source_id = #{sourceId} and  f.status != 0");
        }}.toString();

    }


    /**
     * 根据业务对象id和字段名称获取业务字段信息
     *
     * @param thirdFieldName 第三个字段名
     * @param thirdObjId     第三obj id
     * @return {@link String}
     */
    public String getThirdFieldByNameAndObjId(String thirdFieldName, Integer thirdObjId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_third_field_info f");
            WHERE("f.field_name collate Chinese_PRC_CS_AS = #{thirdFieldName} and f.status != 0 and f.third_object_id = #{thirdObjId}");
        }}.toString();
    }


    /**
     * 跟据业务字段名称和系统来源获取业务字段信息
     *
     * @param thirdFieldName 第三个字段名
     * @param sourceId       源id
     * @return {@link String}
     */
    public String getFieldInfoByNameAndSourceIdAndObjId(String thirdFieldName, String sourceId,Integer thirdObjId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_third_field_info f");
            WHERE("f.field_name = #{thirdFieldName}");
            WHERE("f.source_id = #{sourceId}");
            WHERE("f.third_object_id = #{thirdObjId}");
        }}.toString();
    }


    /**
     * 删除的id字段
     *
     * @param id id
     * @return {@link String}
     */
    public String deleteFieldById(Integer id) {
        return new SQL() {{
            DELETE_FROM("t_third_field_info");
            WHERE("id = #{id}");
        }}.toString();
    }

    /**
     * 根据id导出业务对象Excel
     *
     * @param id
     * @return
     */
    public String getThirdDataInfoById(Integer id) {
        return new SQL() {{
            SELECT("tf.id,tf.field_name,tf.field_type_name,tf.show_name_cn,tf.show_name_en,tf.is_extension,tf.extension_name,mf.field_name as metadataFieldName,mo.object_name as metadataObjName,tf.field_type_code as fieldTypeCode");
            FROM("t_third_field_info tf LEFT JOIN t_metadata_field_info mf ON tf.metadata_field_id = mf.id\n" +
                    "\tLEFT JOIN t_metadata_object_info mo ON mf.object_id = mo.id");
                WHERE("tf.id = #{id}" );

        }}.toString();
    }

    /**
     * 导出业务对象Excel
     *
     * @param
     * @return
     */
    public String getThirdDataInfoByUsercode(Integer thirdObjectId,String sourceId) {
        return new SQL() {{
            SELECT("tf.id,tf.field_name,tf.field_type_name,tf.show_name_cn,tf.show_name_en,tf.is_extension,tf.extension_name,mf.field_name as metadataFieldName,mo.object_name as metadataObjName,tf.field_type_code as fieldTypeCode");
            FROM("t_third_field_info tf LEFT JOIN t_metadata_field_info mf ON tf.metadata_field_id = mf.id\n" +
                    "\tLEFT JOIN t_metadata_object_info mo ON mf.object_id = mo.id");
                WHERE("tf.third_object_id = #{thirdObjectId} and tf.source_id = #{sourceId}");
        }}.toString();
    }


    /**
     * 根据元数据字段id获取业务字段信息
     *
     * @param metaDataFieldId 元数据字段id
     * @return {@link String}
     */
    public String getThirdFieldInfoByMetaFieldId(Integer metaDataFieldId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_third_field_info f");
            WHERE("f.metadata_field_id = #{metaDataFieldId}");
        }}.toString();
    }


    /**
     * 根据系统来源查询业务对象集合
     *
     * @param sourceId 源id
     * @return {@link String}
     */
    public String getThirdObjectInfoBySourceId(String sourceId) {
        return new SQL() {{
            SELECT("*");
            FROM("t_third_object_info f");
            WHERE("f.source_id = #{sourceId}");
            WHERE("f.status != 0");
        }}.toString();
    }
}