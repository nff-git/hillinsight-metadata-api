package hillinsight.metadata.api.models;

import hillinsight.metadata.api.models.group.GroupInsert;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class BaseModel {

    private String creatorId;//创建人id
    private String creatorName;//创建人名称
    private Date creatorTime;//创建时间
    private String updatorId;//修改人id
    private String updatorName;//修改人名称
    private Date updateTime;//修改时间


    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
