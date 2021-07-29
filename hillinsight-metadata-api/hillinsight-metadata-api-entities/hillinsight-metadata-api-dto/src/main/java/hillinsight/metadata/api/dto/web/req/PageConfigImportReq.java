package hillinsight.metadata.api.dto.web.req;

import hillinsight.metadata.api.web.MetadataPageInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName PageConfigImportReq
 * @Description TODO
 * @Author wcy
 * @Date 2020/12/3
 * @Version 1.0
 */

public class PageConfigImportReq {

    private List<MetadataPageInfo> metadataPageInfos;

    @NotNull(message = "分组id不能为空！")
    private Integer groupId;

    @NotBlank(message = "系统来源id不能为空")
    private String sourceId;

    @NotBlank(message = "系统来源名称不能为空")
    private String sourceName;

    public PageConfigImportReq() {
    }

    public PageConfigImportReq(List<MetadataPageInfo> metadataPageInfos, @NotNull(message = "分组id不能为空！") Integer groupId, @NotBlank(message = "系统来源id不能为空") String sourceId, @NotBlank(message = "系统来源名称不能为空") String sourceName) {
        this.metadataPageInfos = metadataPageInfos;
        this.groupId = groupId;
        this.sourceId = sourceId;
        this.sourceName = sourceName;
    }

    public List<MetadataPageInfo> getMetadataPageInfos() {
        return metadataPageInfos;
    }

    public void setMetadataPageInfos(List<MetadataPageInfo> metadataPageInfos) {
        this.metadataPageInfos = metadataPageInfos;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

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
