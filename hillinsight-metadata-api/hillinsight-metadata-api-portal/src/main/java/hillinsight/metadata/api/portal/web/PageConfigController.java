package hillinsight.metadata.api.portal.web;

import com.alibaba.fastjson.JSONObject;
import focus.core.PagedResult;
import focus.core.ResponseResult;

import hillinsight.acs.api.sdk.ApiAcsPermissionResult;
import hillinsight.acs.api.sdk.PermissionUtil;
import hillinsight.acs.api.sdk.UserInfoVo;
import hillinsight.metadata.api.dto.web.*;
import hillinsight.metadata.api.dto.web.req.*;
import hillinsight.metadata.api.service.web.PageConfigService;
import hillinsight.metadata.api.web.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * PageConfigController 页面配置控制器
 *
 * @author wangchunyu
 * @date 2020/12/09
 */
@RestController
@RequestMapping(path = "/api/pageConfig")
public class PageConfigController {

    @Autowired
    private PageConfigService pageConfigService;


    /**
     * 添加或更新分组
     *
     * @param groupAddOrUpdReq 新增修改分组请求入参
     * @return {@link ResponseResult}
     */
    @RequestMapping(path = "/addOrUpdateGroup", method = RequestMethod.POST)
    public ResponseResult<String> addOrUpdateGroup(@RequestBody GroupAddOrUpdReq groupAddOrUpdReq, HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        groupAddOrUpdReq.getMetaDataGroupInfo().initializedUserInfo(userInfoVo.getUserCode(), userInfoVo.getUserName());
        return ResponseResult.success(pageConfigService.addOrUpdateGroup(groupAddOrUpdReq));
    }


    /**
     * 根据条件获取分组列表
     *
     * @param groupListRep 获取分组集合请求入参
     * @return {@link ResponseResult}
     */
    @RequestMapping(path = "/getGroupListByCriteria", method = RequestMethod.POST)
    public ResponseResult<List<MetaDataGroupInfo>> getGroupListByCriteria(@RequestBody GroupListRep groupListRep, HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        groupListRep.setUserCode(userInfoVo.getUserCode());
        return ResponseResult.success(pageConfigService.getGroupListByCriteria(groupListRep));
    }


    /**
     * 添加或更新页面
     *
     * @param pageAddOrUpdReq 页面信息请求
     * @param request         请求
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/addOrUpdatePage", method = RequestMethod.POST)
    public ResponseResult<String> addOrUpdatePage(@RequestBody PageAddOrUpdReq pageAddOrUpdReq, HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        pageAddOrUpdReq.getMetadataPageInfo().initializedUserInfo(userInfoVo.getUserCode(), userInfoVo.getUserName());
        return ResponseResult.success(pageConfigService.addOrUpdatePage(pageAddOrUpdReq));
    }


    /**
     * 根据条件获取页面列表(分页)
     *
     * @param pageListReq 页面列表请求
     * @return {@link ResponseResult<List<MetaDataGroupInfo>>}
     */
    @RequestMapping(path = "/getPageListByCriteria", method = RequestMethod.POST)
    public ResponseResult<PagedResult<MetadataPageInfo>> getPageListByCriteria(@RequestBody PageListReq pageListReq) {
        return ResponseResult.success(pageConfigService.getPageListByCriteria(pageListReq));
    }


    /**
     * 删除页面
     *
     * @param delPageInfoReq 页面删除入参
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/delPage", method = RequestMethod.POST)
    public ResponseResult<String> delPage(@RequestBody DelPageInfoReq delPageInfoReq) {
        return ResponseResult.success(pageConfigService.delPage(delPageInfoReq));
    }


    /**
     * 添加页面模板信息
     *
     * @param pageTemplateAddReq 页面模板
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/addPageTemplate", method = RequestMethod.POST)
    public ResponseResult<String> addPageTemplateInfo(@RequestBody PageTemplateAddReq pageTemplateAddReq, HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        for (PageTemplate pageTemplate : pageTemplateAddReq.getPageTemplateList()) {
            pageTemplate.initializedUserInfo(userInfoVo.getUserCode(), userInfoVo.getUserName());
        }
        return ResponseResult.success(pageConfigService.addPageTemplateInfo(pageTemplateAddReq));
    }


    /**
     * 更新页面模板自定义字段释义信息
     *
     * @param pageTemplateAddReq 页面模板
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/updCustomFieldShow", method = RequestMethod.POST)
    public ResponseResult<String> updCustomFieldShow(@RequestBody PageTemplateAddReq pageTemplateAddReq, HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        pageTemplateAddReq.getPageTemplate().initializedUserInfo(userInfoVo.getUserCode(), userInfoVo.getUserName());
        return ResponseResult.success(pageConfigService.updCustomFieldShow(pageTemplateAddReq));
    }


    /**
     * 获取页面模板字段详情
     *
     * @param pageTemplateAddReq 页面模板添加请求
     * @return {@link ResponseResult<PageTemplateFieldDelResult>}
     */
    @RequestMapping(path = "/pageTempFieldDetail", method = RequestMethod.POST)
    public ResponseResult<PageTemplateFieldDelResult> getPageTempFieldDetail(@RequestBody PageTempFieldDetailReq pageTemplateAddReq) {
        return ResponseResult.success(pageConfigService.getPageTempFieldDetail(pageTemplateAddReq));
    }


