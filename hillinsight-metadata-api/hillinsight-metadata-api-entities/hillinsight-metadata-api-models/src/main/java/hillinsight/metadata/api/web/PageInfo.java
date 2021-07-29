package hillinsight.metadata.api.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import focus.mybatis.extensions.annotations.Id;
import focus.mybatis.extensions.annotations.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Table(tableName = "t_metadata_page")
@TableName("t_metadata_page")
public class PageInfo implements Serializable {

    private static final long serialVersionUID = 5409211263344044359L;

    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;

    @NotBlank(message = "页面标题不能为空")
    private String pageTitle;//页面标题

    private String pageKey;//pagekey

    private String pageGroup;//页面分组

    @NotBlank(message = "页面描述不能为空")
    private String pageExplanation;//页面描述

    private String creatorId;

    private String creatorName;

    private Date createTime;

    private String updatorId;

    private String updatorName;

    private Date updateTime;

    private String delFlag;//删除标识 0：可用  -1：禁用

    @NotNull(message = "页面分组不能为空")
    private Integer groupId;//分组id



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageKey() {
        return pageKey;
    }

    public void setPageKey(String pageKey) {
        this.pageKey = pageKey;
    }

    public String getPageGroup() {
        return pageGroup;
    }

    public void setPageGroup(String pageGroup) {
        this.pageGroup = pageGroup;
    }

    public String getPageExplanation() {
        return pageExplanation;
    }

    public void setPageExplanation(String pageExplanation) {
        this.pageExplanation = pageExplanation;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "id=" + id +
                ", pageTitle='" + pageTitle + '\'' +
                ", pageKey='" + pageKey + '\'' +
                ", pageGroup='" + pageGroup + '\'' +
                ", pageExplanation='" + pageExplanation + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", createTime=" + createTime +
                ", updatorId='" + updatorId + '\'' +
                ", updatorName='" + updatorName + '\'' +
                ", updateTime=" + updateTime +
                ", delFlag='" + delFlag + '\'' +
                ", groupId=" + groupId +
                '}';
    }
}
