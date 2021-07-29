package hillinsight.metadata.api.models.vo;

import lombok.Builder;

import java.io.Serializable;

/**
 * @ClassName 元数据导入copy实体
 * @Description TODO
 * @Author wcy
 * @Date 2021/2/1
 * @Version 1.0
 */
public class MetaFieldExcelimportCopy implements Serializable {

    private String idNum;//编号

    private String fieldName;//字段名

    private String fieldShowCn;//字段显示名(中文)

    private String fieldShowEn;//字段显示名（英文）

    private String fieldTypeName;//字段类型name

    private String fieldParaphraseCn;//字段释义中文

    private String fieldParaphraseEn;//字段释义英文

    private String dataOwnerId;//数据所有者id

    private String fillingExplanation;//填写说明

    private String misTakeMsg;//错误信息

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldShowCn() {
        return fieldShowCn;
    }

    public void setFieldShowCn(String fieldShowCn) {
        this.fieldShowCn = fieldShowCn;
    }

    public String getFieldShowEn() {
        return fieldShowEn;
    }

    public void setFieldShowEn(String fieldShowEn) {
        this.fieldShowEn = fieldShowEn;
    }

    public String getFieldTypeName() {
        return fieldTypeName;
    }

    public void setFieldTypeName(String fieldTypeName) {
        this.fieldTypeName = fieldTypeName;
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

    public String getDataOwnerId() {
        return dataOwnerId;
    }

    public void setDataOwnerId(String dataOwnerId) {
        this.dataOwnerId = dataOwnerId;
    }

    public String getFillingExplanation() {
        return fillingExplanation;
    }

    public void setFillingExplanation(String fillingExplanation) {
        this.fillingExplanation = fillingExplanation;
    }

    public String getMisTakeMsg() {
        return misTakeMsg;
    }

    public void setMisTakeMsg(String misTakeMsg) {
        this.misTakeMsg = misTakeMsg;
    }
}
