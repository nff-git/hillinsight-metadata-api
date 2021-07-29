package hillinsight.metadata.api.service.web;

import hillinsight.metadata.api.dto.web.BusiSysResultV2;
import hillinsight.metadata.api.dto.web.req.BusiSysReq;
import hillinsight.metadata.api.dto.web.BusiSysResult;

/**
 * @ClassName BusinessSystemWebService
 * @Description TODO
 * @Author wcy
 * @Date 2021/1/7
 * @Version 1.0
 */
public interface BusinessSystemWebService {

    BusiSysResult getBusinessSysPageConfig(BusiSysReq busiSysReq);

    BusiSysResultV2 getBusinessSysPageConfigV2(BusiSysReq busiSysReq);

    String deleteRedisCache(BusiSysReq busiSysReq);
}
