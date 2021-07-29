package hillinsight.metadata.api.dao.impl.web;

import com.alibaba.fastjson.JSONObject;
import hillinsight.metadata.api.dao.web.FieldTypeExtendDao;
import hillinsight.metadata.api.mappers.web.FieldTypeExtendMapper;
import hillinsight.metadata.api.web.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @ClassName FieldTypeExtendDaoImpl
 * @Description TODO
 * @Author wcy
 * @Date 2021/4/16
 * @Version 1.0
 */
@Repository
public class FieldTypeExtendDaoImpl implements FieldTypeExtendDao {

    @Autowired
    private FieldTypeExtendMapper fieldTypeExtendMapper;


    @Override
    public void insertMoney(FieldtypeExtendMoney fieldtypeExtendMoney) {
        fieldTypeExtendMapper.insertMoney(fieldtypeExtendMoney);
    }

    @Override
    public void updateMoney(FieldtypeExtendMoney fieldtypeExtendMoney) {
        fieldTypeExtendMapper.updateMoney(fieldtypeExtendMoney);
    }

    @Override
    public void insertValue(FieldtypeExtendValue fieldtypeExtendValue) {
        fieldTypeExtendMapper.insertValue(fieldtypeExtendValue);
    }

    @Override
    public void updateValue(FieldtypeExtendValue fieldtypeExtendValue) {
        fieldTypeExtendMapper.updateValue(fieldtypeExtendValue);
    }

    @Override
    public void insertPercent(FieldtypeExtendPercent fieldtypeExtendPercent) {
        fieldTypeExtendMapper.insertPercent(fieldtypeExtendPercent);
    }

    @Override
    public void updatePercent(FieldtypeExtendPercent fieldtypeExtendPercent) {
        fieldTypeExtendMapper.updatePercent(fieldtypeExtendPercent);
    }

    @Override
    public void insertText(FieldtypeExtendText fieldtypeExtendText) {
        fieldTypeExtendMapper.insertText(fieldtypeExtendText);
    }

    @Override
    public void updateText(FieldtypeExtendText fieldtypeExtendText) {
        fieldTypeExtendMapper.updateText(fieldtypeExtendText);
    }

    @Override
    public void insertDt(FieldtypeExtendDt fieldtypeExtendDt) {
        fieldTypeExtendMapper.insertDt(fieldtypeExtendDt);
    }

    @Override
    public void updateDt(FieldtypeExtendDt fieldtypeExtendDt) {
        fieldTypeExtendMapper.updateDt(fieldtypeExtendDt);
    }

    @Override
    public JSONObject selectOneByTableName(Integer id, String extendTableName) {
        return fieldTypeExtendMapper.selectOneByTableName(id,extendTableName);
    }
}
