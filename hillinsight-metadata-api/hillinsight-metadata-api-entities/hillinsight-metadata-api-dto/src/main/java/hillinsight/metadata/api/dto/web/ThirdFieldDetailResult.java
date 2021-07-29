package hillinsight.metadata.api.dto.web;

import com.alibaba.fastjson.JSONObject;
import hillinsight.metadata.api.models.BaseModel;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 第三个字段详细结果
 *
 * @author wangchunyu
 * @date 2020/11/30
 */
public class ThirdFieldDetailResult extends BaseModel {

    private String fieldName;//字段名 相当于 业务方字段id
    private String showNameCn;//字段显示名(中文)
    private String showNameEn;//字段显示名(英文)
    private String metaFieldName;//元数据字段名
    private String metaShowNameCn;//元数据字段显示名 cn
    private String metahSowNameEn;//元数据字段显示名 en
    private String thirdObjectName;//所属业务对象名
    private Integer metadataFieldId;//元数据字段id

    private Integer isExtension;//是否为 自定义字段 1 是 0不是
    private String extensionName;//自定义字段名称
    private String fieldTypeCode;//字段类型 code值
    private String fieldTypeName;//字段类型  名称
    private String dataOwnerId;//数据所有者id
    private String dataOwnerName;//数据所有者名称

    private JSONObject fieldTypeExtendMap;//高级配置

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
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

    public String getMetaFieldName() {
        return metaFieldName;
    }

    public void setMetaFieldName(String metaFieldName) {
        this.metaFieldName = metaFieldName;
    }

    public String getMetaShowNameCn() {
        return metaShowNameCn;
    }

    public void setMetaShowNameCn(String metaShowNameCn) {
        this.metaShowNameCn = metaShowNameCn;
    }

    public String getMetahSowNameEn() {
        return metahSowNameEn;
    }

    public void setMetahSowNameEn(String metahSowNameEn) {
        this.metahSowNameEn = metahSowNameEn;
    }

    public String getThirdObjectName() {
        return thirdObjectName;
    }

    public void setThirdObjectName(String thirdObjectName) {
        this.thirdObjectName = thirdObjectName;
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

    public JSONObject getFieldTypeExtendMap() {
        return fieldTypeExtendMap;
    }

    public void setFieldTypeExtendMap(JSONObject fieldTypeExtendMap) {
        this.fieldTypeExtendMap = fieldTypeExtendMap;
    }
}