    /**
     * 获取模板配置信息
     *
     * @param delPageInfoReq 页面id
     * @return {@link ResponseResult<List<TemplateConfigInfoResult>>}
     */
    @RequestMapping(path = "/getTemplateConfigInfo", method = RequestMethod.POST)
    public ResponseResult<List<TemplateConfigInfoResult>> getTemplateConfigInfo(@RequestBody DelPageInfoReq delPageInfoReq) {
        return ResponseResult.success(pageConfigService.getTemplateConfigInfo(delPageInfoReq));
    }


    /**
     * //TODO（元数据2.0已作废）
     * 获取业务对象列表（页面模板配置 选择业务对象时使用
     * @param delPageInfoReq pageid
     * @return {@link ResponseResult<List<TemplateConfigInfoResult>>}
     */
    @RequestMapping(path = "/getThirdObjListForPageConfig", method = RequestMethod.POST)
    public ResponseResult<List<ThirdObjectInfo>> getThirdObjListForPageConfig(@RequestBody DelPageInfoReq delPageInfoReq) {
        return ResponseResult.success(pageConfigService.getThirdObjListForPageConfig(delPageInfoReq));
    }


    /**
     * 删除页面模板 业务字段信息（支持多字段删除）
     *
     * @param delPageInfoReq pageid  thirdobjid thirdfieldid
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/delPageTemplateFieldConfig", method = RequestMethod.POST)
    public ResponseResult<String> delPageTemplateFieldConfig(@RequestBody DelPageInfoReq delPageInfoReq) {
        return ResponseResult.success(pageConfigService.delPageTemplateFieldConfig(delPageInfoReq));
    }


    /**
     * //TODO （元数据2.0已作废）
     *  获取业务字段列表（页面模板配置 选择业务对象下的业务字段时使用）
     *
     * @param pageTempThirdFieldListReq pageid thirdObjid
     * @return {@link ResponseResult<List<TemplateConfigInfoResult>>}
     */
    @RequestMapping(path = "/getThirdFieListForPageConfig", method = RequestMethod.POST)
    public ResponseResult<List<ThirdFieldInfo>> getThirdFieListForPageConfig(@RequestBody PageTempThirdFieldListReq pageTempThirdFieldListReq) {
        return ResponseResult.success(pageConfigService.getThirdFieListForPageConfig(pageTempThirdFieldListReq));
    }


    /**
     * 获得页面组系统列表
     *
     * @param request 请求
     * @return {@link ResponseResult<List<PageGroupSysResult>>}
     */
    @RequestMapping(path = "/getPageGroupSystem", method = RequestMethod.POST)
    public ResponseResult<List<PageGroupSysResult>> getPageGroupSystem(HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        return ResponseResult.success(pageConfigService.getPageGroupSystem(userInfoVo.getUserCode()));
    }


