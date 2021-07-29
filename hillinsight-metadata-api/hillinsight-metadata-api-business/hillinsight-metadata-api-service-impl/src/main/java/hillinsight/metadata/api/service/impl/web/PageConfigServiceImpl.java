package hillinsight.metadata.api.service.impl.web;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import focus.core.PagedResult;
import focus.core.ResponseResult;
import hillinsight.metadata.api.dao.web.FieldTypeExtendDao;
import hillinsight.metadata.api.dao.web.MetaDataInfoDao;
import hillinsight.metadata.api.dao.web.PageConfigDao;
import hillinsight.metadata.api.dao.web.ThirdInfoDao;
import hillinsight.metadata.api.dto.web.*;
import hillinsight.metadata.api.dto.web.req.*;
import hillinsight.metadata.api.models.MetadataDict;
import hillinsight.metadata.api.service.dict.MetaDataDictService;
import hillinsight.metadata.api.service.web.PageConfigService;
import hillinsight.metadata.api.utils.convention.Constant;
import hillinsight.metadata.api.utils.convention.PageTransferUtils;
import hillinsight.metadata.api.utils.convention.UniqueUtils;
import hillinsight.metadata.api.web.*;
import org.apache.ibatis.builder.BuilderException;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.ehcache.core.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName PageConfigServiceImpl
 * @Description TODO
 * @Author wcy
 * @Date 2020/11/26
 * @Version 1.0
 */
@Service
public class PageConfigServiceImpl implements PageConfigService {

    @Autowired
    private PageConfigDao pageConfigDao;

    @Autowired
    private ThirdInfoDao thirdInfoDao;


    @Autowired
    private MetaDataInfoDao metaDataInfoDao;

    @Autowired
    private FieldTypeExtendDao fieldTypeExtendDao;

    @Autowired
    private MetaDataDictService metaDataDictService;

