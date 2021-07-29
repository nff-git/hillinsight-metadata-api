package hillinsight.metadata.api.portal.demo;

import focus.apiclient.core.ApiInvoker;
import focus.core.ResponseResult;
import focus.core.TypeReference;
import hillinsight.metadata.api.acs.apisdk.AcsRolesRequest;
import hillinsight.metadata.api.acs.apisdk.vo.RoleInfoResult;
import hillinsight.metadata.api.acs.apisdk.vo.WebRoleInfoQueryVo;
import hillinsight.metadata.api.utils.convention.StaticVar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class AcsApiController {

    @Resource(name = StaticVar.ApiClientBeans.NORMAL_REST_TEMPLATE)
    private RestTemplate restTemplate;

    /**
     * 测试方法仅供参考调用 acs 的接口
     * @return
     */
    @RequestMapping(path = "/testACSApi", method = RequestMethod.GET)
    public ResponseResult<List<RoleInfoResult>> testACSApi() {
        AcsRolesRequest acsRolesRequest = new AcsRolesRequest();
        WebRoleInfoQueryVo  webRoleInfoQueryVo= new WebRoleInfoQueryVo();
        webRoleInfoQueryVo.setUserCode("USER2020112400000041");
        acsRolesRequest.setQueryVo(webRoleInfoQueryVo);
        ResponseResult<List<RoleInfoResult>> roleInfoResultAcsRolesResponse = ApiInvoker.invokeService(acsRolesRequest, new TypeReference<ResponseResult<List<RoleInfoResult>>>() {
        });
        log.error("acs返回结果信息："+roleInfoResultAcsRolesResponse+"*********************************");
        List<RoleInfoResult> result = roleInfoResultAcsRolesResponse.getResult();
        for (RoleInfoResult roleInfoResult : result) {
            log.error("角色列表："+roleInfoResult.getRoleCode()+":"+roleInfoResult.getRoleName()+"********************************");
        }
        return ResponseResult.success(result);
    }
}
