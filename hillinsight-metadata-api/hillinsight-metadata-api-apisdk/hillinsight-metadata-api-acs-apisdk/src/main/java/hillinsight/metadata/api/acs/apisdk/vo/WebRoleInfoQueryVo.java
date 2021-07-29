package hillinsight.metadata.api.acs.apisdk.vo;


import java.io.Serializable;

/**
 * 用户角色表
 *
 * @author xxiang
 * @since 2021-01-16
 */

public class WebRoleInfoQueryVo implements Serializable {

    /**
     * 用户表CODE
     */
    private String userCode;

    /**
     * 角色表CODE
     */
    private String roleCode;

    public String getUserCode() {
        return userCode;
    }

    public WebRoleInfoQueryVo setUserCode(String userCode) {
        this.userCode = userCode;
        return this;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public WebRoleInfoQueryVo setRoleCode(String roleCode) {
        this.roleCode = roleCode;
        return this;
    }
}