    /**
     * 添加或更新信息分组 （元数据2.0使用）
     *
     * @param messageGroupReq 消息组要求
     * @param request         请求
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/addOrUpdateMsgGroupInfo", method = RequestMethod.POST)
    public ResponseResult<Integer> addOrUpdateMsgGroupInfo(@RequestBody MessageGroupReq messageGroupReq, HttpServletRequest request) {
        ApiAcsPermissionResult apiAcsPermissionResult = PermissionUtil.get(request);
        UserInfoVo userInfoVo = apiAcsPermissionResult.getUserInfoVo();
        messageGroupReq.getMessageGroup().initializedUserInfo(userInfoVo.getUserCode(),userInfoVo.getUserName());
        return ResponseResult.success(pageConfigService.addOrUpdateMsgGroupInfo(messageGroupReq));
    }


    /**
     * 删除信息分组（元数据2.0使用）
     *
     * @param delMsgGroupReq 德尔味精集团要求
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/deleteMsgGroupInfo", method = RequestMethod.POST)
    public ResponseResult<String> deleteMsgGroupInfo(@RequestBody DelMsgGroupReq delMsgGroupReq) {
        return ResponseResult.success(pageConfigService.deleteMsgGroupInfo(delMsgGroupReq));
    }


    /**
     * 添加或更新信息分组模板（元数据2.0使用）
     *
     * @param messageGroupTempReq 消息组临时要求的事情
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/addOrUpdateMsgGrpTempInfo", method = RequestMethod.POST)
    public ResponseResult<String> addOrUpdateMsgGrpTempInfo(@RequestBody MessageGroupTempReq messageGroupTempReq) {
        return ResponseResult.success(pageConfigService.addOrUpdateMsgGrpTempInfo(messageGroupTempReq));
    }


    /**
     * 更新信息分组模板配置（元数据2.0使用）
     *
     * @param updateMsgGrpTempConfigReq 更新味精grp临时配置要求
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/updateMsgGrpTempConfig", method = RequestMethod.POST)
    public ResponseResult<String> updateMsgGrpTempConfig(@RequestBody UpdateMsgGrpTempConfigReq updateMsgGrpTempConfigReq) {
        return ResponseResult.success(pageConfigService.updateMsgGrpTempConfig(updateMsgGrpTempConfigReq));
    }


    /**
     * 获取信息分组模板 所选业务配置列表 （元数据2.0使用）
     *
     * @param msgGroupTempThirdListReq
     * @return {@link ResponseResult<JSONObject>}
     */
    @RequestMapping(path = "/getMsgGroupTempThirdList", method = RequestMethod.POST)
    public ResponseResult<List<MsgGroupTempThirdListResult>> getMsgGroupTempThirdList(@RequestBody MsgGroupTempThirdListReq msgGroupTempThirdListReq) {
        return ResponseResult.success(pageConfigService.getMsgGroupTempThirdList(msgGroupTempThirdListReq));
    }


    /**
     * 获取页面信息分组模板信息列表（元数据2.0使用）
     *
     * @param delPageInfoReq 页面id
     * @return {@link ResponseResult<List<MessageGroupTemplate>>}
     */
    @RequestMapping(path = "/getMsgGroupTempList", method = RequestMethod.POST)
    public ResponseResult<List<MessageGroup>> getMsgGroupTempList(@RequestBody DelPageInfoReq delPageInfoReq) {
        return ResponseResult.success(pageConfigService.getMsgGroupTempList(delPageInfoReq));
    }


    /**
     * 更新信息分组模板 排序（元数据2.0使用）
     *
     * @param msgGroupTempOrderReq 味精集团临时订单要求
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/updateMsgGroupTempOrder", method = RequestMethod.POST)
    public ResponseResult<String> updateMsgGroupTempOrder(@RequestBody MsgGroupTempOrderReq msgGroupTempOrderReq) {
        return ResponseResult.success(pageConfigService.updateMsgGroupTempOrder(msgGroupTempOrderReq));
    }


    /**
     * 获取信息分组模板配置详情（元数据2.0使用）
     *
     * @param msgGroupTempDetailReq 味精集团临时要求的细节
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/getMsgGroupTempDetail", method = RequestMethod.POST)
    public ResponseResult<String> getMsgGroupTempDetail(@RequestBody MsgGroupTempDetailReq msgGroupTempDetailReq) {
        return ResponseResult.success(pageConfigService.getMsgGroupTempDetail(msgGroupTempDetailReq));
    }


    /**
     * 删除信息分组模板配置（元数据2.0使用）
     *
     * @param msgGroupTempDetailReq 味精集团临时要求的细节
     * @return {@link ResponseResult<String>}
     */
    @RequestMapping(path = "/deleteMsgGroupTemp", method = RequestMethod.POST)
    public ResponseResult<String> deleteMsgGroupTemp(@RequestBody MsgGroupTempDetailReq msgGroupTempDetailReq) {
        return ResponseResult.success(pageConfigService.deleteMsgGroupTemp(msgGroupTempDetailReq));
    }






}
