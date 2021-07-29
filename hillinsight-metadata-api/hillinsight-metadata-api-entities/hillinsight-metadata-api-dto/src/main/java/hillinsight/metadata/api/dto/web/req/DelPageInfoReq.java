package hillinsight.metadata.api.dto.web.req;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName delPageInfoReq
 * @Description TODO
 * @Author wcy
 * @Date 2020/11/30
 * @Version 1.0
 */
public class DelPageInfoReq {


    @NotNull(message = "页面id不能为空")
    private Integer pageId;

    private List<Integer> ids;//页面模板配置 删除业务字段时使用

    private String messageGroupKey;//信息分组key

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getMessageGroupKey() {
        return messageGroupKey;
    }

    public void setMessageGroupKey(String messageGroupKey) {
        this.messageGroupKey = messageGroupKey;
    }
}
