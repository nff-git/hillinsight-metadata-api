package hillinsight.metadata.api.dao.web;

import com.alibaba.fastjson.JSONObject;
import hillinsight.metadata.api.web.*;

public interface FieldTypeExtendDao {


    void insertMoney(FieldtypeExtendMoney fieldtypeExtendMoney);

    void updateMoney(FieldtypeExtendMoney fieldtypeExtendMoney);

    void insertValue(FieldtypeExtendValue fieldtypeExtendValue);

    void updateValue(FieldtypeExtendValue fieldtypeExtendValue);

    void insertPercent(FieldtypeExtendPercent fieldtypeExtendPercent);

    void updatePercent(FieldtypeExtendPercent fieldtypeExtendPercent);

    void insertText(FieldtypeExtendText fieldtypeExtendText);

    void updateText(FieldtypeExtendText fieldtypeExtendText);

    void insertDt(FieldtypeExtendDt fieldtypeExtendDt);

    void updateDt(FieldtypeExtendDt fieldtypeExtendDt);

    JSONObject selectOneByTableName(Integer id, String extendTableName);
}
