package hillinsight.metadata.api.dto.web.req;

import focus.core.BaseCriteria;
import hillinsight.acs.api.sdk.RoleInfoResult;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.List;

/**
 * 获取字段列表请求参入
 *
 * @author wangchunyu
 * @date 2020/11/25
 */
public class MetaFieListByObjIdReq  extends BaseCriteria implements Serializable {


    @NotNull(message = "对象id不能为空！")
    private Integer objectId;

    private String criteria;// 模糊查询条件  字段名 字段显示名cn  字段显示名en

    private List<RoleInfoResult> roles;//登录用户所拥有的所有角色

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getCriteria() {

        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public List<RoleInfoResult> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleInfoResult> roles) {
        this.roles = roles;
    }
}

