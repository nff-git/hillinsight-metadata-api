package hillinsight.metadata.api.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import focus.mybatis.extensions.annotations.Id;
import hillinsight.metadata.api.models.BaseModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ThirdObjectInfo extends BaseModel {

    @TableId(type = IdType.AUTO)
    @Id
    private Integer  id;
    @NotBlank(message = "对象名不能为空")
    private String objectName;//对象名
    @NotBlank(message = "对象显示名(中文)不能为空")
    private String showNameCn;//对象显示名(中文)
    @NotBlank(message = "对象显示名(英文)不能为空")
    private String showNameEn;//对象显示名(英文)
    @NotBlank(message = "系统来源id不能为空")
    private String sourceId;//系统来源id
    @NotBlank(message = "系统来源名称不能为空")
    private String sourceName;//系统来源名称
//    @NotNull(message = "父级id不能为空")
    private Integer parentId;//父级id
//    @NotBlank(message = "描述不能为空")
    private String describe;//描述
//    @NotNull(message = "元数据对象id不能为空")
    private Integer metadataObjectId;//元数据对象id
    private Integer status;//1启用 0 禁用

    /**
     * 非数据库字段
     */
    private String metadataObjectName;//元数据对象名称

    private String metadataObjectShowName;//元数据对象显示名称

    private String parentObjName;//业务对象父对象名称

    private String parentObjShowNameCn;//业务对象父对象显示名称
    private String parentObjShowNameEn;//业务对象父对象显示名称


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
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


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getMetadataObjectId() {
        return metadataObjectId;
    }

    public void setMetadataObjectId(Integer metadataObjectId) {
        this.metadataObjectId = metadataObjectId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getMetadataObjectName() {
        return metadataObjectName;
    }

    public void setMetadataObjectName(String metadataObjectName) {
        this.metadataObjectName = metadataObjectName;
    }

    public String getMetadataObjectShowName() {
        return metadataObjectShowName;
    }

    public void setMetadataObjectShowName(String metadataObjectShowName) {
        this.metadataObjectShowName = metadataObjectShowName;
    }

    public String getParentObjName() {
        return parentObjName;
    }

    public void setParentObjName(String parentObjName) {
        this.parentObjName = parentObjName;
    }

    public String getParentObjShowNameCn() {
        return parentObjShowNameCn;
    }

    public void setParentObjShowNameCn(String parentObjShowNameCn) {
        this.parentObjShowNameCn = parentObjShowNameCn;
    }

    public String getParentObjShowNameEn() {
        return parentObjShowNameEn;
    }

    public void setParentObjShowNameEn(String parentObjShowNameEn) {
        this.parentObjShowNameEn = parentObjShowNameEn;
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
