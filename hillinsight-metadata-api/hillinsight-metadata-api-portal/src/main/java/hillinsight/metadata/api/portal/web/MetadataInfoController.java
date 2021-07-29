package hillinsight.metadata.api.portal.web;

import focus.core.PagedResult;
import focus.core.ResponseResult;
import hillinsight.acs.api.sdk.ApiAcsPermissionResult;
import hillinsight.acs.api.sdk.PermissionUtil;
import hillinsight.acs.api.sdk.UserInfoVo;
import hillinsight.metadata.api.dto.web.*;
import hillinsight.metadata.api.dto.web.req.*;
import hillinsight.metadata.api.service.web.MetaDataInfoService;
import hillinsight.metadata.api.web.MetaDataFieldInfo;
import hillinsight.metadata.api.web.MetaDataObjectInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * MetadataInfoController 元数据信息的控制器
 *
 * @author wangchunyu
 * @date 2020/12/09
 */
@RestController
@RequestMapping(path = "/api/objectInfo")
@Slf4j
public class MetadataInfoController {

    @Autowired
    private MetaDataInfoService metaDataInfoService;


    /**
     * 添加或更新元数据对象
     *
     * @param metaAddOrUpdObjReq 元数据对象入参
     * @return {@link ResponseResult}
     */
    @RequestMapping(path = "/addOrUpdateObject", method = RequestMethod.POST)
    public ResponseResult addOrUpdateObject(@RequestBody MetaAddOrUpdObjReq metaAddOrUpdObjReq, HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        log.error("权限用户信息Error："+userInfoVo.getUserCode()+":"+userInfoVo.getUserName()+"*******************");
        metaAddOrUpdObjReq.getMetaDataObjectInfo().initializedUserInfo(userInfoVo.getUserCode(),userInfoVo.getUserName());
        return ResponseResult.success(metaDataInfoService.addOrUpdateObjectInfo(metaAddOrUpdObjReq));
    }


    /**
     * 添加或更新元数据字段
     *
     * @param metaAddOrUpdFieReq 元数据字段入参
     * @return {@link ResponseResult}
     */
    @RequestMapping(path = "/addOrUpdateField", method = RequestMethod.POST)
    public ResponseResult addOrUpdateField(@RequestBody MetaAddOrUpdFieReq metaAddOrUpdFieReq, HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        metaAddOrUpdFieReq.getMetaDataFieldInfo().initializedUserInfo(userInfoVo.getUserCode(),userInfoVo.getUserName());
        return ResponseResult.success(metaDataInfoService.addOrUpdateFieldInfo(metaAddOrUpdFieReq));
    }


    /**
     * 查询元数据对象列表(支持模糊查询)
     *
     * @param metaObjListFuzzyReq 请求对象名入参
     * @return {@link ResponseResult<List<MetaDataObjectInfo>>}
     */
    @RequestMapping(path = "/getObjectListByobjName", method = RequestMethod.POST)
    public ResponseResult<List<MetaDataObjectInfo>> getObjectListByName(@RequestBody MetaObjListFuzzyReq metaObjListFuzzyReq) {
        return ResponseResult.success(metaDataInfoService.getObjectList(metaObjListFuzzyReq));
    }


    /**
     * 查询元数据字段列表（分页）
     *
     * @param metaFieListByObjIdReq 获取字段列表请求参入
     * @return 返回元数据字段列表
     */
    @RequestMapping(path = "/getFieldListByObjectId", method = RequestMethod.POST)
    public ResponseResult<PagedResult<MetaDataFieldInfo>> getFieldListByObjectId(@RequestBody MetaFieListByObjIdReq metaFieListByObjIdReq,
                                                                                 HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        metaFieListByObjIdReq.setRoles(apiAcsPermissionResult.getUserInfoVo().getRoleInfos());
        //打印用户登录角色列表
        log.info("登录用户角色列表：「"+apiAcsPermissionResult.getUserInfoVo().getRoleInfos()+"」******************");
        return ResponseResult.success(metaDataInfoService.getFieldListByObjectId(metaFieListByObjIdReq));
    }

