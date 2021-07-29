package hillinsight.metadata.api.dto.web.req;

import focus.core.BaseCriteria;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName PageListReq
 * @Description TODO
 * @Author wcy
 * @Date 2020/11/30
 * @Version 1.0
 */
public class PageListReq  extends BaseCriteria implements Serializable {


    @NotNull(message = "分组id不能为空！")
    private Integer groupId;//分组id

    private String criteria;//条件 标题或者 pagekey

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
