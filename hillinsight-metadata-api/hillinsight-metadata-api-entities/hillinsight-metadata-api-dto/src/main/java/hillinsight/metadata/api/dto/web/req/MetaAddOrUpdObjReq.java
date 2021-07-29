package hillinsight.metadata.api.dto.web.req;

import hillinsight.metadata.api.models.BaseModel;
import hillinsight.metadata.api.web.MetaDataObjectInfo;

import javax.validation.Valid;


/**
 * 新增或修改元数据对象请求实体
 *
 * @author wangchunyu
 * @date 2020/11/25
 */
public class MetaAddOrUpdObjReq {

    @Valid
    private MetaDataObjectInfo metaDataObjectInfo;

    public MetaDataObjectInfo getMetaDataObjectInfo() {
        return metaDataObjectInfo;
    }

    public void setMetaDataObjectInfo(MetaDataObjectInfo metaDataObjectInfo) {
        this.metaDataObjectInfo = metaDataObjectInfo;
    }
}
