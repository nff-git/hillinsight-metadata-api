package hillinsight.metadata.api.dto.web.req;

import focus.core.BaseCriteria;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 获取字段列表请求入参实体
 *
 * @author wangchunyu
 * @date 2020/11/25
 */
public class ThirdFieListByObjIdReq extends BaseCriteria implements Serializable {

    @NotNull(message = "业务对象id不能为空！")
    private  Integer thirdObjectId;

    private String criteria;

    public Integer getThirdObjectId() {
        return thirdObjectId;
    }

    public void setThirdObjectId(Integer thirdObjectId) {
        this.thirdObjectId = thirdObjectId;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
