package hillinsight.metadata.api.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import focus.mybatis.extensions.annotations.Id;
import hillinsight.metadata.api.models.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class MetaDataFieldInfo extends BaseModel {

    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;
    @NotBlank(message = "字段名不能为空")
    @Length(message = "单行文本长度不能超过64个字符", max = 32)
    private String fieldName;//字段名
    @NotBlank(message = "字段显示名(中文)不能为空")
    @Length(message = "单行文本长度不能超过64个字符", max = 32)
    private String fieldShowCn;//字段显示名(中文)
    @Length(message = "单行文本长度不能超过64个字符", max = 32)
    @NotBlank(message = "字段显示名(英文)不能为空")
    private String fieldShowEn;//字段显示名（英文）
    private String fieldTypeCode;//字段类型code
    private String fieldTypeName;//字段类型name
    @Length(message = "多行文本长度不能超过512个字符", max = 70000)
    private String fieldParaphraseCn;//字段释义中文
    @Length(message = "多行文本长度不能超过512个字符", max = 70000)
    private String fieldParaphraseEn;//字段释义英文
    private String dataOwnerId;//数据所有者id
    private String dataOwnerName;//数据所有者名称
    @Length(message = "多行文本长度不能超过512个字符", max = 70000)
    private String fillingExplanation;//填写说明
    private String source;//使用系统  ideal;mFront
    private Integer status;//字段状态  1使用 0禁用
    @NotNull(message = "元数据对象id不能为空")
    private Integer objectId;//元数据对象表id


    /**
     * 非数据库字段
     */
    private String statusName;//字段状态名称

    private String objectName;//元数据对象名称

    private Integer objStatus;//元数据对象状态


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDataOwnerName() {
        return dataOwnerName;
    }

    public void setDataOwnerName(String dataOwnerName) {
        this.dataOwnerName = dataOwnerName;
    }

    public String getFillingExplanation() {
        return fillingExplanation;
    }

    public void setFillingExplanation(String fillingExplanation) {
        this.fillingExplanation = fillingExplanation;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getFieldTypeCode() {
        return fieldTypeCode;
    }

    public void setFieldTypeCode(String fieldTypeCode) {
        this.fieldTypeCode = fieldTypeCode;
    }

    public String getFieldTypeName() {
        return fieldTypeName;
    }

    public void setFieldTypeName(String fieldTypeName) {
        this.fieldTypeName = fieldTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Integer getObjStatus() {
        return objStatus;
    }

    public void setObjStatus(Integer objStatus) {
        this.objStatus = objStatus;
    }

    public void initializedUserInfo(String userCode, String userName) {
        Date date = new Date();
        super.setCreatorId(userCode);
        super.setCreatorName(userName);
        super.setCreatorTime(date);
        super.setUpdatorId(userCode);
        super.setUpdatorName(userName);
        super.setUpdateTime(date);
    }
}
