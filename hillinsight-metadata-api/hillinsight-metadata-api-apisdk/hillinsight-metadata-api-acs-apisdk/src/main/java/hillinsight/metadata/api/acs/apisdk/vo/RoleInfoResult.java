package hillinsight.metadata.api.acs.apisdk.vo;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 角色信息结果(acs系统中实体)
 *
 * @author wangchunyu
 * @date 2021/01/06
 */
public class RoleInfoResult implements Serializable {


    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色说明
     */
    private String description;

    /**
     * 角色类型
     *
     */
    private Integer roleType;
    private String roleTypeText;

    /**
     * 业务组表
     */
    private Long groupId;
    private String groupName;

    /**
     * 创建人
     */
    private String creatorCode;

    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    private String createTimeText;

    /**
     * 更新人
     */
    private String updatorCode;

    /**
     * 更新人名称
     */
    private String updatorName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    private String updateTimeText;

    public RoleInfoResult() {
    }

    public RoleInfoResult(Long id, String roleCode, String roleName, String description, Integer roleType, String roleTypeText, Long groupId, String groupName, String creatorCode, String creatorName, LocalDateTime createTime, String createTimeText, String updatorCode, String updatorName, LocalDateTime updateTime, String updateTimeText) {
        this.id = id;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.description = description;
        this.roleType = roleType;
        this.roleTypeText = roleTypeText;
        this.groupId = groupId;
        this.groupName = groupName;
        this.creatorCode = creatorCode;
        this.creatorName = creatorName;
        this.createTime = createTime;
        this.createTimeText = createTimeText;
        this.updatorCode = updatorCode;
        this.updatorName = updatorName;
        this.updateTime = updateTime;
        this.updateTimeText = updateTimeText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public String getRoleTypeText() {
        return roleTypeText;
    }

    public void setRoleTypeText(String roleTypeText) {
        this.roleTypeText = roleTypeText;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreatorCode() {
        return creatorCode;
    }

    public void setCreatorCode(String creatorCode) {
        this.creatorCode = creatorCode;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeText() {
        return createTimeText;
    }

    public void setCreateTimeText(String createTimeText) {
        this.createTimeText = createTimeText;
    }

    public String getUpdatorCode() {
        return updatorCode;
    }

    public void setUpdatorCode(String updatorCode) {
        this.updatorCode = updatorCode;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTimeText() {
        return updateTimeText;
    }

    public void setUpdateTimeText(String updateTimeText) {
        this.updateTimeText = updateTimeText;
    }

    @Override
    public String toString() {
        return "RoleInfoResult{" +
                "id=" + id +
                ", roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", roleType=" + roleType +
                ", roleTypeText='" + roleTypeText + '\'' +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", creatorCode='" + creatorCode + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", createTime=" + createTime +
                ", createTimeText='" + createTimeText + '\'' +
                ", updatorCode='" + updatorCode + '\'' +
                ", updatorName='" + updatorName + '\'' +
                ", updateTime=" + updateTime +
                ", updateTimeText='" + updateTimeText + '\'' +
                '}';
    }
}
