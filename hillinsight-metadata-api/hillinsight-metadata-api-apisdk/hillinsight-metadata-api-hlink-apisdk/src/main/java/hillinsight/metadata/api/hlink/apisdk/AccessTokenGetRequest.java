package hillinsight.metadata.api.hlink.apisdk;

import focus.apiclient.core.HttpMethodEnum;
import focus.apiclient.core.annotation.RequestDto;
import hillinsight.metadata.api.utils.convention.StaticVar;

/**
 * 从 Hlink 获取 accesstoken 请求对象
 */
@RequestDto(url = "/cgi/token/get", method = HttpMethodEnum.Get, clientType = StaticVar.ApiClientKeys.HLINK_API_CLIENT)
public class AccessTokenGetRequest {
    private String app_key;
    private String app_secret;

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getApp_secret() {
        return app_secret;
    }

    public void setApp_secret(String app_secret) {
        this.app_secret = app_secret;
    }
}
