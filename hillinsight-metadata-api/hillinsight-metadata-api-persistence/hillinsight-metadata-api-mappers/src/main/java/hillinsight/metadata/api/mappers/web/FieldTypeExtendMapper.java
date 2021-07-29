package hillinsight.metadata.api.mappers.web;

import com.alibaba.fastjson.JSONObject;
import hillinsight.metadata.api.mappers.web.provider.FieldtypeExtendProvider;
import hillinsight.metadata.api.mappers.web.provider.MetaDataInfoProvider;
import hillinsight.metadata.api.web.*;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldTypeExtendMapper {

    //添加字段类型金额扩展表
    @InsertProvider(type = FieldtypeExtendProvider.class, method = "insertMoney")
    void insertMoney(FieldtypeExtendMoney fieldtypeExtendMoney);

    //修改字段类型金额扩展表
    @UpdateProvider(type = FieldtypeExtendProvider.class, method = "updateMoney")
    void updateMoney(FieldtypeExtendMoney fieldtypeExtendMoney);

    //添加字段类型数值扩展表
    @InsertProvider(type = FieldtypeExtendProvider.class, method = "insertValue")
    void insertValue(FieldtypeExtendValue fieldtypeExtendValue);

    //修改字段类型数值扩展表
    @UpdateProvider(type = FieldtypeExtendProvider.class, method = "updateValue")
    void updateValue(FieldtypeExtendValue fieldtypeExtendValue);

    //添加字段类型百分比扩展表
    @InsertProvider(type = FieldtypeExtendProvider.class, method = "insertPercent")
    void insertPercent(FieldtypeExtendPercent fieldtypeExtendPercent);

    //修改字段类型百分比扩展表
    @UpdateProvider(type = FieldtypeExtendProvider.class, method = "updatePercent")
    void updatePercent(FieldtypeExtendPercent fieldtypeExtendPercent);

    //添加字段类型文本扩展表
    @InsertProvider(type = FieldtypeExtendProvider.class, method = "insertText")
    void insertText(FieldtypeExtendText fieldtypeExtendText);

    //修改字段类型百分比扩展表
    @UpdateProvider(type = FieldtypeExtendProvider.class, method = "updateText")
    void updateText(FieldtypeExtendText fieldtypeExtendText);

    //添加字段类型日期扩展表
    @InsertProvider(type = FieldtypeExtendProvider.class, method = "insertDt")
    void insertDt(FieldtypeExtendDt fieldtypeExtendDt);

    //修改字段类型日期扩展表
    @UpdateProvider(type = FieldtypeExtendProvider.class, method = "updateDt")
    void updateDt(FieldtypeExtendDt fieldtypeExtendDt);

    @SelectProvider(type = FieldtypeExtendProvider.class, method = "selectOneByTableName")
    JSONObject selectOneByTableName(Integer id, String extendTableName);
}
