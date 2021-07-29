package hillinsight.metadata.api.dto.web.req;

import hillinsight.metadata.api.web.MetaDataObjectInfo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @ClassName MetaDataStatusReq
 * @Description TODO
 * @Author wcy
 * @Date 2020/12/11
 * @Version 1.0
 */
public class MetaDataStatusReq {

    @Valid
    private MetaDataObjectInfo metaDataObjectInfo;

    public MetaDataObjectInfo getMetaDataObjectInfo() {
        return metaDataObjectInfo;
    }

    public void setMetaDataObjectInfo(MetaDataObjectInfo metaDataObjectInfo) {
        this.metaDataObjectInfo = metaDataObjectInfo;
    }
}
