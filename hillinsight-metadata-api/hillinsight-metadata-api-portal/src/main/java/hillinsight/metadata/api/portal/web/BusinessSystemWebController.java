package hillinsight.metadata.api.portal.web;

import focus.core.ResponseResult;
import hillinsight.metadata.api.dto.web.BusiSysResultV2;
import hillinsight.metadata.api.dto.web.req.BusiSysReq;
import hillinsight.metadata.api.dto.web.BusiSysResult;
import hillinsight.metadata.api.service.web.BusinessSystemWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BusinessSystemWebController 业务系统 api
 * @Description TODO
 * @Author wcy
 * @Date 2021/1/7
 * @Version 1.0
 */

@RestController
@RequestMapping(path = "/api/busiSys")
@Slf4j
public class BusinessSystemWebController {

    @Autowired
    private BusinessSystemWebService businessSystemWebService;


    /**
     * //TODO 元数据2.0已作废
     * 获取业务系统页面配置信息
     *
     * @param busiSysReq 入参
     * @return {@link ResponseResult<BusiSysResult>}
     */
    @RequestMapping(path = "/getBusinessSysPageConfig", method = RequestMethod.POST)
    public ResponseResult<BusiSysResult> getBusinessSysPageConfig(@RequestBody BusiSysReq busiSysReq) {
        return ResponseResult.success(businessSystemWebService.getBusinessSysPageConfig(busiSysReq));
    }


    /**
     * 获取业务系统页面配置信息 (元数据2.0使用)
     *
     * @param busiSysReq 入参
     * @return {@link ResponseResult<BusiSysResult>}
     */
    @RequestMapping(path = "/getBusinessSysPageConfig/v2", method = RequestMethod.POST)
    public ResponseResult<BusiSysResultV2> getBusinessSysPageConfigV2(@RequestBody BusiSysReq busiSysReq) {
        return ResponseResult.success(businessSystemWebService.getBusinessSysPageConfigV2(busiSysReq));
    }

    /**
     * 删除缓存
     *
     * @param busiSysReq 商业系统要求
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/deleteRedisCache/v2", method = RequestMethod.POST)
    public ResponseResult<String> deleteRedisCache(@RequestBody BusiSysReq busiSysReq) {
        return ResponseResult.success(businessSystemWebService.deleteRedisCache(busiSysReq));
    }









}
