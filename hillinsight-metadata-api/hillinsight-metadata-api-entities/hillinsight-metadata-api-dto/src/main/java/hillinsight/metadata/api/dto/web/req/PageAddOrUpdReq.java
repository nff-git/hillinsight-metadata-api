package hillinsight.metadata.api.dto.web.req;

import hillinsight.metadata.api.models.BaseModel;
import hillinsight.metadata.api.web.MetadataPageInfo;

import javax.validation.Valid;

/**
 * @ClassName PageAddOrUpdReq
 * @Description TODO 新增修改配置页请求入参实体
 * @Author wcy
 * @Date 2020/11/26
 * @Version 1.0
 */
public class PageAddOrUpdReq  extends BaseModel {

    @Valid
    private MetadataPageInfo metadataPageInfo;

    public MetadataPageInfo getMetadataPageInfo() {
        return metadataPageInfo;
    }

    public void setMetadataPageInfo(MetadataPageInfo metadataPageInfo) {
        this.metadataPageInfo = metadataPageInfo;
    }
}
