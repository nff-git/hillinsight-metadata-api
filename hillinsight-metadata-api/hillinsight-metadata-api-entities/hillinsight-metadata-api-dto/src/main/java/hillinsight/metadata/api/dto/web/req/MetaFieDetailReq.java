package hillinsight.metadata.api.dto.web.req;

import javax.validation.constraints.NotNull;

/**
 * 获取字段详情请求实体
 *
 * @author wangchunyu
 * @date 2020/11/25
 */
public class MetaFieDetailReq {

    @NotNull(message = "字段id不能为空！")
    private Integer fieldId;

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }
}
