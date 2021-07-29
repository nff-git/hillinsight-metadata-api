package hillinsight.metadata.api.dto.web.req;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName BusiSysReq  业务系统请求实体
 * @Description TODO
 * @Author wcy
 * @Date 2021/1/7
 * @Version 1.0
 */
public class BusiSysReq {

    @NotBlank(message = "系统id不能为空！")
    private String sourceId;//系统id

    @NotBlank(message = "pageKey不能为空！")
    private String pageKey;//pageKey

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getPageKey() {
        return pageKey;
    }

    public void setPageKey(String pageKey) {
        this.pageKey = pageKey;
    }
}
