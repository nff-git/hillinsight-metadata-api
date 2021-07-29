package hillinsight.metadata.api.dto.web.req;

import hillinsight.metadata.api.models.BaseModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @ClassName PageTempThirdFieldReq
 * @Description TODO 新增页面模板 添加业务字段 入参
 * @Author wcy
 * @Date 2020/12/9
 * @Version 1.0
 */
public class PageTempThirdFieldReq  extends BaseModel {

    private Integer thirdFieldId;//业务字段id

    private String thirdFieldName;//业务字段名称

    private Integer isCustomField;//是否为自定义字段  1是 0否

    private String fieldParaphraseCn;//字段释义中文

    private String fieldParaphraseEn;//字段释义中英文

    public Integer getThirdFieldId() {
        return thirdFieldId;
    }

    public void setThirdFieldId(Integer thirdFieldId) {
        this.thirdFieldId = thirdFieldId;
    }

    public String getThirdFieldName() {
        return thirdFieldName;
    }

    public void setThirdFieldName(String thirdFieldName) {
        this.thirdFieldName = thirdFieldName;
    }

    public Integer getIsCustomField() {
        return isCustomField;
    }

    public void setIsCustomField(Integer isCustomField) {
        this.isCustomField = isCustomField;
    }

    public String getFieldParaphraseCn() {
        return fieldParaphraseCn;
    }

    public void setFieldParaphraseCn(String fieldParaphraseCn) {
        this.fieldParaphraseCn = fieldParaphraseCn;
    }

    public String getFieldParaphraseEn() {
        return fieldParaphraseEn;
    }

    public void setFieldParaphraseEn(String fieldParaphraseEn) {
        this.fieldParaphraseEn = fieldParaphraseEn;
    }

    public  void initializedUserInfo(String userCode, String  userName){
        Date date = new Date();
        super.setCreatorId(userCode);
        super.setCreatorName(userName);
        super.setCreatorTime(date);
        super.setUpdatorId(userCode);
        super.setUpdatorName(userName);
        super.setUpdateTime(date);
    }
}
