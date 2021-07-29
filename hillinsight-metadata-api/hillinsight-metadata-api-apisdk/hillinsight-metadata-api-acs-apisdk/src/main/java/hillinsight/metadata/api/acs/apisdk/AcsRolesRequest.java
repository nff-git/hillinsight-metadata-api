package hillinsight.metadata.api.acs.apisdk;

import focus.apiclient.core.HttpMethodEnum;
import focus.apiclient.core.annotation.RequestDto;
import hillinsight.metadata.api.acs.apisdk.vo.WebRoleInfoQueryVo;
import hillinsight.metadata.api.utils.convention.StaticVar;

/**
 * @ClassName  权限系统获取用户角色列表请求
 * @Description TODO
 * @Author wcy
 * @Date 2020/12/18
 * @Version 1.0
 */

@RequestDto(url = "/api/WebAccess/listRoleByUserCode", method = HttpMethodEnum.Post, clientType = StaticVar.ApiClientKeys.ACS_API_CLIENT, serviceName = StaticVar.ServiceNames.ACS_SERVICE_NAME)
public class AcsRolesRequest {

    private WebRoleInfoQueryVo queryVo;

    public WebRoleInfoQueryVo getQueryVo() {
        return queryVo;
    }

    public void setQueryVo(WebRoleInfoQueryVo queryVo) {
        this.queryVo = queryVo;
    }
}
