package hillinsight.metadata.api.mappers.web.provider;

import com.github.pagehelper.Constant;
import hillinsight.metadata.api.web.*;
import org.apache.ibatis.jdbc.SQL;

/**
 * @ClassName FieldtypeExtendProvider
 * @Description TODO
 * @Author wcy
 * @Date 2021/4/16
 * @Version 1.0
 */
public class FieldtypeExtendProvider {


    /**
     * 插入字段类型金额扩展表
     *
     * @param fieldtypeExtendMoney 金额扩展表
     * @return {@link String}
     */
    public String insertMoney(FieldtypeExtendMoney fieldtypeExtendMoney) {

        return new SQL()
                .INSERT_INTO("t_fieldtype_extend_money")
                .VALUES("show_thousands,unit_transform_code,unit_transform_name,save_decimals_places," +
                                "unit_showname_cn,unit_showname_en,unit_showlocation_code,unit_showlocation_name," +
                                "money_scope_min,money_scope_max,default_value,is_must,is_employ,field_id,is_show_currency",
                        "#{showThousands},#{unitTransformCode},#{unitTransformName},#{saveDecimalsPlaces}" +
                                ",#{unitShownameCn},#{unitShownameEn},#{unitShowlocationCode},#{unitShowlocationName}" +
                                ",#{moneyScopeMin},#{moneyScopeMax},#{defaultValue},#{isMust},#{isEmploy},#{fieldId},#{isShowCurrency}").toString();
    }


    /**
     * 更新字段类型金额扩展表
     *
     * @param fieldtypeExtendMoney 金额扩展表
     * @return {@link String}
     */
    public String updateMoney(FieldtypeExtendMoney fieldtypeExtendMoney) {
        return new SQL() {{
            UPDATE("t_fieldtype_extend_money");
            SET("show_thousands = #{showThousands}," +
                    "unit_transform_code = #{unitTransformCode},unit_transform_name = #{unitTransformName},save_decimals_places = #{saveDecimalsPlaces}," +
                    "unit_showname_cn = #{unitShownameCn},unit_showname_en = #{unitShownameEn}," +
                    "unit_showlocation_code = #{unitShowlocationCode},unit_showlocation_name  = #{unitShowlocationName}," +
                    "money_scope_min  = #{moneyScopeMin},money_scope_max  = #{moneyScopeMax}," +
                    "default_value  = #{defaultValue},is_must = #{isMust},is_employ = #{isEmploy},field_id = #{fieldId},is_show_currency = #{isShowCurrency}");
            WHERE("id = #{id}");
        }}.toString();
    }


    /**
     * 插入字段类型数值扩展表
     *
     * @param fieldtypeExtendValue 数值扩展表
     * @return {@link String}
     */
    public String insertValue(FieldtypeExtendValue fieldtypeExtendValue) {

        return new SQL()
                .INSERT_INTO("t_fieldtype_extend_value")
                .VALUES("show_thousands,save_decimals_places,unit_showname_cn,unit_showname_en," +
                                "unit_showlocation_code,unit_showlocation_name,default_value,is_must," +
                                "is_employ,multiple_transform,number_scope_min,number_scope_max,field_id",
                        "#{showThousands},#{saveDecimalsPlaces},#{unitShownameCn},#{unitShownameEn}" +
                                ",#{unitShowlocationCode},#{unitShowlocationName},#{defaultValue},#{isMust}" +
                                ",#{isEmploy},#{multipleTransform},#{numberScopeMin},#{numberScopeMax},#{fieldId}").toString();
    }





    /**
     * 更新字段类型数值扩展表
     *
     * @param fieldtypeExtendValue 数值扩展值
     * @return {@link String}
     */
    public String updateValue(FieldtypeExtendValue fieldtypeExtendValue) {
        return new SQL() {{
            UPDATE("t_fieldtype_extend_value");
            SET("show_thousands = #{showThousands}," +
                    "save_decimals_places = #{saveDecimalsPlaces},unit_showname_cn = #{unitShownameCn},unit_showname_en = #{unitShownameEn}," +
                    "unit_showlocation_code = #{unitShowlocationCode},unit_showlocation_name = #{unitShowlocationName}," +
                    "default_value = #{defaultValue},is_must  = #{isMust}," +
                    "is_employ  = #{isEmploy},multiple_transform  = #{multipleTransform}," +
                    "number_scope_min  = #{numberScopeMin},number_scope_max = #{numberScopeMax},field_id = #{fieldId}");
            WHERE("id = #{id}");
        }}.toString();
    }


    /**
     * 插入字段类型百分比扩展表
     *
     * @param fieldtypeExtendPercent fieldtype扩展百分比
     * @return {@link String}
     */
    public String insertPercent(FieldtypeExtendPercent fieldtypeExtendPercent) {

        return new SQL()
                .INSERT_INTO("t_fieldtype_extend_percent")
                .VALUES("input_number_way_code,input_number_way_name,save_decimals_places,number_scope_min,number_scope_max," +
                                "default_value,is_must,is_employ,field_id" ,
                        "#{inputNumberWayCode},#{inputNumberWayName},#{saveDecimalsPlaces},#{numberScopeMin},#{numberScopeMax}" +
                                ",#{defaultValue},#{isMust},#{isEmploy},#{fieldId}").toString();
    }


