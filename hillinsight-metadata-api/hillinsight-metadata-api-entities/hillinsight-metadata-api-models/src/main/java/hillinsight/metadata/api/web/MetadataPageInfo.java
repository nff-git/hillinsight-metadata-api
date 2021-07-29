package hillinsight.metadata.api.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import focus.mybatis.extensions.annotations.Id;
import hillinsight.metadata.api.models.BaseModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @ClassName MetadataPageInfo
 * @Description TODO 页面配置 页实体类
 * @Author wcy
 * @Date 2020/11/26
 * @Version 1.0
 */
public class MetadataPageInfo extends  BaseModel {

    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;

    private String pageKey;//pagekey

    @NotBlank(message = "页面标题不能为空")
    private String pageTitle;//页面标题

    private String pageExplain;//页面说明

    @NotNull(message = "分组id不能为空")
    private Integer groupId;//分组id

    @NotBlank(message = "系统来源id不能为空")
    private String sourceId;//系统来源id

    @NotBlank(message = "系统来源名称不能为空")
    private String sourceName;//系统来源名称

    private Integer status;//1使用 0删除

    private String groupName;//分组名称  非数据库字段

    private List<MessageGroup> msgGroupList;//信息分组信息 非数据库字段

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

    public String getPageKey() {
        return pageKey;
    }

    public void setPageKey(String pageKey) {
        this.pageKey = pageKey;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageExplain() {
        return pageExplain;
    }

    public void setPageExplain(String pageExplain) {
        this.pageExplain = pageExplain;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public Date getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
    }

    public List<MessageGroup> getMsgGroupList() {
        return msgGroupList;
    }

    public void setMsgGroupList(List<MessageGroup> msgGroupList) {
        this.msgGroupList = msgGroupList;
    }

    @Override
    public String toString() {
        return "MetadataPageInfo{" +
                "id=" + id +
                ", pageKey='" + pageKey + '\'' +
                ", pageTitle='" + pageTitle + '\'' +
                ", pageExplain='" + pageExplain + '\'' +
                ", groupId=" + groupId +
                ", sourceId='" + sourceId + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", status=" + status +
                ", groupName='" + groupName + '\'' +
                ", msgGroupList=" + msgGroupList +
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