    /**
     * 查询字段详情
     *
     * @param metaFieDetailReq 获取字段详情请求入参
     * @return 返回元数据字段详情
     */
    @RequestMapping(path = "/getFieldetailById", method = RequestMethod.POST)
    public ResponseResult<MetaDataFieldInfo> getFieldetailById(@RequestBody(required = false) MetaFieDetailReq metaFieDetailReq) {

        return ResponseResult.success(metaDataInfoService.getFieldetailById(metaFieDetailReq));
    }

    /**
     * 字段启用/停用
     *
     * @param metaFieldBlockUpReq 设置字段状态请求入参
     * @return 返回元数据字段详情
     */
    @RequestMapping(path = "/fieldUseOrBlockUp", method = RequestMethod.POST)
    public ResponseResult<String> fieldBlockUp(@RequestBody MetaFieldBlockUpReq metaFieldBlockUpReq, HttpServletRequest request) {
        //TODO 上到开发环境时需要释放
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        metaFieldBlockUpReq.initializedUserInfo(userInfoVo.getUserCode(),userInfoVo.getUserName());
        return ResponseResult.success(metaDataInfoService.fieldBlockUp(metaFieldBlockUpReq));
    }


    /**
     * 获取元数据父对象列表（新增元数据对象 选择父对象时使用）
     *
     * @return {@link ResponseResult<MetaDataFieldInfo>}
     */
    @RequestMapping(path = "/getMetaParentList", method = RequestMethod.POST)
    public ResponseResult<List<MetaDataObjectInfo>> getMetaParentList() {
        return ResponseResult.success(metaDataInfoService.getMetaParentList());
    }



    /**
     * 查询元数据字段列表（业务字段绑定元数据字段时使用）
     * //TODO 业务系统使用接口
     * @return 返回元数据字段详情
     */
    @RequestMapping(path = "/getMetaFieldListForThirdFie", method = RequestMethod.POST)
    public ResponseResult<List<MetaFieldListResult>> getMetaFieldList(@RequestBody MetaObjListFuzzyReq metaObjListFuzzyReq) {
        return ResponseResult.success(metaDataInfoService.getMetaFieldList(metaObjListFuzzyReq));
    }


    /**
     * 获取元数据对象列表 （业务对象新增时绑定元数据对象使用）
     *
     * //TODO 业务系统使用接口
     * @return {@link ResponseResult<List<MetaFieldListResult>>}
     */
    @RequestMapping(path = "/getMetaObjListForThirdObj", method = RequestMethod.POST)
    public ResponseResult<List<MetaDataObjectInfo>> getMetaObjListForThirdObj(@RequestBody MetaObjListFuzzyReq metaObjListFuzzyReq) {
        return ResponseResult.success(metaDataInfoService.getMetaObjListForThirdObj(metaObjListFuzzyReq));
    }


    /**
     * 对象使用或停用
     *
     * @param metaDataStatusReq 元数据状态要求
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/ObjectUseOrBlockUp", method = RequestMethod.POST)
    public ResponseResult<String> ObjectUseOrBlockUp(@RequestBody MetaDataStatusReq metaDataStatusReq, HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        metaDataStatusReq.getMetaDataObjectInfo().initializedUserInfo(userInfoVo.getUserCode(),userInfoVo.getUserName());
        return ResponseResult.success(metaDataInfoService.ObjectUseOrBlockUp(metaDataStatusReq));
    }


    /**
     * 获取元数据对象列表 （业务对象新增时绑定元数据   "字段"  时使用）
     *
     * //TODO 业务系统使用接口
     * @return {@link ResponseResult<List<MetaFieldListResult>>}
     */
    @RequestMapping(path = "/getMetaObjListForThirdField", method = RequestMethod.POST)
    public ResponseResult<List<MetaDataObjectInfo>> getMetaObjListForThirdField(@RequestBody MetaObjListFuzzyReq metaObjListFuzzyReq) {
        return ResponseResult.success(metaDataInfoService.getMetaObjListForThirdField(metaObjListFuzzyReq));
    }


    /**
     * 删除的id字段
     *
     * @param metaFieDetailReq 元5细节要求
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/deleteFieldById", method = RequestMethod.POST)
    public ResponseResult<String> deleteFieldById(@RequestBody MetaFieDetailReq metaFieDetailReq) {
        return ResponseResult.success(metaDataInfoService.deleteFieldById(metaFieDetailReq));
    }


}