    /**
     * 更新字段类型百分比扩展表
     *
     * @param fieldtypeExtendPercent 百分比扩展表
     * @return {@link String}
     */
    public String updatePercent(FieldtypeExtendPercent fieldtypeExtendPercent) {
        return new SQL() {{
            UPDATE("t_fieldtype_extend_percent");
            SET("input_number_way_code = #{inputNumberWayCode},input_number_way_name = #{inputNumberWayName}," +
                    "save_decimals_places = #{saveDecimalsPlaces},number_scope_min = #{numberScopeMin},number_scope_max = #{numberScopeMax}," +
                    "default_value = #{defaultValue},is_must = #{isMust}," +
                    "is_employ = #{isEmploy},field_id = #{fieldId}");
            WHERE("id = #{id}");
        }}.toString();
    }


    /**
     * 插入字段类型文本扩展表
     *
     * @param fieldtypeExtendText 文本扩展表
     * @return {@link String}
     */
    public String insertText(FieldtypeExtendText fieldtypeExtendText) {

        return new SQL()
                .INSERT_INTO("t_fieldtype_extend_text")
                .VALUES("line_type_code,line_type_name,max_number_limit,format_validata_code," +
                                "format_validata_name,default_value,is_must,is_employ,field_id" ,
                        "#{lineTypeCode},#{lineTypeName},#{maxNumberLimit},#{formatValidataCode}" +
                                ",#{formatValidataName},#{defaultValue},#{isMust},#{isEmploy},#{fieldId}").toString();
    }


    /**
     * 更新文本
     *
     * @param fieldtypeExtendText 文本扩展表
     * @return {@link String}
     */
    public String updateText(FieldtypeExtendText fieldtypeExtendText) {
        return new SQL() {{
            UPDATE("t_fieldtype_extend_text");
            SET("line_type_code = #{lineTypeCode}," +
                    "line_type_name = #{lineTypeName},max_number_limit = #{maxNumberLimit},format_validata_code = #{formatValidataCode}," +
                    "format_validata_name = #{formatValidataName},default_value = #{defaultValue},is_must = #{isMust}," +
                    "is_employ = #{isEmploy},field_id = #{fieldId}");
            WHERE("id = #{id}");
        }}.toString();
    }


    /**
     * 插入日期时间扩展表
     *
     * @param fieldtypeExtendDt 日期时间扩展表
     * @return {@link String}
     */
    public String insertDt(FieldtypeExtendDt fieldtypeExtendDt) {

        return new SQL()
                .INSERT_INTO("t_fieldtype_extend_dt")
                .VALUES("is_date,is_time,is_week,date_format_name," +
                                "date_format_code,time_format_name,time_format_code,week_format_name,week_format_code," +
                                "start_time_scope,end_time_scope,is_must,is_employ,field_id" ,
                        "#{isDate},#{isTime},#{isWeek},#{dateFormatName}" +
                                ",#{dateFormatCode},#{timeFormatName},#{timeFormatCode},#{weekFormatName},#{weekFormatCode}," +
                                "#{startTimeScope},#{endTimeScope},#{isMust},#{isEmploy},#{fieldId}").toString();
    }


    /**
     * 更新日期时间
     *
     * @param fieldtypeExtendDt fieldtype扩展dt
     * @return {@link String}
     */
    public String updateDt(FieldtypeExtendDt fieldtypeExtendDt) {
        return new SQL() {{
            UPDATE("t_fieldtype_extend_dt");
            SET("is_date = #{isDate},is_time = #{isTime},is_week = #{isWeek},date_format_name = #{dateFormatName},date_format_code = #{dateFormatCode}," +
                    "time_format_name = #{timeFormatName},time_format_code = #{timeFormatCode},week_format_name = #{weekFormatName}," +
                    "week_format_code = #{weekFormatCode},start_time_scope = #{startTimeScope},end_time_scope =#{endTimeScope},is_must = #{isMust}," +
                    "is_employ = #{isEmploy},field_id = #{fieldId}");
            WHERE("id = #{id}");
        }}.toString();
    }


    /**
     * 根据表名查询对应扩展表
     *
     * @param id              id
     * @param extendTableName 扩展表名
     * @return {@link String}
     */
    public String selectOneByTableName(Integer id,String extendTableName) {
        return new SQL() {{
            SELECT("*");
            if(extendTableName.equals("2008")){
                FROM("t_fieldtype_extend_dt ");
            }else if(extendTableName.equals("2004")){
                FROM("t_fieldtype_extend_money ");
            }else if(extendTableName.equals("2011")){
                FROM("t_fieldtype_extend_percent ");
            }else if(extendTableName.equals("2001")){
                FROM("t_fieldtype_extend_text ");
            }else if(extendTableName.equals("2003")){
                FROM("t_fieldtype_extend_value ");
            }
            WHERE(" field_id = #{id}");
        }}.toString();
    }



}
