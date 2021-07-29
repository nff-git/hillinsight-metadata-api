package hillinsight.metadata.api.dto.web;

/**
 * @ClassName TemplateConfigInfoFieldResult
 * @Description TODO
 * @Author wcy
 * @Date 2020/12/1
 * @Version 1.0
 */
public class TemplateConfigInfoFieldResult {

    private Integer id;//t_page_template 表中的主键id

    private Integer thirdFieldId;

    private String thirdFieldName;

    private Integer isCustomField;//是否为自定义字段  1是 0否

    private String fieldParaphraseCn;//字段释义中文

    private String fieldParaphraseEn;//字段释义中英文


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
