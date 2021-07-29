package hillinsight.metadata.api.portal.web;

import focus.core.PagedResult;
import focus.core.ResponseResult;
import hillinsight.acs.api.sdk.ApiAcsPermissionResult;
import hillinsight.acs.api.sdk.PermissionUtil;
import hillinsight.acs.api.sdk.UserInfoVo;
import hillinsight.metadata.api.dto.web.*;
import hillinsight.metadata.api.dto.web.req.*;
import hillinsight.metadata.api.service.web.ThirdInfoService;
import hillinsight.metadata.api.web.ThirdFieldInfo;
import hillinsight.metadata.api.web.ThirdObjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * ThirdInfoController 业务对象控制层
 *
 * @author wangchunyu
 * @date 2020/12/09
 */
@RestController
@RequestMapping(path = "/api/thirdInfo")
public class ThirdInfoController {

    @Autowired
    private ThirdInfoService thirdInfoService;


    /**
     * 添加或更新业务对象
     *
     * @param thirdAddOrUpdObjReq 请求添加或修改业务对象入参
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/addOrUpdateThirdObj", method = RequestMethod.POST)
    public ResponseResult<String> addOrUpdateObject(@RequestBody ThirdAddOrUpdObjReq thirdAddOrUpdObjReq, HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        thirdAddOrUpdObjReq.getThirdObjectInfo().initializedUserInfo(userInfoVo.getUserCode(), userInfoVo.getUserName());
        return ResponseResult.success(thirdInfoService.addOrUpdateThirdObjInfo(thirdAddOrUpdObjReq));
    }


    /**
     * 添加或更新业务字段
     *
     * @param thirdAddOrUpdFieReq 请求添加或修改业务字段入参
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/addOrUpdateThirdField", method = RequestMethod.POST)
    public ResponseResult<String> addOrUpdateThirdField(@RequestBody ThirdAddOrUpdFieReq thirdAddOrUpdFieReq, HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        thirdAddOrUpdFieReq.getThirdFieldInfo().initializedUserInfo(userInfoVo.getUserCode(), userInfoVo.getUserName());
        return ResponseResult.success(thirdInfoService.addOrUpdateThirdField(thirdAddOrUpdFieReq));
    }

    /**
     * 根据业务对象id查询字段列表（分页/支持模糊查询）
     *
     * @param thirdFieListByObjIdReq 获取字段列表请求入参
     * @return 返回字段列表信息
     */
    @RequestMapping(path = "/getFieldListByObjId", method = RequestMethod.POST)
    public ResponseResult<PagedResult<ThirdFieldInfo>> getFieldListByObjId(@RequestBody ThirdFieListByObjIdReq thirdFieListByObjIdReq) {
        return ResponseResult.success(thirdInfoService.getFieldListByObjId(thirdFieListByObjIdReq));
    }

    /**
     * 查询业务字段详情
     *
     * @param thirdFieDetailReq 获取业务字段详情请求入参
     * @return 返回字段信息
     */
    @RequestMapping(path = "/getFieldDetailById", method = RequestMethod.POST)
    public ResponseResult<ThirdFieldDetailResult> getFieldDetailById(@RequestBody ThirdFieDetailReq thirdFieDetailReq) {
        return ResponseResult.success(thirdInfoService.getFieldDetailById(thirdFieDetailReq));
    }

    /**
     * 业务字段启用/停用
     *
     * @param thirdFieldBlockUpReq 业务字段启停请求入参
     * @return 返回结果信息
     */
    @RequestMapping(path = "/ThirdfieldUseOrBlockUp", method = RequestMethod.POST)
    public ResponseResult<String> ThirdfieldBlockUp(@RequestBody ThirdFieldBlockUpReq thirdFieldBlockUpReq, HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        thirdFieldBlockUpReq.initializedUserInfo(userInfoVo.getUserCode(), userInfoVo.getUserName());
        return ResponseResult.success(thirdInfoService.thirdfieldBlockUp(thirdFieldBlockUpReq));
    }


    /**
     * 查询可用的自定义字段列表
     *
     * @param thirdFieListByObjIdReq 获取可用的自定义字段列表请求入参
     * @return 返回结果信息
     */
    @RequestMapping(path = "/getExtensionListByObjId", method = RequestMethod.POST)
    public ResponseResult<List<String>> getExtensionListByObjId(@RequestBody ThirdFieListByObjIdReq thirdFieListByObjIdReq) {

        return ResponseResult.success(thirdInfoService.getExtensionListByObjId(thirdFieListByObjIdReq));
    }

    /**
     * 获取业务对象列表(支持模糊查询)
     *
     * @param thirdObjectListReq sourceid
     * @return
     */
    @RequestMapping(path = "/getThirdObjectList", method = RequestMethod.POST)
    public ResponseResult<List<ThirdObjectInfo>> getThirdObjectList(@RequestBody ThirdObjectListReq thirdObjectListReq) {
        return ResponseResult.success(thirdInfoService.getThirdObjectList(thirdObjectListReq));
    }


    /**
     * 获取业务系统父对象列表（新增业务对象时 绑定父对象使用）
     *
     * @return {@link ResponseResult<List<ThirdObjectInfo>>}
     */
    @RequestMapping(path = "/getThirdParentObjList", method = RequestMethod.POST)
    public ResponseResult<List<ThirdObjectInfo>> getThirdParentObjList(@RequestBody ThirdObjectListReq thirdObjectListReq) {
        return ResponseResult.success(thirdInfoService.getThirdParentObjList(thirdObjectListReq));
    }


    /**
     * 业务对象启用/停用
     *
     * @param thirdObjBlockUpReq 业务对象启停请求入参
     * @return 返回结果信息
     */
    @RequestMapping(path = "/ThirdObjUseOrBlockUp", method = RequestMethod.POST)
    public ResponseResult<String> ThirdObjUseOrBlockUp(@RequestBody ThirdObjBlockUpReq thirdObjBlockUpReq, HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        thirdObjBlockUpReq.getThirdObjectInfo().initializedUserInfo(userInfoVo.getUserCode(), userInfoVo.getUserName());
        return ResponseResult.success(thirdInfoService.ThirdObjUseOrBlockUp(thirdObjBlockUpReq));
    }


    /**
     * 删除字段
     *
     * @param thirdFieDetailReq thirdFieDetailReq
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/deleteFieldById", method = RequestMethod.POST)
    public ResponseResult<String> deleteFieldById(@RequestBody ThirdFieDetailReq thirdFieDetailReq) {
        return ResponseResult.success(thirdInfoService.deleteFieldById(thirdFieDetailReq));
    }


}
