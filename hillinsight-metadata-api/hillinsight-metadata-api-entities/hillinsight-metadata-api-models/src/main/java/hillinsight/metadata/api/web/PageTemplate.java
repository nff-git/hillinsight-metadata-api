package hillinsight.metadata.api.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import focus.mybatis.extensions.annotations.Id;
import hillinsight.metadata.api.models.BaseModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @ClassName PageTemplate
 * @Description TODO
 * @Author wcy
 * @Date 2020/11/30
 * @Version 1.0
 */
public class PageTemplate extends BaseModel {

    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;

    //@NotNull(message = "业务对象id不能为空")
    private Integer thirdObjId;//业务对象id

    @NotBlank(message = "业务对象名称不能为空")
    private String thirdObjName;//业务对象名称

   // @NotNull(message = "业务字段id不能为空")
    private Integer thirdFieldId;//业务字段id

    @NotBlank(message = "业务字段名称不能为空")
    private String thirdFieldName;//业务字段名称

    @NotNull(message = "页面id不能为空")
    private Integer pageId;//页面id

    private Integer isCustomField;//是否为自定义字段  1是 0否

    private String fieldParaphraseCn;//字段释义中文

    private String fieldParaphraseEn;//字段释义中英文

    /**
     * 导入转换时使用
     */

    private String creatorId;//创建人id
    private String creatorName;//创建人名称
    private Date creatorTime;//创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getThirdObjId() {
        return thirdObjId;
    }

    public void setThirdObjId(Integer thirdObjId) {
        this.thirdObjId = thirdObjId;
    }

    public String getThirdObjName() {
        return thirdObjName;
    }

    public void setThirdObjName(String thirdObjName) {
        this.thirdObjName = thirdObjName;
    }



    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getIsCustomField() {
        return isCustomField;
    }

    public void setIsCustomField(Integer isCustomField) {
        this.isCustomField = isCustomField;
    }



    @Override
    public String getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String getCreatorName() {
        return creatorName;
    }

    @Override
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    public Date getCreatorTime() {
        return creatorTime;
    }

    @Override
    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
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

    @Override
    public String toString() {
        return "PageTemplate{" +
                "id=" + id +
                ", thirdObjId=" + thirdObjId +
                ", thirdObjName='" + thirdObjName + '\'' +
                ", thirdFieldId=" + thirdFieldId +
                ", thirdFieldName='" + thirdFieldName + '\'' +
                ", pageId=" + pageId +
                ", isCustomField=" + isCustomField +
                ", fieldParaphraseCn='" + fieldParaphraseCn + '\'' +
                ", fieldParaphraseEn='" + fieldParaphraseEn + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", creatorTime=" + creatorTime +
                '}';
    }

    public  void initializedUserInfo(String userCode, String  userName){
        Date date = new Date();
        this.setCreatorId(userCode);
        this.setCreatorName(userName);
        this.setCreatorTime(date);
        super.setUpdatorId(userCode);
        super.setUpdatorName(userName);
        super.setUpdateTime(date);
    }
}
