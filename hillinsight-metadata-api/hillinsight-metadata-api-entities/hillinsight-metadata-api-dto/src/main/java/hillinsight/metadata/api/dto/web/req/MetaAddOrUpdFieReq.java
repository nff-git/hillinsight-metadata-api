package hillinsight.metadata.api.dto.web.req;

import hillinsight.metadata.api.web.MetaDataFieldInfo;
import hillinsight.metadata.api.web.MetaDataObjectInfo;

import javax.validation.Valid;


/**
 * 新增或修改元数据字段请求实体
 *
 * @author wangchunyu
 * @date 2020/11/25
 */
public class MetaAddOrUpdFieReq {


    @Valid
    private MetaDataFieldInfo metaDataFieldInfo;

    public MetaDataFieldInfo getMetaDataFieldInfo() {
        return metaDataFieldInfo;
    }

    public void setMetaDataFieldInfo(MetaDataFieldInfo metaDataFieldInfo) {
        this.metaDataFieldInfo = metaDataFieldInfo;
    }
}
