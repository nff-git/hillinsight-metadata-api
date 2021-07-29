package hillinsight.metadata.api.dto.web;

/**
 * @ClassName PageGroupSysResult
 * @Description TODO 页面分组系统 返回实体
 * @Author wcy
 * @Date 2020/12/9
 * @Version 1.0
 */
public class PageGroupSysResult {

    private  String sourceId;

    private  String sourceName;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
