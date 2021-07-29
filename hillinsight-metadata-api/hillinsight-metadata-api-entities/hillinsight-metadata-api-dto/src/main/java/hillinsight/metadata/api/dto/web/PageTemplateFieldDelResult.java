package hillinsight.metadata.api.dto.web;

/**
 * @ClassName PageTemplateFieldDelResult
 * @Description TODO 页面模板字段详情实体
 * @Author wcy
 * @Date 2020/11/30
 * @Version 1.0
 */
public class PageTemplateFieldDelResult {

    private String thirdFieldName;//业务字段名

    private String thirdFieldShowNameCn;//业务字段显示名 中文

    private String thirdFieldShowNameEn;//业务字段显示名 英文

    private String metaFieldName;//元数据字段名

    private String metaFieldParaphraseCn;//元数据字段显示名 中文

    private String metaFieldParaphraseEn;//元数据字段显示名 英文

    public String getThirdFieldName() {
        return thirdFieldName;
    }

    public void setThirdFieldName(String thirdFieldName) {
        this.thirdFieldName = thirdFieldName;
    }

    public String getThirdFieldShowNameCn() {
        return thirdFieldShowNameCn;
    }

    public void setThirdFieldShowNameCn(String thirdFieldShowNameCn) {
        this.thirdFieldShowNameCn = thirdFieldShowNameCn;
    }

    public String getThirdFieldShowNameEn() {
        return thirdFieldShowNameEn;
    }

    public void setThirdFieldShowNameEn(String thirdFieldShowNameEn) {
        this.thirdFieldShowNameEn = thirdFieldShowNameEn;
    }

    public String getMetaFieldName() {
        return metaFieldName;
    }

    public void setMetaFieldName(String metaFieldName) {
        this.metaFieldName = metaFieldName;
    }

    public String getMetaFieldParaphraseCn() {
        return metaFieldParaphraseCn;
    }

    public void setMetaFieldParaphraseCn(String metaFieldParaphraseCn) {
        this.metaFieldParaphraseCn = metaFieldParaphraseCn;
    }

    public String getMetaFieldParaphraseEn() {
        return metaFieldParaphraseEn;
    }

    public void setMetaFieldParaphraseEn(String metaFieldParaphraseEn) {
        this.metaFieldParaphraseEn = metaFieldParaphraseEn;
    }
}
