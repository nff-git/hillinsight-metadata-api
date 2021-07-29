package hillinsight.metadata.api.dto.web.req;

import hillinsight.metadata.api.models.BaseModel;
import hillinsight.metadata.api.web.MetaDataGroupInfo;

import javax.validation.Valid;

/**
 * @ClassName GroupAddOrUpdReq
 * @Description TODO 新增修改分组请求入参实体
 * @Author wcy
 * @Date 2020/11/26
 * @Version 1.0
 */
public class GroupAddOrUpdReq extends BaseModel {

    @Valid
    private MetaDataGroupInfo metaDataGroupInfo;

    public MetaDataGroupInfo getMetaDataGroupInfo() {
        return metaDataGroupInfo;
    }

    public void setMetaDataGroupInfo(MetaDataGroupInfo metaDataGroupInfo) {
        this.metaDataGroupInfo = metaDataGroupInfo;
    }


}
