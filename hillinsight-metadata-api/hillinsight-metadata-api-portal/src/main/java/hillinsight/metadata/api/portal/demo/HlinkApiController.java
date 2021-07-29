package hillinsight.metadata.api.portal.demo;

import focus.apiclient.core.ApiInvoker;
import focus.core.ResponseResult;
import focus.core.TypeReference;
import hillinsight.metadata.api.hlink.apisdk.AccessTokenGetRequest;
import hillinsight.metadata.api.hlink.apisdk.HlinkApiResponse;
import hillinsight.metadata.api.utils.convention.StaticVar;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class HlinkApiController {

    @Resource(name = StaticVar.ApiClientBeans.NORMAL_REST_TEMPLATE)
    private RestTemplate restTemplate;

    /**
     * 测试方法仅供参考调用 hlink 的接口
     * @return
     */
    @RequestMapping(path = "/testHlinkApi", method = RequestMethod.GET)
    public ResponseResult<String> testHlinkApi() {
        AccessTokenGetRequest request = new AccessTokenGetRequest();
        request.setApp_key("12312");
        request.setApp_secret("2312313");
        HlinkApiResponse<Object> json = ApiInvoker.invokeService(request, new TypeReference<HlinkApiResponse<Object>>() {
        });
        return ResponseResult.success(json.getMessage());
    }
}