    /**
     * 添加或更新分组
     *
     * @param groupAddOrUpdReq 请求参数
     * @return {@link String}
     */
    @Override
    @Transactional
    public String addOrUpdateGroup(@Validated GroupAddOrUpdReq groupAddOrUpdReq) {
        MetaDataGroupInfo metaDataGroupInfo = groupAddOrUpdReq.getMetaDataGroupInfo();
        List<MetaDataDeveloperInfo> metaDataDeveloperInfos = metaDataGroupInfo.getMetaDataDeveloperInfos();
        //校验开发者
        validatedDeveloper(metaDataDeveloperInfos);
        //获取分组id
        Integer groupId = metaDataGroupInfo.getId();
        if (null == groupId) {//添加接口
            metaDataGroupInfo.setStatus(Constant.METADATA_ONE);
            pageConfigDao.addGroupInfo(metaDataGroupInfo);
            //新增分组同时绑定 开发者
            for (MetaDataDeveloperInfo metaDataDeveloperInfo : metaDataDeveloperInfos) {
                metaDataDeveloperInfo.setGroupId(metaDataGroupInfo.getId());
                pageConfigDao.addDeveloperInfo(metaDataDeveloperInfo);
            }
        } else {
            pageConfigDao.updateGroupInfo(metaDataGroupInfo);
            //删除分组所绑定的开发者
            pageConfigDao.deleteDeveloperByGroupId(groupId);
            //更新分组绑定的开发者
            for (MetaDataDeveloperInfo metaDataDeveloperInfo : metaDataDeveloperInfos) {
                metaDataDeveloperInfo.setGroupId(groupId);
                pageConfigDao.addDeveloperInfo(metaDataDeveloperInfo);
            }
        }
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 验证开发者
     *
     * @param metaDataDeveloperInfos 元数据开发人员信息
     */
    private void validatedDeveloper(List<MetaDataDeveloperInfo> metaDataDeveloperInfos) {
        if (null == metaDataDeveloperInfos || metaDataDeveloperInfos.size() < 1) {
            throw new BuilderException("开发者信息不能为空！");
        } else {
            for (MetaDataDeveloperInfo metaDataDeveloperInfo : metaDataDeveloperInfos) {
                if (StrUtil.isEmpty(metaDataDeveloperInfo.getDeveloperId()) ||
                        StrUtil.isEmpty(metaDataDeveloperInfo.getDeveloperName())) {
                    throw new BuilderException("开发者Id或开发者名称不能为空！");
                }
            }
        }
    }

    /**
     * 根据条件获取分组列表
     *
     * @param groupListRep 入参
     * @return {@link List<MetaDataGroupInfo>}
     */
    @Override
    public List<MetaDataGroupInfo> getGroupListByCriteria(@Validated GroupListRep groupListRep) {
        String pageGroup = groupListRep.getPageGroup();
        if (StrUtil.isNotEmpty(pageGroup)) {
            pageGroup = "%" + pageGroup + "%";
        }
        //根据登录用户id过滤获取 分组列表
        List<MetaDataGroupInfo> metaDataGroupInfos = pageConfigDao.getGroupListByCriteria(pageGroup, groupListRep.getSourceId(), groupListRep.getUserCode());
        for (MetaDataGroupInfo metaDataGroupInfo : metaDataGroupInfos) {
            List<MetaDataDeveloperInfo> metaDataDeveloperInfos = pageConfigDao.getDeveloperListByGroupId(metaDataGroupInfo.getId());
            metaDataGroupInfo.setMetaDataDeveloperInfos(metaDataDeveloperInfos);
        }
        return metaDataGroupInfos;
    }

    /**
     * 添加或更新页面
     *
     * @param pageAddOrUpdReq 请求参数
     * @return {@link String}
     */
    @Override
    public String addOrUpdatePage(@Validated PageAddOrUpdReq pageAddOrUpdReq) {
        MetadataPageInfo metadataPageInfo = pageAddOrUpdReq.getMetadataPageInfo();
        if (null == metadataPageInfo.getId()) {//添加
            metadataPageInfo.setPageKey(UniqueUtils.getpageKey("MD"));
            metadataPageInfo.setStatus(Constant.METADATA_ONE);
            pageConfigDao.addPage(metadataPageInfo);
        } else {//修改
            pageConfigDao.updatePage(metadataPageInfo);
        }
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 根据条件获取页面列表
     *
     * @param pageListReq 页面列表请求
     * @return {@link List<MetadataPageInfo>}
     */
    @Override
    public PagedResult<MetadataPageInfo> getPageListByCriteria(@Validated PageListReq pageListReq) {
        String criteria = pageListReq.getCriteria();
        if (StrUtil.isNotEmpty(criteria)) {
            criteria = "%" + criteria + "%";
        }
        PageHelper.startPage(pageListReq.getPageIndex(), pageListReq.getPageSize());
        List<MetadataPageInfo> metadataPageInfos = pageConfigDao.getPageListByCriteria(pageListReq.getGroupId(), criteria);
        //分组信息
        MetaDataGroupInfo metaDataGroupInfo = pageConfigDao.getGroupInfoById(pageListReq.getGroupId());
        if (null != metaDataGroupInfo) {
            for (MetadataPageInfo metadataPageInfo : metadataPageInfos) {
                metadataPageInfo.setGroupName(metaDataGroupInfo.getPageGroup());
            }
        }
        return PageTransferUtils.pageInfoTransferResult(new PageInfo<MetadataPageInfo>(metadataPageInfos));
    }

    /**
     * 删除页面信息
     *
     * @param delPageInfoReq 页面id
     * @return {@link String}
     */
    @Override
    public String delPage(@Validated DelPageInfoReq delPageInfoReq) {
        //删除页面
        pageConfigDao.delPage(delPageInfoReq.getPageId());
        //同时删除页面模板配置信息
        pageConfigDao.delAllPageTemplateByPageId(delPageInfoReq.getPageId());
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 添加页面模板信息
     *
     * @param pageTemplateAddReq 页面模板
     * @return {@link String}
     */
    @Override
    public String addPageTemplateInfo(PageTemplateAddReq pageTemplateAddReq) {
        if (null == pageTemplateAddReq.getPageId() || null == pageTemplateAddReq.getThirdObjName()) {
            throw new BuilderException("页面id和对象名称不能为空！");
        }
        //TODO 修改时间21年1月15日 根据业务对象id查询业务对象信息改为根据来源id和对象名称获取对象信息
        //获取页面配置信息
        MetadataPageInfo pageInfoById = pageConfigDao.getPageInfoById(pageTemplateAddReq.getPageId());
        if (null == pageInfoById) throw new BuilderException("页面配置信息不存在");
        //获取业务对象信息
        ThirdObjectInfo thirdObjectInfo = thirdInfoDao.
                getThirdObjectInfoByNameAndSourceId(pageTemplateAddReq.getThirdObjName(), pageInfoById.getSourceId());
        if (null == thirdObjectInfo) {
            throw new BuilderException("业务对象不存在！");
        } else {
            for (PageTemplate pageTemplate : pageTemplateAddReq.getPageTemplateList()) {
                //TODO 修改时间21年1月15日 根据业务字段id查询业务段信息改为根据来源id和对象id和字段名称获取字段信息
                ThirdFieldInfo thirdFieldInfo = thirdInfoDao.getFieldInfoByNameAndSourceIdAndObjId
                        (pageTemplate.getThirdFieldName(), pageInfoById.getSourceId(), thirdObjectInfo.getId());
                if (null == thirdFieldInfo) {
                    throw new BuilderException("未查询到入参业务字段名称为：" + pageTemplate.getThirdFieldName() + "的业务字段！");
                } else {
                    //创建 页面模板信息
                    pageTemplate.setPageId(pageTemplateAddReq.getPageId());
                    pageTemplate.setThirdObjId(thirdObjectInfo.getId());
                    pageTemplate.setThirdObjName(thirdObjectInfo.getObjectName());
                    pageTemplate.setThirdFieldId(thirdFieldInfo.getId());
                    pageTemplate.setThirdFieldName(thirdFieldInfo.getFieldName());
                    pageTemplate.setIsCustomField(Constant.METADATA_ZERO);//默认 不是自定义字段释义
                    //校验新增的页面模板在db中是否存在 不存在则新增
                    PageTemplate pageTemplateVo = pageConfigDao.getpageTempByPageIdAndObjNameAndFieName(pageTemplateAddReq.getPageId(), thirdObjectInfo.getObjectName(), thirdFieldInfo.getFieldName());
                    if (null == pageTemplateVo) {
                        pageConfigDao.addPageTemplateInfo(pageTemplate);
                    }
                }
            }
        }
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 更新页面模板自定义字段释义信息
     *
     * @param pageTemplateAddReq 页面模板添加请求
     * @return {@link String}
     */
    @Override
    public String updCustomFieldShow(PageTemplateAddReq pageTemplateAddReq) {
        PageTemplate pageTemplate = pageTemplateAddReq.getPageTemplate();
        if (null == pageTemplate.getIsCustomField()) {
            throw new BuilderException("自定义字段释义不能为空！");
        } else if (pageTemplate.getIsCustomField() == 1 && (StrUtil.isEmpty(pageTemplate.getFieldParaphraseCn()) ||
                StrUtil.isEmpty(pageTemplate.getFieldParaphraseEn()))) {
            throw new BuilderException("自定义字段释义时，释义中文和释义英文不能为空");
        } else if (null == pageTemplate.getId()) {
            throw new BuilderException("页面模板id不能为空！");
        }
        pageConfigDao.updCustomFieldShow(pageTemplate);
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 获取页面模板字段详情
     *
     * @param pageTemplateAddReq 页面模板添加请求
     * @return {@link PageTemplateFieldDelResult}
     */
    @Override
    public PageTemplateFieldDelResult getPageTempFieldDetail(@Validated PageTempFieldDetailReq pageTemplateAddReq) {
        if (StrUtil.isEmpty(pageTemplateAddReq.getSourceId()) || StrUtil.isEmpty(pageTemplateAddReq.getThirdFieldName())) {
            throw new BuilderException("系统来源或业务字段名称不能为空！");
        } else if (StrUtil.isEmpty(pageTemplateAddReq.getThirdObjName())) {
            throw new BuilderException("业务对象名称不能为空！");
        }
        PageTemplateFieldDelResult pageTemplateFieldDelResult = new PageTemplateFieldDelResult();
        String sourceId = pageTemplateAddReq.getSourceId();
        String thirdFieldName = pageTemplateAddReq.getThirdFieldName();
        //根据业务对象名称和系统id获取业务对象
        ThirdObjectInfo thirdObjectInfoByNameAndSourceId = thirdInfoDao.getThirdObjectInfoByNameAndSourceId(pageTemplateAddReq.getThirdObjName(), sourceId);
        if (null == thirdObjectInfoByNameAndSourceId) throw new BuilderException("业务对象不存在！");
        //根据业务字段名称和系统来源获取业务字段信息
        ThirdFieldInfo thirdFieldInfo = thirdInfoDao.getFieldInfoByNameAndSourceIdAndObjId(
                thirdFieldName, sourceId, thirdObjectInfoByNameAndSourceId.getId());
        if (null != thirdFieldInfo) {
            //获取元数据字段详情
            MetaDataFieldInfo metaDataFieldInfo = metaDataInfoDao.getFieldetailById(thirdFieldInfo.getMetadataFieldId());
            pageTemplateFieldDelResult.setMetaFieldName(metaDataFieldInfo.getFieldName());
            pageTemplateFieldDelResult.setMetaFieldParaphraseCn(metaDataFieldInfo.getFieldParaphraseCn());
            pageTemplateFieldDelResult.setMetaFieldParaphraseEn(metaDataFieldInfo.getFieldParaphraseEn());
        } else {
            throw new BuilderException("未查询到此业务字段！");
        }
        pageTemplateFieldDelResult.setThirdFieldName(thirdFieldInfo.getFieldName());
        pageTemplateFieldDelResult.setThirdFieldShowNameCn(thirdFieldInfo.getShowNameCn());
        pageTemplateFieldDelResult.setThirdFieldShowNameEn(thirdFieldInfo.getShowNameEn());
        return pageTemplateFieldDelResult;
    }

    /**
     * 获取模板配置信息
     *
     * @param delPageInfoReq 页面id
     * @return {@link List<TemplateConfigInfoResult>}
     */
    @Override
    public List<TemplateConfigInfoResult> getTemplateConfigInfo(@Validated DelPageInfoReq delPageInfoReq) {
        //根据业务对象id分组后获得的业务对象id列表
        List<TemplateConfigInfoResult> resultList = pageConfigDao.getThirdObjIdByObjIdGroup(delPageInfoReq.getPageId());
        //获取页面配置信息 根据 pageid
        MetadataPageInfo pageInfoById = pageConfigDao.getPageInfoById(delPageInfoReq.getPageId());
        String sourceId = "";
        if (null != pageInfoById) {
            sourceId = pageInfoById.getSourceId();
        } else throw new BuilderException("页面配置不存在！");
        for (TemplateConfigInfoResult InfoResult : resultList) {
            //填充业务对象显示名
            ThirdObjectInfo thirdObjectInfoById = thirdInfoDao.getThirdObjectInfoByNameAndSourceId(InfoResult.getThirdObjName(), sourceId);
            if (null != thirdObjectInfoById) {
                InfoResult.setThirdObjShowNameCn(thirdObjectInfoById.getShowNameCn());
                InfoResult.setThirdObjShowNameEn(thirdObjectInfoById.getShowNameEn());
            } else throw new BuilderException("业务对象不存在");
            //根据业务对象id获取页面模板配置的字段详情
            List<TemplateConfigInfoFieldResult> templateConfigInfoFieldResults = pageConfigDao.
                    getPageTempFieldDetailByThirdObjName(thirdObjectInfoById.getObjectName(), delPageInfoReq.getPageId());
            InfoResult.setTemplateConfigInfoFieldResults(templateConfigInfoFieldResults);
        }
        return resultList;
    }

    /**
     * 获取业务对象列表（页面模板配置 选择业务对象时使用）
     *
     * @param delPageInfoReq pageId 页面配置id
     * @return {@link List<ThirdObjectInfo>}
     */
    @Override
    public List<ThirdObjectInfo> getThirdObjListForPageConfig(@Validated DelPageInfoReq delPageInfoReq) {
        //根据页面id获取页面信息
        MetadataPageInfo pageInfoById = pageConfigDao.getPageInfoById(delPageInfoReq.getPageId());
        return pageConfigDao.getThirdObjListForPageConfig(delPageInfoReq.getPageId(), pageInfoById.getSourceId());
    }

    /**
     * 删除页面模板 业务字段信息（支持多字段删除）
     *
     * @param delPageInfoReq 页面id  业务对象id  业务字段id
     * @return {@link String}
     */
    @Override
    public String delPageTemplateFieldConfig(DelPageInfoReq delPageInfoReq) {
        List<Integer> ids = delPageInfoReq.getIds();
        if (null == ids || ids.size() < 1) {
            throw new BuilderException("id不能为空！");
        }
        for (Integer id : ids) {
            pageConfigDao.delPageTemplateFieldConfig(id);
        }
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 获取业务字段列表（页面模板配置 选择业务对象下的业务字段时使用）
     *
     * @param pageTempThirdFieldListReq pageid  thirdobjid
     * @return {@link List<ThirdFieldInfo>}
     */
    @Override
    public List<ThirdFieldInfo> getThirdFieListForPageConfig(@Validated PageTempThirdFieldListReq pageTempThirdFieldListReq) {

        //获取页面配置信息
        MetadataPageInfo pageInfoById = pageConfigDao.getPageInfoById(pageTempThirdFieldListReq.getPageId());
        if (null == pageInfoById) throw new BuilderException("页面信息不存在！");
        //获取业务对象信息
        ThirdObjectInfo thirdObjectInfoByNameAndSourceId = thirdInfoDao.
                getThirdObjectInfoByNameAndSourceId(pageTempThirdFieldListReq.getThirdObjName(), pageInfoById.getSourceId());

        if (null == thirdObjectInfoByNameAndSourceId) throw new BuilderException("页面信息不存在！");
        return pageConfigDao.getThirdFieListForPageConfig(pageTempThirdFieldListReq.getPageId(),
                pageTempThirdFieldListReq.getThirdObjName(), thirdObjectInfoByNameAndSourceId.getId());
    }

    /**
     * 获得页面组系统
     *
     * @param userCode 用户代码
     * @return {@link List<PageGroupSysResult>}
     */
    @Override
    public List<PageGroupSysResult> getPageGroupSystem(String userCode) {
        return pageConfigDao.getgroupListByDevelopId(userCode);
    }

    /**
     * 添加或更新信息分组
     *
     * @param messageGroupReq 消息组要求
     * @return {@link String}
     */
    @Override
    public Integer addOrUpdateMsgGroupInfo(@Validated MessageGroupReq messageGroupReq) {
        MessageGroup messageGroup = messageGroupReq.getMessageGroup();
        //校验页面是否存在
        MetadataPageInfo pageInfo = pageConfigDao.getPageInfoById(messageGroup.getPageId());
        if (pageInfo == null) throw new BuilderException("页面信息不存在！");

        if (null == messageGroup.getId()) {//添加
            //校验信息分组key是否重复
            MessageGroup messageGroupByKey = pageConfigDao.getMsgGroupByKeyAndPageId(
                    messageGroup.getMessageGroupKey());
            if (messageGroupByKey != null) throw new BuilderException("信息分组key重复！");
            pageConfigDao.addMessageGroupInfo(messageGroup);
        } else {
            pageConfigDao.updateMessageGroupInfo(messageGroup);
        }
        return messageGroup.getId();
    }

    /**
     * 添加或更新味精grp临时信息
     *
     * @param messageGroupTempReq 消息组临时要求的事情
     * @return {@link String}
     */
    @Override
    @Transactional
    public String addOrUpdateMsgGrpTempInfo(@Validated MessageGroupTempReq messageGroupTempReq) {
        List<MessageGroupTemplate> messageGroupTemplateList = messageGroupTempReq.getMessageGroupTemplateList();
        String messageGroupKey = messageGroupTempReq.getMessageGroupKey();
        //校验信息分组是否存在
        MessageGroup msgGroupByKey = pageConfigDao.getMsgGroupByKey(messageGroupKey);
        if (msgGroupByKey == null) throw new BuilderException("信息分组不存在！");
        //删除之前的配置信息
        pageConfigDao.deleteMsgGroupTemplateByKey(messageGroupKey);
        if (messageGroupTemplateList != null && messageGroupTemplateList.size() > 0) {
            for (int i = 0; i < messageGroupTemplateList.size(); i++) {
                MessageGroupTemplate messageGroupTemplate = messageGroupTemplateList.get(i);
                messageGroupTemplate.setConfigJson(getThirdFieldConfigInfo(messageGroupTemplate));
                //添加新的配置
                if (null == messageGroupTemplate.getIsUpdateConfig())
                    messageGroupTemplate.setIsUpdateConfig(Constant.METADATA_ZERO);
                pageConfigDao.addMsgGroupTemplateInfo(messageGroupTemplate);
            }
        }
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 删除信息分组
     *
     * @param delMsgGroupReq 德尔味精集团要求
     * @return {@link String}
     */
    @Override
    @Transactional
    public String deleteMsgGroupInfo(@Validated DelMsgGroupReq delMsgGroupReq) {
        MessageGroup messageGroup = pageConfigDao.getMsgGroupByKeyAndPageId(delMsgGroupReq.getMessageGroupKey());
        if (messageGroup == null) throw new BuilderException("信息分组不存在！");
        //删除信息分组
        pageConfigDao.deleteMsgGroupByKeyAndPageId(
                delMsgGroupReq.getMessageGroupKey(), delMsgGroupReq.getPageId());
        //删除信息分组模板
        pageConfigDao.deleteMsgGroupTemplateByKey(delMsgGroupReq.getMessageGroupKey());
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 更新信息分组模板配置
     *
     * @param updateMsgGrpTempConfigReq 更新味精grp临时配置要求
     * @return {@link String}
     */
    @Override
    public String updateMsgGrpTempConfig(@Validated UpdateMsgGrpTempConfigReq updateMsgGrpTempConfigReq) {
        pageConfigDao.updateMsgGrpTempConfig(updateMsgGrpTempConfigReq.getId(), updateMsgGrpTempConfigReq.getConfigJson());
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 获取信息分组模板 所选业务配置列表
     *
     * @param msgGroupTempThirdListReq 消息组的关键
     * @return {@link JSONObject}
     */
    @Override
    public List<MsgGroupTempThirdListResult> getMsgGroupTempThirdList(@Validated MsgGroupTempThirdListReq msgGroupTempThirdListReq) {
        List<MsgGroupTempThirdListResult> msgGroupTempThirdListResults = new ArrayList<>();
        //获取系统来源
//        MessageGroup msgGroup = pageConfigDao.getMsgGroupByKey(
//                msgGroupTempThirdListReq.getMessageGroupKey());
//        MetadataPageInfo pageInfo = pageConfigDao.getPageInfoById(msgGroup.getPageId());
        List<ThirdObjectInfo> thirdObjectInfos = thirdInfoDao.getThirdObjectInfoBySourceId(msgGroupTempThirdListReq.getSourceId());
        for (ThirdObjectInfo thirdObjectInfo : thirdObjectInfos) {
            MsgGroupTempThirdListResult msgGroupTempThirdListResult = new MsgGroupTempThirdListResult();
            msgGroupTempThirdListResult.setThirdObjectInfo(thirdObjectInfo);//填充业务对象
            //获取业务对象下的业务字段列表
            List<ThirdFieldInfo> thirdFields = thirdInfoDao.getThirdFieldByObjId(thirdObjectInfo.getId());
            if (thirdFields.size() > 0) msgGroupTempThirdListResult.setThirdFieldInfos(thirdFields);//填充业务字段列表
            msgGroupTempThirdListResults.add(msgGroupTempThirdListResult);//结果数据

        }
        return msgGroupTempThirdListResults;
    }

    /**
     * 获取页面信息分组模板信息列表
     *
     * @param delPageInfoReq 页面id
     * @return {@link ResponseResult <List<MessageGroupTemplate>>}
     */
    @Override
    public List<MessageGroup> getMsgGroupTempList(@Validated DelPageInfoReq delPageInfoReq) {
        //获取页面信息分组列表
        List<MessageGroup> messageGroups = pageConfigDao.getMsgGroupByPageId(
                delPageInfoReq.getPageId(), delPageInfoReq.getMessageGroupKey());
        for (MessageGroup messageGroup : messageGroups) {
            List<MessageGroupTemplate> messageGroupTemplates = pageConfigDao.getMsgGroupTempByKey(messageGroup.getMessageGroupKey());
            if (messageGroupTemplates.size() > 0) messageGroup.setMessageGroupTemplates(messageGroupTemplates);
        }
        return messageGroups;
    }

    /**
     * 更新信息分组模板 排序
     *
     * @param msgGroupTempOrderReq 味精集团临时订单要求
     * @return {@link String}
     */
    @Override
    public String updateMsgGroupTempOrder(@Validated MsgGroupTempOrderReq msgGroupTempOrderReq) {
        List<MessageGroupTemplate> messageGroupTemplates = pageConfigDao.getMsgGroupTempByKey(msgGroupTempOrderReq.getMessageGroupKey());
        LinkedList<MessageGroupTemplate> record = new LinkedList<>();
        int orderNumOne = msgGroupTempOrderReq.getOrderNumOne();
        int orderNumTwo = msgGroupTempOrderReq.getOrderNumTwo();
        if (orderNumOne > orderNumTwo) {
            //舍去 idOne 之后的数据 ，并且舍去 idTwo  之前的数据
            for (int i = orderNumTwo - 1; i < orderNumOne; i++) {
                record.add(messageGroupTemplates.get(i));
            }
            //链表数据 重组
            record.add(0, record.getLast());
            record.removeLast();
        } else if (orderNumOne < orderNumTwo) {
            //舍去 idOne 之前的数据 ，并且舍去 idTwo  之后的数据
            for (int i = orderNumOne - 1; i < orderNumTwo; i++) {
                record.add(messageGroupTemplates.get(i));
            }
            //链表数据 重组
            record.add(record.size(), record.getFirst());
            record.removeFirst();
        } else throw new BuilderException("排序数据有重复！");

        for (int i = 0; i < record.size(); i++) {
            MessageGroupTemplate messageGroupTemplate = record.get(i);
            if (orderNumOne > orderNumTwo) {
                pageConfigDao.updateMsgGrpTempOrder(messageGroupTemplate.getId(),
                        orderNumTwo + i);
            } else {
                pageConfigDao.updateMsgGrpTempOrder(messageGroupTemplate.getId(),
                        orderNumOne + i);
            }
        }
        return Constant.METADATA_SUCCESS_STRING;
    }

    /**
     * 获取信息分组模板配置详情
     *
     * @param msgGroupTempDetailReq 味精集团临时要求的细节
     * @return {@link String}
     */
    @Override
    public String getMsgGroupTempDetail(@Validated MsgGroupTempDetailReq msgGroupTempDetailReq) {
        //获取信息分组模板配置详情json
        MessageGroupTemplate messageGroupTemplate = pageConfigDao.getMsgGroupTempById(
                msgGroupTempDetailReq.getTempId());
        if (null != messageGroupTemplate && StrUtil.isNotEmpty(messageGroupTemplate.getConfigJson())) {
            //直接获取信息分组模板中的json配置
            return messageGroupTemplate.getConfigJson();
        } else {
            //获取 业务字段中的配置信息
            return getThirdFieldConfigInfo(messageGroupTemplate);
        }
    }

    /**
     * 删除信息分组模板
     *
     * @param msgGroupTempDetailReq 味精集团临时要求的细节
     * @return {@link String}
     */
    @Override
    public String deleteMsgGroupTemp(@Validated MsgGroupTempDetailReq msgGroupTempDetailReq) {
        pageConfigDao.deleteMsgGroupTempById(msgGroupTempDetailReq.getTempId());
        return Constant.METADATA_SUCCESS_STRING;
    }

    //构建业务字段中的配置信息
    private String getThirdFieldConfigInfo(MessageGroupTemplate messageGroupTemplate) {
        //构建数据
        MsgGroupTempDetailResult msgGroupTempDetailResult = new MsgGroupTempDetailResult();
        //获取信息分组
        MessageGroup msgGroup = pageConfigDao.getMsgGroupByKey(messageGroupTemplate.getMessageGroupKey());
        if (msgGroup == null) throw new BuilderException("获取信息分组时失败！");
        //获取页面信息
        MetadataPageInfo pageInfo = pageConfigDao.getPageInfoById(msgGroup.getPageId());
        if (msgGroup == null) throw new BuilderException("获取页面信息时失败！");
        //获取业务对象
        ThirdObjectInfo thirdObjectInfo = thirdInfoDao.getThirdObjInfoByNameAndSourceId(
                messageGroupTemplate.getThirdObjName(), pageInfo.getSourceId());
        if (thirdObjectInfo == null) throw new BuilderException("获取业务对象时失败！");
        //MsgGroupTempDetailResult   获取业务字段信息
        ThirdFieldInfo thirdFieldInfo = thirdInfoDao.getThirdFieldByNameAndObjId(
                messageGroupTemplate.getThirdFieldName(), thirdObjectInfo.getId());
        if (thirdFieldInfo == null) throw new BuilderException("获取业务字段时失败！");
        //获取业务字段对应的元数据信息2012
        if (!thirdFieldInfo.getFieldTypeCode().equals("2012")) {
            MetaDataFieldInfo metaDataFieldInfo = metaDataInfoDao.getFieldetailById(thirdFieldInfo.getMetadataFieldId());
            if (metaDataFieldInfo == null) throw new BuilderException("获取元数据字段时失败！");
            msgGroupTempDetailResult.setShowNameEn(thirdFieldInfo.getShowNameEn());//业务字段显示名en
            msgGroupTempDetailResult.setMetaFieldName(metaDataFieldInfo.getFieldName());//元数据字段名称
            msgGroupTempDetailResult.setFieldParaphraseCn(metaDataFieldInfo.getFieldParaphraseCn());//元数据字段释义cn
            msgGroupTempDetailResult.setFieldParaphraseEn(metaDataFieldInfo.getFieldParaphraseEn());//元数据字段释义en
        }
        msgGroupTempDetailResult.setFieldName(thirdFieldInfo.getFieldName());//业务字段名称
        msgGroupTempDetailResult.setShowNameCn(thirdFieldInfo.getShowNameCn());//业务字段显示名cn
        msgGroupTempDetailResult.setShowNameEn(thirdFieldInfo.getShowNameEn());//业务字段显示名en
        msgGroupTempDetailResult.setFieldTypeCode(thirdFieldInfo.getFieldTypeCode());
        msgGroupTempDetailResult.setFieldTypeName(thirdFieldInfo.getFieldTypeName());

        //获取高级配置
        List<String> extendCodeList = PageTransferUtils.buildExtendTableType();
        for (String s : extendCodeList) {
            if (s.equals(thirdFieldInfo.getFieldTypeCode())) {
                JSONObject o = fieldTypeExtendDao.selectOneByTableName(thirdFieldInfo.getId(), thirdFieldInfo.getFieldTypeCode());
                msgGroupTempDetailResult.setFieldTypeExtendMap(PageTransferUtils.jsonCamelCasing(o));
                break;
            }
        }
        return JSONObject.toJSON(msgGroupTempDetailResult).toString();
    }


    /**
     * 导入页面信息
     *
     * @param pageConfigImportResult 页面配置导入结果
     * @param metadataPageInfo       页面元数据信息
     * @param groupId                分组id
     * @param flag                   验证结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importPage(Integer groupId, MetadataPageInfo metadataPageInfo, PageConfigImportResult pageConfigImportResult, boolean flag) {
//        if (!CollectionUtils.isEmpty(metadataPageInfos)) {
//            for (MetadataPageInfo metadataPageInfo : metadataPageInfos) {
        if (flag) {
            //不同分组下的pageKey相同 提示错误（pageKey是唯一的不允许重复）
            MetadataPageInfo metadataPageInfoVoTwo = pageConfigDao.getPageCondfigByDiffGroupIdAndPageKey(groupId, metadataPageInfo.getPageKey());
            if(null != metadataPageInfoVoTwo){
                throw new BuilderException("pageKey为 【" + metadataPageInfo.getPageKey() + "】的已存在！");
            }
            //2判断 是新增或更新页面数据（相同分组下的pageKey相同为新增否则更新）
            MetadataPageInfo metadataPageInfoVo = pageConfigDao.getPageCondfigByGroupIdAndPageKey(groupId, metadataPageInfo.getPageKey());
            if (null == metadataPageInfoVo) {//新增
                metadataPageInfo.setStatus(Constant.METADATA_ONE);
                pageConfigDao.addPage(metadataPageInfo);
                //新增此页面下的信息分组
                List<MessageGroup> messageGroupListAdd = metadataPageInfo.getMsgGroupList();//页面下的分组信息
                for (MessageGroup messageGroup : messageGroupListAdd) {
                    //校验信息分组key是否重复
                    MessageGroup messageGroupByKey = pageConfigDao.getMsgGroupByKeyAndPageId(
                            messageGroup.getMessageGroupKey());
                    if (null == messageGroupByKey) {
                        //插入信息分组
                        messageGroup.setPageId(metadataPageInfo.getId());
                        pageConfigDao.addMessageGroupInfo(messageGroup);
                    } else {
                        pageConfigImportResult.setErrorMsg("信息分组key为【" + messageGroupByKey.getMessageGroupKey() + "】已存在！");
                        throw new BuilderException("信息分组key为【" + messageGroupByKey.getMessageGroupKey() + "】已存在！");
                    }
                    //新增信息分组下的模板信息
                    for (MessageGroupTemplate messageGroupTemp : messageGroup.getMsgGroupTempList()) {
                        MessageGroupTemplate msgGroupTempByKeyAndOrderNum = pageConfigDao.getMsgGroupTempByKeyAndOrderNum(messageGroup.getMessageGroupKey(), messageGroupTemp.getOrderNum());
                        //判断排序是否有重复
                        if (null != msgGroupTempByKeyAndOrderNum) {
                            pageConfigImportResult.setErrorMsg("信息分组key为【" + msgGroupTempByKeyAndOrderNum.getMessageGroupKey() + "】且orderNum为【" + msgGroupTempByKeyAndOrderNum.getOrderNum() + "】orderNum已存在！");
                            throw new BuilderException("信息分组key为【" + msgGroupTempByKeyAndOrderNum.getMessageGroupKey() + "】且orderNum为【" + msgGroupTempByKeyAndOrderNum.getOrderNum() + "】orderNum已存在！");
                        } else {
                            //插入模板信息
                            messageGroupTemp.setMessageGroupKey(messageGroup.getMessageGroupKey());
                            //添加新的配置
                            if (null == messageGroupTemp.getIsUpdateConfig())
                                messageGroupTemp.setIsUpdateConfig(Constant.METADATA_ZERO);
                            pageConfigDao.addMsgGroupTemplateInfo(messageGroupTemp);
                        }

                    }
                }
                pageConfigImportResult.setResultStatus(Constant.PPAGCONFIG_IMPROT_STATUS_NEW);//修改返回信息为新增状态
            } else {//修改操作
                metadataPageInfo.setId(metadataPageInfoVo.getId());
                pageConfigDao.updatePageForImportByPageKey(metadataPageInfo);
                pageConfigImportResult.setResultStatus(Constant.PPAGCONFIG_IMPROT_STATUS_UPDATE);//修改返回信息为更新状态
                List<MessageGroup> messageGroupListAdd = metadataPageInfo.getMsgGroupList();//页面下的分组信息
                for (MessageGroup messageGroup : messageGroupListAdd) {
                    //判断导入的信息分组是否存在重复
                    MessageGroup msgGroupByKeyAndPageId = pageConfigDao.getMsgGroupByKeyAndPageId(messageGroup.getMessageGroupKey());
                    if (null == msgGroupByKeyAndPageId) {//不存在 信息分组 添加
                        messageGroup.setPageId(metadataPageInfoVo.getId());
                        pageConfigDao.addMessageGroupInfo(messageGroup);
                    } else {//存在此信息分组 修改
                        messageGroup.setId(msgGroupByKeyAndPageId.getId());
                        pageConfigDao.updateMessageGroupInfo(messageGroup);
                    }
                    for (MessageGroupTemplate msgGroupTemp : messageGroup.getMsgGroupTempList()) {
                        //通过导入的对象名称和字段名称来判断是否存在重复
                        MessageGroupTemplate msgGrTemp = pageConfigDao.getMsgTempByMsgGroupKeyAndObjNameAndFieName(msgGroupTemp.getMessageGroupKey(), msgGroupTemp.getThirdObjName(),
                                msgGroupTemp.getThirdFieldName());
                        msgGroupTemp.setMessageGroupKey(messageGroup.getMessageGroupKey());
                        //查询是否有重复的排序num
                        MessageGroupTemplate msgGroupTempByKeyAndOrderNum = pageConfigDao.getMsgGroupTempByKeyAndOrderNum(msgGroupTemp.getMessageGroupKey(), msgGroupTemp.getOrderNum());
                        if (null == msgGrTemp) {//不存在 页面模板 添加
                            //判断排序是否有重复
                            if (null != msgGroupTempByKeyAndOrderNum) {//不存在重复排序 可以直接添加
                                pageConfigImportResult.setErrorMsg("信息分组key为【" + msgGroupTempByKeyAndOrderNum.getMessageGroupKey() + "】且orderNum为【" + msgGroupTempByKeyAndOrderNum.getOrderNum() + "】orderNum已存在！");
                                throw new BuilderException("信息分组key为【" + msgGroupTempByKeyAndOrderNum.getMessageGroupKey() + "】且orderNum为【" + msgGroupTempByKeyAndOrderNum.getOrderNum() + "】orderNum已存在！");
                            } else {
                                //添加新的配置
                                if (null == msgGroupTemp.getIsUpdateConfig())
                                    msgGroupTemp.setIsUpdateConfig(Constant.METADATA_ZERO);
                                pageConfigDao.addMsgGroupTemplateInfo(msgGroupTemp);
                            }
                        } else {//存在此页面模板 修改
                            msgGroupTemp.setId(msgGrTemp.getId());
                            pageConfigDao.updateMsgGrpTemp(msgGroupTemp);
                        }
                    }
                }
//                    }
//                }
            }
        }

    }

    /**
     * 验证导入页面信息
     *
     * @param pageConfigImportResult 页面配置导入结果
     * @param metadataPageInfo       页面元数据信息
     * @return boolean
     */

    @Override
    public boolean validatedImportPageInfo(PageConfigImportResult pageConfigImportResult, MetadataPageInfo metadataPageInfo, String sourceId) {
        boolean vailResultBoolean = false;//默认不通过
        String pageTitle = metadataPageInfo.getPageTitle(); //页面标题
        pageConfigImportResult.setPageTitle(pageTitle);
        pageConfigImportResult.setResultStatus(Constant.PPAGCONFIG_IMPROT_STATUS_FAIL);
        if (StrUtil.isEmpty(pageTitle)) {
            pageConfigImportResult.setPageTitle("未知页面标题");
            pageConfigImportResult.setErrorMsg("页面标题不能为空！");
        } else if (StrUtil.isEmpty(metadataPageInfo.getPageKey())) {
            pageConfigImportResult.setErrorMsg("pageKey不能为空！");
        } else {
            vailResultBoolean = true;//通过
        }

        //校验导入信息
        if (null != metadataPageInfo.getMsgGroupList() || metadataPageInfo.getMsgGroupList().size() > 0) {
            //校验页面模板导入信息
            List<MessageGroup> messageGroupList = metadataPageInfo.getMsgGroupList();

            for (MessageGroup msgGroup : messageGroupList) {
                //信息分组key重复计数情况
                Map<Object, Long> collect2 = messageGroupList.stream().collect(
                        Collectors.groupingBy(MessageGroup::getMessageGroupKey, Collectors.counting()));
                //筛出有重复的信息分组key
                List<Object> collect3 = collect2.keySet().stream().
                        filter(key -> collect2.get(key) > 1).collect(Collectors.toList());
                //1.1校验导入的json文件中信息分组key是否重复
                if (!CollectionUtils.isEmpty(collect3)) {
                    pageConfigImportResult.setErrorMsg("json文件中msgGroupList中的信息分组key为：" + collect3 + "的重复");
                    vailResultBoolean = false;//未通过
                    break;
                }
                //校验信息分组模板信息是否重复
                List<MessageGroupTemplate> repeatList = new ArrayList<>();//用于存放重复的元素的list
                List<MessageGroupTemplate> orderNumList = new ArrayList<>();//用于存放重复的orderNum的list
                for (int i = 0; i < msgGroup.getMsgGroupTempList().size() - 1; i++) {
                    for (int j = msgGroup.getMsgGroupTempList().size() - 1; j > i; j--) {
                        //把 信息分组key、业务对象、业务字段 重复的放到repeatList
                        if (msgGroup.getMsgGroupTempList().get(j).getMessageGroupKey().equals(msgGroup.getMsgGroupTempList().get(i).getMessageGroupKey()) &&
                                msgGroup.getMsgGroupTempList().get(j).getThirdObjName().equals(msgGroup.getMsgGroupTempList().get(i).getThirdObjName()) &&
                                msgGroup.getMsgGroupTempList().get(j).getThirdFieldName().equals(msgGroup.getMsgGroupTempList().get(i).getThirdFieldName())) {
                            repeatList.add(msgGroup.getMsgGroupTempList().get(j));//把相同元素加入list(找出相同的)
                        }
                        //把 orderNum 重复的放到orderNumList
                        if (msgGroup.getMsgGroupTempList().get(j).getMessageGroupKey().equals(msgGroup.getMsgGroupTempList().get(i).getMessageGroupKey()) &&
                                msgGroup.getMsgGroupTempList().get(j).getOrderNum().equals(msgGroup.getMsgGroupTempList().get(i).getOrderNum())) {
                            orderNumList.add(msgGroup.getMsgGroupTempList().get(j));//把相同元素加入list(找出相同的)
                        }
                    }
                }
                //1.2校验信息分组模板中 信息分组key、业务对象、业务字段 信息是否重复
                if (!CollectionUtils.isEmpty(repeatList)) {
                    pageConfigImportResult.setErrorMsg("json文件中msgGroupTempList中的信息分组key为：【" + repeatList.get(0).getMessageGroupKey() + "】" +
                            "、业务对象为【" + repeatList.get(0).getThirdObjName() + "】、业务字段为【" + repeatList.get(0).getThirdFieldName() + "】的模板信息重复");
                    vailResultBoolean = false;//未通过
                    break;
                }
                //1.3校验json中的orderNum是否重复
                if (!CollectionUtils.isEmpty(orderNumList)) {
                    pageConfigImportResult.setErrorMsg("json文件中msgGroupTempList中的信息分组key为：【" + orderNumList.get(0).getMessageGroupKey() + "】" +
                            "的orderNum为【" + orderNumList.get(0).getOrderNum() + "】的orderNum重复");
                    vailResultBoolean = false;//未通过
                    break;
                }
                //判断导入的信息分组是否存在重复
                for (MessageGroupTemplate msgGroupTemp : msgGroup.getMsgGroupTempList()) {
                    //1.4校验模板中的业务对象是否存在
                    ThirdObjectInfo thirdObjectInfoByIdAndName = thirdInfoDao.getThirdObjectInfoByNameAndSourceId(msgGroupTemp.getThirdObjName(), sourceId);
                    if (null == thirdObjectInfoByIdAndName) {
                        pageConfigImportResult.setErrorMsg("未查询到业务对象名称为：【" + msgGroupTemp.getThirdObjName() + "】的业务对象或者业务对象已停用");
                        vailResultBoolean = false;//未通过
                        break;
                    } else {
                        //1.5校验业务对象下的业务字段是否存在
                        ThirdFieldInfo thirdFieldInfo = thirdInfoDao.getThirdFieldByNameAndObjId(msgGroupTemp.getThirdFieldName(), thirdObjectInfoByIdAndName.getId());
                        if (null == thirdFieldInfo) {
                            pageConfigImportResult.setErrorMsg("未查询到业务字段名称为：【" + msgGroupTemp.getThirdFieldName() + "】的业务字段");
                            vailResultBoolean = false;//未通过
                            break;
                        }
                    }
                    //ConfigJson有值时再校验
                    if (!msgGroupTemp.getConfigJson().isEmpty()) {
                        //configJson
                        String config = JSONObject.toJSON(msgGroupTemp.getConfigJson()).toString();
                        //转换为信息分组模板对象
                        MsgGroupTempDetailResult msgGroupTempDetailResult = JSONObject.parseObject(config, MsgGroupTempDetailResult.class);
                        //根据业务字段名称和系统来源和业务对象id 获取业务字段信息
                        ThirdFieldInfo thirdFieldInfo = thirdInfoDao.getFieldInfoByNameAndSourceIdAndObjId(msgGroupTempDetailResult.getFieldName(), sourceId, thirdObjectInfoByIdAndName.getId());

                        //1.6校验configJson下的业务字段是否存在
                        if (null == thirdFieldInfo) {
                            pageConfigImportResult.setErrorMsg("configJson中未查询到业务字段名称为：【" + msgGroupTempDetailResult.getFieldName() + "】的业务字段");
                            vailResultBoolean = false;//未通过
                            break;
                        } else {
                            //1.7校验业务字段所对应的元数据字段名称是否匹配
                            if(!msgGroupTempDetailResult.getFieldTypeCode().equals("2012")){
                                //获取元数据字段详情
                                MetaDataFieldInfo metaDataFieldInfo = metaDataInfoDao.getFieldetailById(thirdFieldInfo.getMetadataFieldId());
                                if (!metaDataFieldInfo.getFieldName().equals(msgGroupTempDetailResult.getMetaFieldName())) {
                                    pageConfigImportResult.setErrorMsg("configJson中元数据名称为：【" + msgGroupTempDetailResult.getMetaFieldName() + "】的与业务字段不匹配");
                                    vailResultBoolean = false;//未通过
                                    break;
                                }
                            }
                            //1.8校验configJson中的字段名称与模板中的字段名称必须一致
                            if (!msgGroupTempDetailResult.getFieldName().equals(msgGroupTemp.getThirdFieldName())) {
                                pageConfigImportResult.setErrorMsg("configJson中fieldName为：【" + msgGroupTempDetailResult.getFieldName() + "】的必须与thirdFieldName为：【" + msgGroupTemp.getThirdFieldName() + "】的一致");
                                vailResultBoolean = false;//未通过
                                break;
                            }
                            //1.9校验configJson里的高级配置
                            vailResultBoolean = isVailResultBoolean(pageConfigImportResult, vailResultBoolean, msgGroupTempDetailResult);
                        }
                    }

                }
            }
        }
        return vailResultBoolean;
    }


    private boolean isVailResultBoolean(PageConfigImportResult pageConfigImportResult, boolean vailResultBoolean, MsgGroupTempDetailResult msgGroupTempDetailResult) {
        List<MetaDataDictReq> metaDataDictReqList = new ArrayList<>();

        //通过字典路径查询是否有该字典值
        MetaDataDictReq metaDataDictReq = new MetaDataDictReq();
        metaDataDictReq.setDictPath(Constant.UNIT_SHOW_LOCATIONINPUT);

        //通过字典路径查询是否有该字典值
        MetaDataDictReq reqValidata = new MetaDataDictReq();
        reqValidata.setDictPath(Constant.FORMAT_VALIDATA);

        //通过字典路径查询是否有该字典值
        MetaDataDictReq unitTransformReq = new MetaDataDictReq();
        unitTransformReq.setDictPath(Constant.UNIT_TRANSFORM);

        //通过字典路径查询是否有该字典值
        MetaDataDictReq numberWayReq = new MetaDataDictReq();
        numberWayReq.setDictPath(Constant.INPUT_NUMBER_WAY);

        //通过字典路径查询是否有该字典值
        MetaDataDictReq lineTypeReq = new MetaDataDictReq();
        lineTypeReq.setDictPath(Constant.LINE_TYPE);

        //通过字典路径查询是否有该字典值
        MetaDataDictReq dateFormatReq = new MetaDataDictReq();
        dateFormatReq.setDictPath(Constant.DATETIMEFORMAT);

        //通过字典路径查询是否有该字典值
        MetaDataDictReq timeFormatReq = new MetaDataDictReq();
        timeFormatReq.setDictPath(Constant.TIMEFORMAT);

        //通过字典路径查询是否有该字典值
        MetaDataDictReq weekFormatReq = new MetaDataDictReq();
        weekFormatReq.setDictPath(Constant.WEEKFORMAT);

        Map<String, List<MetadataDict>> numberWayValue = metaDataDictService.getFTExpertConfigDictListByPath(numberWayReq);

        Map<String, List<MetadataDict>> unitTransformValue = metaDataDictService.getFTExpertConfigDictListByPath(unitTransformReq);

        Map<String, List<MetadataDict>> dictValueValidata = metaDataDictService.getFTExpertConfigDictListByPath(reqValidata);

        Map<String, List<MetadataDict>> dictValue = metaDataDictService.getFTExpertConfigDictListByPath(metaDataDictReq);

        Map<String, List<MetadataDict>> lineTypeValue = metaDataDictService.getFTExpertConfigDictListByPath(lineTypeReq);

        Map<String, List<MetadataDict>> dateFormatValue = metaDataDictService.getFTExpertConfigDictListByPath(dateFormatReq);

        Map<String, List<MetadataDict>> timeFormatValue = metaDataDictService.getFTExpertConfigDictListByPath(timeFormatReq);

        Map<String, List<MetadataDict>> weekFormatValue = metaDataDictService.getFTExpertConfigDictListByPath(weekFormatReq);

        //校验高级配置
        if (Constant.FILEID_TYPE_CODE_MONEY.equals(msgGroupTempDetailResult.getFieldTypeCode())) {
            vailResultBoolean = isVailResultBooleanMoney(pageConfigImportResult, vailResultBoolean, msgGroupTempDetailResult, dictValue, unitTransformValue);
        } else if (Constant.FILEID_TYPE_CODE_VALUE.equals(msgGroupTempDetailResult.getFieldTypeCode())) {
            vailResultBoolean = isVailResultBooleanValue(pageConfigImportResult, vailResultBoolean, msgGroupTempDetailResult, dictValue);
        } else if (Constant.FILEID_TYPE_CODE_PERCENT.equals(msgGroupTempDetailResult.getFieldTypeCode())) {
            vailResultBoolean = isVailResultBooleanPercent(pageConfigImportResult, vailResultBoolean, msgGroupTempDetailResult, numberWayValue);
        } else if (Constant.FILEID_TYPE_CODE_TEXT.equals(msgGroupTempDetailResult.getFieldTypeCode())) {
            vailResultBoolean = isVailResultBooleanText(pageConfigImportResult, vailResultBoolean, msgGroupTempDetailResult, dictValueValidata, lineTypeValue);
        } else if (Constant.FILEID_TYPE_CODE_DT.equals(msgGroupTempDetailResult.getFieldTypeCode())) {
            vailResultBoolean = isVailResultBooleanDt(pageConfigImportResult, vailResultBoolean, msgGroupTempDetailResult, dateFormatValue, timeFormatValue, weekFormatValue);
        }
        return vailResultBoolean;
    }

    private boolean isVailResultBooleanPercent(PageConfigImportResult pageConfigImportResult, boolean vailResultBoolean, MsgGroupTempDetailResult msgGroupTempDetailResult, Map<String, List<MetadataDict>> numberWayValue) {
        FieldtypeExtendPercent fieldtypeExtendPercent = JSONObject.parseObject(JSONObject.toJSON(msgGroupTempDetailResult.getFieldTypeExtendMap()).toString(), FieldtypeExtendPercent.class);
//        if (null == fieldtypeExtendPercent.getIsEmploy()) {
//            pageConfigImportResult.setErrorMsg("isEmploy为【" + fieldtypeExtendPercent.getIsEmploy() + "】的 为空值;");
//            vailResultBoolean = false;//未通过
//        } else
        if (StrUtil.isNotEmpty(fieldtypeExtendPercent.getInputNumberWayName()) && !numberWayValue.get(Constant.INPUT_NUMBER_WAY).stream()
                .filter(item -> item.getName()
                        .equals(fieldtypeExtendPercent.getInputNumberWayName()))
                .findAny()
                .isPresent()) {//单位显示位置
            pageConfigImportResult.setErrorMsg("inputNumberWay为【" + fieldtypeExtendPercent.getInputNumberWayName() + "】的 未查询到此输入数值方式;");
            vailResultBoolean = false;//未通过
        } else if (null != fieldtypeExtendPercent.getIsEmploy() && (fieldtypeExtendPercent.getIsEmploy() != 0 && fieldtypeExtendPercent.getIsEmploy() != 1)) {
            pageConfigImportResult.setErrorMsg("isEmploy为【" + fieldtypeExtendPercent.getIsEmploy() + "】的 值必须是0或者1;");
            vailResultBoolean = false;//未通过
        }
        return vailResultBoolean;
    }

    private boolean isVailResultBooleanText(PageConfigImportResult pageConfigImportResult, boolean vailResultBoolean, MsgGroupTempDetailResult msgGroupTempDetailResult, Map<String, List<MetadataDict>> dictValueValidata, Map<String, List<MetadataDict>> lineTypeValue) {
        FieldtypeExtendText fieldtypeExtendText = JSONObject.parseObject(JSONObject.toJSON(msgGroupTempDetailResult.getFieldTypeExtendMap()).toString(), FieldtypeExtendText.class);
//        if (null == fieldtypeExtendText.getIsEmploy()) {
//            pageConfigImportResult.setErrorMsg("isEmploy 为【" + fieldtypeExtendText.getIsEmploy() + "】的为空值;");
//            vailResultBoolean = false;//未通过
//        } else
        if (StrUtil.isNotEmpty(fieldtypeExtendText.getLineTypeName()) && !lineTypeValue.get(Constant.LINE_TYPE).stream()
                .filter(item -> item.getName()
                        .equals(fieldtypeExtendText.getLineTypeName()))
                .findAny()
                .isPresent()) {//单位显示位置
            pageConfigImportResult.setErrorMsg("lineTypeName 为【" + fieldtypeExtendText.getLineTypeName() + "】的未查询到此行类型;");
            vailResultBoolean = false;//未通过
        } else if (StrUtil.isNotEmpty(fieldtypeExtendText.getFormatValidataName()) && !dictValueValidata.get(Constant.FORMAT_VALIDATA).stream()//邮箱
                .filter(item -> item.getName()
                        .equals(fieldtypeExtendText.getFormatValidataName()))
                .findAny()
                .isPresent()) {//单位显示位置
            pageConfigImportResult.setErrorMsg("formatValidataName为【" + fieldtypeExtendText.getFormatValidataName() + "】的 未查询到此邮箱类型;");
            vailResultBoolean = false;//未通过
        } else if (null != fieldtypeExtendText.getIsEmploy() && (fieldtypeExtendText.getIsEmploy() != 0 && fieldtypeExtendText.getIsEmploy() != 1)) {
            pageConfigImportResult.setErrorMsg("isEmploy为【" + fieldtypeExtendText.getIsEmploy() + "】的 值必须是0或者1;");
            vailResultBoolean = false;//未通过
        }
        return vailResultBoolean;
    }

    private boolean isVailResultBooleanDt(PageConfigImportResult pageConfigImportResult, boolean vailResultBoolean, MsgGroupTempDetailResult msgGroupTempDetailResult, Map<String, List<MetadataDict>> dateFormatValue, Map<String, List<MetadataDict>> timeFormatValue, Map<String, List<MetadataDict>> weekFormatValue) {
        FieldtypeExtendDt fieldtypeExtendDt = JSONObject.parseObject(JSONObject.toJSON(msgGroupTempDetailResult.getFieldTypeExtendMap()).toString(), FieldtypeExtendDt.class);
//        if (null == fieldtypeExtendDt.getIsEmploy()) {
//            pageConfigImportResult.setErrorMsg("isEmploy 为【" + fieldtypeExtendDt.getIsEmploy() + "】的为空值;");
//            vailResultBoolean = false;//未通过
//        } else
        if (null != fieldtypeExtendDt.getIsDate() && (fieldtypeExtendDt.getIsDate() != 0 && fieldtypeExtendDt.getIsDate() != 1)) {
            pageConfigImportResult.setErrorMsg("isDate为【" + fieldtypeExtendDt.getIsDate() + "】的 值必须是0或者1;");
            vailResultBoolean = false;//未通过
        } else if (null != fieldtypeExtendDt.getIsTime() && (fieldtypeExtendDt.getIsTime() != 0 && fieldtypeExtendDt.getIsTime() != 1)) {
            pageConfigImportResult.setErrorMsg("isTime为【" + fieldtypeExtendDt.getIsTime() + "】的 值必须是0或者1;");
            vailResultBoolean = false;//未通过
        } else if (StrUtil.isNotEmpty(fieldtypeExtendDt.getDateFormatName()) && !dateFormatValue.get(Constant.DATETIMEFORMAT).stream().filter(item -> item.getName().equals(fieldtypeExtendDt.getDateFormatName())).findAny().isPresent()) {//日期格式
            pageConfigImportResult.setErrorMsg("dateFormatName【" + fieldtypeExtendDt.getDateFormatName() + "】的 未查询到此日期格式;");
            vailResultBoolean = false;//未通过
        } else if (StrUtil.isNotEmpty(fieldtypeExtendDt.getTimeFormatName()) && !timeFormatValue.get(Constant.TIMEFORMAT).stream().filter(item -> item.getName().equals(fieldtypeExtendDt.getTimeFormatName())).findAny().isPresent()) {//时间格式
            pageConfigImportResult.setErrorMsg("timeFormatName为【" + fieldtypeExtendDt.getTimeFormatName() + "】的 未查询到此时间格式;");
            vailResultBoolean = false;//未通过
        } else if (null != fieldtypeExtendDt.getIsEmploy() && (fieldtypeExtendDt.getIsEmploy() != 0 && fieldtypeExtendDt.getIsEmploy() != 1)) {
            pageConfigImportResult.setErrorMsg("isEmploy为【" + fieldtypeExtendDt.getIsEmploy() + "】的 值必须是0或者1;");
            vailResultBoolean = false;//未通过
        }
        return vailResultBoolean;
//        else if (StrUtil.isNotEmpty(fieldtypeExtendDt.getWeekFormatName()) && !weekFormatValue.get(Constant.WEEKFORMAT).stream().filter(item -> item.getName().equals(fieldtypeExtendDt.getWeekFormatName())).findAny().isPresent()) {//星期格式
//            pageConfigImportResult.setErrorMsg("weekFormatName为【" + fieldtypeExtendDt.getWeekFormatName() + "】的 未查询到此星期格式;");
//            vailResultBoolean = false;//未通过
//        }
//        else if (null != fieldtypeExtendDt.getIsWeek() && (fieldtypeExtendDt.getIsWeek() != 0 && fieldtypeExtendDt.getIsWeek() != 1)) {
//            pageConfigImportResult.setErrorMsg("isWeek为【" + fieldtypeExtendDt.getIsWeek() + "】的 值必须是0或者1;");
//            vailResultBoolean = false;//未通过
//        }
    }

    private boolean isVailResultBooleanValue(PageConfigImportResult pageConfigImportResult, boolean vailResultBoolean, MsgGroupTempDetailResult msgGroupTempDetailResult, Map<String, List<MetadataDict>> dictValue) {
        FieldtypeExtendValue fieldtypeExtendValue = JSONObject.parseObject(JSONObject.toJSON(msgGroupTempDetailResult.getFieldTypeExtendMap()).toString(), FieldtypeExtendValue.class);
//        if (null == fieldtypeExtendValue.getIsEmploy()) {
//            pageConfigImportResult.setErrorMsg("isEmploy 为【" + fieldtypeExtendValue.getIsEmploy() + "】的为空值;");
//            vailResultBoolean = false;//未通过
//            //判断dictValue中是否存在此单位显示位置名称
//        } else
        if (StrUtil.isNotEmpty(fieldtypeExtendValue.getUnitShowlocationName()) && !dictValue.get(Constant.UNIT_SHOW_LOCATIONINPUT).stream().filter(item -> item.getName().equals(fieldtypeExtendValue.getUnitShowlocationName())).findAny().isPresent()) {//单位显示位置
            pageConfigImportResult.setErrorMsg("unitShowlocationName为【" + fieldtypeExtendValue.getUnitShowlocationName() + "】的 未查询到此单位显示位置名称;");
            vailResultBoolean = false;//未通过
        } else if (null != fieldtypeExtendValue.getIsEmploy() && (fieldtypeExtendValue.getIsEmploy() != 0 && fieldtypeExtendValue.getIsEmploy() != 1)) {
            pageConfigImportResult.setErrorMsg("isEmploy为【" + fieldtypeExtendValue.getIsEmploy() + "】的 值必须是0或者1;");
            vailResultBoolean = false;//未通过
        }
        return vailResultBoolean;
    }

    private boolean isVailResultBooleanMoney(PageConfigImportResult pageConfigImportResult, boolean vailResultBoolean, MsgGroupTempDetailResult msgGroupTempDetailResult, Map<String, List<MetadataDict>> dictValue, Map<String, List<MetadataDict>> unitTransformValue) {
        FieldtypeExtendMoney fieldtypeExtendMoney = JSONObject.parseObject(JSONObject.toJSON(msgGroupTempDetailResult.getFieldTypeExtendMap()).toString(), FieldtypeExtendMoney.class);
//        if (null == fieldtypeExtendMoney.getIsEmploy()) {
//            pageConfigImportResult.setErrorMsg("isEmploy为【" + fieldtypeExtendMoney.getIsEmploy() + "】的 为空值;");
//            vailResultBoolean = false;//未通过
//            //校验单位转换
//        } else
        if (StrUtil.isNotEmpty(fieldtypeExtendMoney.getUnitTransformName()) && !unitTransformValue.get(Constant.UNIT_TRANSFORM).stream()
                .filter(item -> item.getName()
                        .equals(fieldtypeExtendMoney.getUnitTransformName()))
                .findAny()
                .isPresent()) {//单位显示位置
            pageConfigImportResult.setErrorMsg("unitTransformName为【" + fieldtypeExtendMoney.getUnitTransformName() + "】的 未查询到此单位转换名称;");
            vailResultBoolean = false;//未通过

        } else if (StrUtil.isNotEmpty(fieldtypeExtendMoney.getUnitShowlocationName()) && !dictValue.get(Constant.UNIT_SHOW_LOCATIONINPUT).stream()
                .filter(item -> item.getName()
                        .equals(fieldtypeExtendMoney.getUnitShowlocationName()))
                .findAny()
                .isPresent()) {//单位显示位置
            pageConfigImportResult.setErrorMsg("unitShowlocationName为【" + fieldtypeExtendMoney.getUnitShowlocationName() + "】的 未查询到此单位显示位置名称;");
            vailResultBoolean = false;//未通过
        } else if (null != fieldtypeExtendMoney.getIsEmploy() && (fieldtypeExtendMoney.getIsEmploy() != 0 && fieldtypeExtendMoney.getIsEmploy() != 1)) {
            pageConfigImportResult.setErrorMsg("isEmploy为【" + fieldtypeExtendMoney.getIsEmploy() + "】的值必须是0或者1;");
            vailResultBoolean = false;//未通过
        } else if (null != fieldtypeExtendMoney.getIsShowCurrency() && (fieldtypeExtendMoney.getIsShowCurrency() != 0 && fieldtypeExtendMoney.getIsShowCurrency() != 1)) {
            pageConfigImportResult.setErrorMsg("isShowCurrency为【" + fieldtypeExtendMoney.getIsShowCurrency() + "】的 值必须是0或者1;");
            vailResultBoolean = false;//未通过
        }
        return vailResultBoolean;
    }
}
