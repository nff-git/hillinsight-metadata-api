package hillinsight.metadata.api.starter.requests;

import focus.apiclient.core.HttpMethodEnum;
import focus.apiclient.core.annotation.RequestDto;
import hillinsight.metadata.api.starter.StaticVar;

@RequestDto(url = "/", method = HttpMethodEnum.Post, clientType = StaticVar.ApiClient.CLIENT_TYPE, serviceName = StaticVar.ApiClient.SERVICE_NAME)
public class MetaDataInfoRequest {
    private String metaDataObjectName;
    private String metaDataPropertyName;

    public String getMetaDataObjectName() {
        return metaDataObjectName;
    }

    public void setMetaDataObjectName(String metaDataObjectName) {
        this.metaDataObjectName = metaDataObjectName;
    }

    public String getMetaDataPropertyName() {
        return metaDataPropertyName;
    }

    public void setMetaDataPropertyName(String metaDataPropertyName) {
        this.metaDataPropertyName = metaDataPropertyName;
    }
}
