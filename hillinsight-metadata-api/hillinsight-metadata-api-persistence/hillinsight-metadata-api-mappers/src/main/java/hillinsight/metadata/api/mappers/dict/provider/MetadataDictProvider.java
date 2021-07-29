package hillinsight.metadata.api.mappers.dict.provider;

import org.apache.ibatis.jdbc.SQL;


/**
 * 字典 db层
 *
 * @author wcy
 */
public class MetadataDictProvider {

    /**
     * 查询所有父级字典SQL
     *
     * @return
     */
    public String getParentDictList() {
        return new SQL() {{
            SELECT("d.id,d.code,d.name,d.dict_path,d.parent_id,d.remark,d.order_num");
            FROM("t_metadata_dict d");
            WHERE("d.parent_id = 0");
            ORDER_BY("d.order_num desc");
        }}.toString();

    }


    /**
     * 根据path查询字典列表SQL
     *
     * @return
     */
    public String getDictListByPath(String dictPath) {
        return new SQL() {{
            SELECT("d.id,d.code,d.name,d.dict_path,d.parent_id,d.remark,d.order_num");
            FROM("t_metadata_dict d");
            WHERE("d.dict_path = #{dictPath}");
            ORDER_BY("d.order_num");
        }}.toString();

    }


    /**
     * 根据code和path查询字典SQL
     *
     * @param code     代码
     * @param dictPath dict路径
     * @return {@link String}
     */
    public String getDictByCode(String code, String dictPath) {
        return new SQL() {{
            SELECT("d.id,d.code,d.name,d.dict_path,d.parent_id,d.remark,d.order_num");
            FROM("t_metadata_dict d");
            WHERE("d.dict_path = #{dictPath}");
            WHERE("d.code = #{code}");
        }}.toString();
    }


    /**
     * 根据name和path查询字典SQL
     *
     * @return
     */
    public String getDictByName(String name, String dictPath) {
        return new SQL() {{
            SELECT("d.id,d.code,d.name,d.dict_path,d.parent_id,d.remark,d.order_num");
            FROM("t_metadata_dict d");
            WHERE("d.dict_path = #{dictPath}");
            WHERE("d.name = #{name}");
        }}.toString();

    }


    /**
     * 获取业务字段类型中高级配置的所有字典列表
     *
     * @return {@link String}
     */
    public String getFTExpertConfigDictList() {
        return new SQL() {{
            SELECT("*");
            FROM("t_metadata_dict d");
            WHERE("d.remark = '*'");
        }}.toString();
    }
}