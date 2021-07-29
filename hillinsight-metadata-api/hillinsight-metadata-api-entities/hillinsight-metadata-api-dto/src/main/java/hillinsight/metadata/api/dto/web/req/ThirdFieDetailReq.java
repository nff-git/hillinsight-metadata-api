package hillinsight.metadata.api.dto.web.req;

import hillinsight.metadata.api.models.group.GroupInsert;

import javax.validation.MessageInterpolator;
import javax.validation.constraints.NotNull;

/**
 * 获取业务字段详情请求入参实体
 *
 * @author wangchunyu
 * @date 2020/11/25
 */
public class ThirdFieDetailReq {

    @NotNull(message = "业务字段id不能为空！")
    private Integer thirdFieldId;

    public Integer getThirdFieldId() {
        return thirdFieldId;
    }

    public void setThirdFieldId(Integer thirdFieldId) {
        this.thirdFieldId = thirdFieldId;
    }

}
