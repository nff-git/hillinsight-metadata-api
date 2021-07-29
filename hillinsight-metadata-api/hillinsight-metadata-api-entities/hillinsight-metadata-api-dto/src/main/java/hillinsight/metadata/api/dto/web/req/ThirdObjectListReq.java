package hillinsight.metadata.api.dto.web.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 获取业务对象列表请求入参实体
 *
 * @author wangchunyu
 * @date 2020/11/25
 */
public class ThirdObjectListReq {

    private String thirdObjectName;//TODO 暂时不用试

    private Integer thirdObjectId;//TODO 暂时不用试


    @NotBlank(message = "系统来源不能为空！")
    private String sourceId;//系统来源

    private String criteria;//查询条件


    public String getThirdObjectName() {
        return thirdObjectName;
    }

    public void setThirdObjectName(String thirdObjectName) {
        this.thirdObjectName = thirdObjectName;
    }

    public Integer getThirdObjectId() {
        return thirdObjectId;
    }

    public void setThirdObjectId(Integer thirdObjectId) {
        this.thirdObjectId = thirdObjectId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}

