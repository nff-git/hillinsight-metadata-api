package hillinsight.metadata.api.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import focus.mybatis.extensions.annotations.Id;
import hillinsight.metadata.api.models.BaseModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ThirdFieldInfo extends BaseModel {

    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;
    @NotBlank(message = "字段不名能为空")
    private String fieldName;//字段名 相当于 业务方字段id
    @NotBlank(message = "系统来源id不能为空")
    private String sourceId;//系统来源id
    @NotBlank(message = "系统来源名称不能为空")
    private String sourceName;//系统来源名称
    @NotBlank(message = "字段类型不能为空")
    private String fieldTypeCode;//字段类型 code值
    @NotBlank(message = "字段类型不能为空")
    private String fieldTypeName;//字段类型  名称
    @NotBlank(message = "字段显示名(中文)不能为空")
    private String showNameCn;//字段显示名(中文)
    @NotBlank(message = "字段显示名(英文)不能为空")
    private String showNameEn;//字段显示名(英文)
//    @NotNull(message = "元数据对象id不能为空")
    private Integer metadataFieldId;//元数字段id
    private Integer status;//1启用 0禁用
    @NotNull(message = "业务对象id不能为空")
    private Integer thirdObjectId;//业务对象id
    @NotNull(message = "是否为自定义字段不能为空")
    private Integer isExtension;//是否为 自定义字段 1 是 0不是
    private String extensionName;//自定义字段名称

    private String metadataFieldName;//元数据字段名 临时变量
    private String statusName;//字段状态名 临时变量
    private String updateTimeStr;//修改时间 字符串格式

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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }


    public String getShowNameCn() {
        return showNameCn;
    }

    public void setShowNameCn(String showNameCn) {
        this.showNameCn = showNameCn;
    }

    public String getShowNameEn() {
        return showNameEn;
    }

    public void setShowNameEn(String showNameEn) {
        this.showNameEn = showNameEn;
    }

    public Integer getMetadataFieldId() {
        return metadataFieldId;
    }

    public void setMetadataFieldId(Integer metadataFieldId) {
        this.metadataFieldId = metadataFieldId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMetadataFieldName() {
        return metadataFieldName;
    }

    public void setMetadataFieldName(String metadataFieldName) {
        this.metadataFieldName = metadataFieldName;
    }

    public Integer getThirdObjectId() {
        return thirdObjectId;
    }

    public void setThirdObjectId(Integer thirdObjectId) {
        this.thirdObjectId = thirdObjectId;
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

    public Integer getIsExtension() {
        return isExtension;
    }

    public void setIsExtension(Integer isExtension) {
        this.isExtension = isExtension;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
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
