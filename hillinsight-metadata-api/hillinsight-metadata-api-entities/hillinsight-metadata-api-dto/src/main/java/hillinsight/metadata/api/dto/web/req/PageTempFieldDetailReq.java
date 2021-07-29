package hillinsight.metadata.api.dto.web.req;

import javax.validation.constraints.NotNull;

/**
 * @ClassName PageTempFieldDetailReq
 * @Description TODO
 * @Author wcy
 * @Date 2020/11/30
 * @Version 1.0
 */
public class PageTempFieldDetailReq {

    //@NotNull(message = "业务字段id不能为空")
    private Integer thirdFieldId;//业务字段主键id

    /**
     * 获取页面模板业务字段详情
     */
    private String sourceId;

    private String thirdFieldName;

    private String thirdObjName;

    public Integer getThirdFieldId() {
        return thirdFieldId;
    }

    public void setThirdFieldId(Integer thirdFieldId) {
        this.thirdFieldId = thirdFieldId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getThirdFieldName() {
        return thirdFieldName;
    }

    public void setThirdFieldName(String thirdFieldName) {
        this.thirdFieldName = thirdFieldName;
    }

    public String getThirdObjName() {
        return thirdObjName;
    }

    public void setThirdObjName(String thirdObjName) {
        this.thirdObjName = thirdObjName;
    }
}
