package hillinsight.metadata.api.dto.web.req;

import javax.validation.constraints.NotNull;

/**
 * @ClassName PageTempThirdFieldListReq
 * @Description TODO 页面模板配置 获取业务字段 请求入参实体
 * @Author wcy
 * @Date 2020/12/8
 * @Version 1.0
 */
public class
PageTempThirdFieldListReq {

    @NotNull(message = "页面id不能为空！")
    private Integer pageId;//页面id

    //@NotNull(message = "业务对象id不能为空！")
    private Integer thirdObjId;//业务对象id

    @NotNull(message = "业务对象名称不能为空！")
    private String thirdObjName;//业务对象名称

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getThirdObjId() {
        return thirdObjId;
    }

    public void setThirdObjId(Integer thirdObjId) {
        this.thirdObjId = thirdObjId;
    }

    public String getThirdObjName() {
        return thirdObjName;
    }

    public void setThirdObjName(String thirdObjName) {
        this.thirdObjName = thirdObjName;
    }
}
