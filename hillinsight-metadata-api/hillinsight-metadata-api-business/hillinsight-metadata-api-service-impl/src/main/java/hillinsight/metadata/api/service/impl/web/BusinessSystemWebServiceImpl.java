package hillinsight.metadata.api.service.impl.web;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import hillinsight.metadata.api.dao.web.FieldTypeExtendDao;
import hillinsight.metadata.api.dao.web.MetaDataInfoDao;
import hillinsight.metadata.api.dao.web.PageConfigDao;
import hillinsight.metadata.api.dao.web.ThirdInfoDao;
import hillinsight.metadata.api.dto.web.BusiSysResultV2;
import hillinsight.metadata.api.dto.web.req.BusiSysReq;
import hillinsight.metadata.api.dto.web.BusiSysResult;
import hillinsight.metadata.api.dto.web.TemplateConfigInfoResult;
import hillinsight.metadata.api.service.web.BusinessSystemWebService;
import hillinsight.metadata.api.utils.convention.PageTransferUtils;
import hillinsight.metadata.api.web.*;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName BusinessSystemWebServiceImpl
 * @Description TODO
 * @Author wcy
 * @Date 2021/1/7
 * @Version 1.0
 */
@Service
public class BusinessSystemWebServiceImpl implements BusinessSystemWebService {

    @Autowired
    private PageConfigDao pageConfigDao;

    @Autowired
    private ThirdInfoDao thirdInfoDao;

    @Autowired
    private MetaDataInfoDao metaDataInfoDao;

    @Autowired
    public RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private FieldTypeExtendDao fieldTypeExtendDao;


    /**
     * 获取业务系统页面配置
     *
     * @param busiSysReq 商业系统要求
     * @return {@link BusiSysResult}
     */
    @Override
    public BusiSysResult getBusinessSysPageConfig(@Validated BusiSysReq busiSysReq) {
        BusiSysResult busiSysResult = null;//返回结果集
        //reids中获取业务系统配置信息
        BusiSysResult busiSysResultRedis = (BusiSysResult) redisTemplate.opsForValue().get(busiSysReq.getSourceId() + ":" + busiSysReq.getPageKey());
        if (null != busiSysResultRedis) {
            return busiSysResultRedis;
        } else {
            busiSysResult = new BusiSysResult(busiSysReq.getPageKey());
            MetadataPageInfo metadataPageInfo = pageConfigDao.getPageCondfigBySourceIdAndPageKey
                    (busiSysReq.getSourceId(), busiSysReq.getPageKey());
            if (null == metadataPageInfo) {
                throw new BuilderException("未查询到此系统下的页面配置信息");
            }
            Map<String, Map<String, Object>> objMapResult = new HashMap<>();//返回map类型的对象以及字段信息
            busiSysResult.setObjects(objMapResult);
            //根据业务对象id分组后获得的业务对象id列表
            List<TemplateConfigInfoResult> resultList = pageConfigDao.getThirdObjIdByObjIdGroup(metadataPageInfo.getId());
            for (TemplateConfigInfoResult templateConfigInfoResult : resultList) {
                Map<String, Object> thirdObjInfoMap = new HashMap<>();//封装的 对象信息
                //TODO 修改2021年1月14日 获取业务对象信息 修改为通过对象名称和系统来源获取
                ThirdObjectInfo thirdObjectInfoById = thirdInfoDao.getThirdObjectInfoByNameAndSourceId(templateConfigInfoResult.getThirdObjName(),
                        metadataPageInfo.getSourceId());
                if (null == thirdObjectInfoById) {
                    throw new BuilderException("页面配置中设置的业务对象不存在,对象名称为：{" + templateConfigInfoResult.getThirdObjName() + "}");
                }
                //填充业务对象信息
                thirdObjInfoMap.put("ThirdObjshowNameCn", thirdObjectInfoById.getShowNameCn());
                thirdObjInfoMap.put("ThirdObjshowNameEn", thirdObjectInfoById.getShowNameEn());
                //将业务对象map信息插入到返回结果集中
                objMapResult.put(thirdObjectInfoById.getObjectName(), thirdObjInfoMap);

                //填充业务字段列表
                //TODO 修改时间2021年月14日 将业务对象id替换为名称
                List<PageTemplate> pageTemplates = pageConfigDao.getPageTemplateListByPageIdAndThirdObjName(metadataPageInfo.getId(),
                        templateConfigInfoResult.getThirdObjName());
                Map<String, Object> thirdFieldDetailap = new HashMap<>();//封装的 业务字段详情信息
                for (PageTemplate pageTemplate : pageTemplates) {
                    Map<String, Object> thirdieldParaphraselap = new HashMap<>();//封装的 字段显示名
                    if (StrUtil.isEmpty(pageTemplate.getFieldParaphraseCn()) && StrUtil.isEmpty(pageTemplate.getFieldParaphraseEn())) {
                        //自定义字段释义为空时 以业务字段释义为主
                        //TODO 修改时间  2021年1月14日 将通过模板中的 对象id和字段id获取的字段信息 改为 通过系统来源和字段名称和业务对象名称
                        //根据业务对象名称和系统id获取业务对象
                        ThirdObjectInfo thirdObjectInfoByNameAndSourceId = thirdInfoDao.getThirdObjectInfoByNameAndSourceId(pageTemplate.getThirdObjName(), metadataPageInfo.getSourceId());
                        if (null == thirdObjectInfoByNameAndSourceId) throw new BuilderException("业务对象不存在！");
                        ThirdFieldInfo thirdFieldByIdAndObjId = thirdInfoDao.getFieldInfoByNameAndSourceIdAndObjId(pageTemplate.getThirdFieldName(), metadataPageInfo.getSourceId(), thirdObjectInfoByNameAndSourceId.getId());
                        if (null != thirdFieldByIdAndObjId) {
                            thirdieldParaphraselap.put("ThirdFieldshowNameCn", thirdFieldByIdAndObjId.getShowNameCn());
                            thirdieldParaphraselap.put("ThirdFieldshowNameEn", thirdFieldByIdAndObjId.getShowNameEn());
                            thirdieldParaphraselap.put("fieldTypeCode", thirdFieldByIdAndObjId.getFieldTypeCode());
                            //获取业务字段绑定的元数据字段释义
                            MetaDataFieldInfo fieldetailById = metaDataInfoDao.getFieldetailById(thirdFieldByIdAndObjId.getMetadataFieldId());
                            if (null != fieldetailById) {
                                thirdieldParaphraselap.put("ThirdFieldParaphraseCn", fieldetailById.getFieldParaphraseCn());
                                thirdieldParaphraselap.put("ThirdFieldParaphraseEn", fieldetailById.getFieldParaphraseEn());
                            }
                            //获取字段类型高级配置
                            List<String> strings = PageTransferUtils.buildExtendTableType();
                            for (String s : strings) {
                                if (s.equals(thirdFieldByIdAndObjId.getFieldTypeCode())) {
                                    JSONObject o = fieldTypeExtendDao.selectOneByTableName(thirdFieldByIdAndObjId.getId(), thirdFieldByIdAndObjId.getFieldTypeCode());
                                    if (null != o) {
                                        thirdieldParaphraselap.put("fieldTypeExtendMap", PageTransferUtils.jsonCamelCasing(o));
                                    } else {
                                        thirdieldParaphraselap.put("fieldTypeExtendMap", new JSONObject());
                                    }
                                    break;
                                }
                            }
                        }
                    } else {
                        thirdieldParaphraselap.put("ThirdFieldParaphraseCn", pageTemplate.getFieldParaphraseCn());
                        thirdieldParaphraselap.put("ThirdFieldParaphraseEn", pageTemplate.getFieldParaphraseEn());
                    }
                    //填充业务字段详情（字段名为Key的map）
                    thirdFieldDetailap.put(pageTemplate.getThirdFieldName(), thirdieldParaphraselap);
                }
                //封装业务字段map  key为fields
                thirdObjInfoMap.put("fields", thirdFieldDetailap);
            }
            //向redis中插入数据
            redisTemplate.opsForValue().set(busiSysReq.getSourceId() + ":" + busiSysReq.getPageKey(), busiSysResult, 60 * 10, TimeUnit.SECONDS);
        }
        return busiSysResult;
    }

    /**
     * 获取业务系统页面配置信息 (元数据2.0使用)
     *
     * @param busiSysReq 商业系统要求
     * @return {@link BusiSysResultV2}
     */
    @Override
    public BusiSysResultV2 getBusinessSysPageConfigV2(@Validated BusiSysReq busiSysReq) {
        BusiSysResultV2 busiSysResultV2 = null;//返回结果集
        //reids中获取业务系统配置信息
        BusiSysResultV2 busiSysResultRedisv2 = (BusiSysResultV2) redisTemplate.opsForValue().
                get(busiSysReq.getSourceId() + ":" + busiSysReq.getPageKey());
        if (null != busiSysResultRedisv2) {
            return busiSysResultRedisv2;
        } else {
            busiSysResultV2 = new BusiSysResultV2(busiSysReq.getPageKey());
            MetadataPageInfo metadataPageInfo = pageConfigDao.getPageCondfigBySourceIdAndPageKey
                    (busiSysReq.getSourceId(), busiSysReq.getPageKey());
            if (null == metadataPageInfo) {
                throw new BuilderException("未查询到此系统下的页面配置信息");
            }
            Map<String, Map<String, Object>> MsgGroupMapResult = new HashMap<>();//返回map类型的信息分组配置
            busiSysResultV2.setMsgGroups(MsgGroupMapResult);
            //获取信息分组列表
            List<MessageGroup> msgGroup = pageConfigDao.getMsgGroupByPageId(metadataPageInfo.getId(),null);
            for (MessageGroup messageGroup : msgGroup) {
                Map<String, Object> map = new HashMap<>();//信息分组模板 map
                //根据信息分组key获取 信息分组模板列表
                List<MessageGroupTemplate> msgGroupTemp = pageConfigDao.getMsgGroupTempByKey(
                        messageGroup.getMessageGroupKey());
                if (msgGroupTemp != null && msgGroupTemp.size() > 0) {
                    for (MessageGroupTemplate messageGroupTemplate : msgGroupTemp) {
                        Map<String, Object> FieldsMap = new HashMap<>();//字段配置map
                        //获取业务对象信息
                        ThirdObjectInfo thirdObjectInfo = thirdInfoDao.getThirdObjInfoByNameAndSourceId(
                                messageGroupTemplate.getThirdObjName(), metadataPageInfo.getSourceId());
                        if(null == thirdObjectInfo) throw  new BuilderException("业务对象{"+messageGroupTemplate.getThirdObjName()+"}信息不存在！");
//                            FieldsMap.put("ThirdObjshowNameCn", thirdObjectInfo.getShowNameCn());
//                            FieldsMap.put("ThirdObjshowNameEn", thirdObjectInfo.getShowNameEn());
                        //获取业务字段
                        ThirdFieldInfo thirdFieldInfo = thirdInfoDao.getThirdFieldByNameAndObjId(
                                messageGroupTemplate.getThirdFieldName(), thirdObjectInfo.getId());
                        if(null == thirdFieldInfo) throw  new BuilderException("业务字段{"+messageGroupTemplate.getThirdFieldName()+"}信息不存在！");
//                        FieldsMap.put("FieldTypeCode", thirdFieldInfo.getFieldTypeCode());
//                        FieldsMap.put("FieldTypeName", thirdFieldInfo.getFieldTypeName());
//                        FieldsMap.put("ThirdObjName", messageGroupTemplate.getThirdObjName());
//                        FieldsMap.put("ThirdFieldName",messageGroupTemplate.getThirdFieldName());
                        FieldsMap.put("OrderNum",messageGroupTemplate.getOrderNum());
                        FieldsMap.put("ConfigJson", messageGroupTemplate.getConfigJson());
                        map.put(messageGroupTemplate.getThirdFieldName(), FieldsMap);//字段配置信息存入信息模板中
                    }
                }
                MsgGroupMapResult.put(messageGroup.getMessageGroupKey(), map);//信息分组模板配置列表存入返回结果map中
            }
            //向redis中插入数据
            redisTemplate.opsForValue().set(busiSysReq.getSourceId() + ":" + busiSysReq.getPageKey(), busiSysResultV2, 60 * 10, TimeUnit.SECONDS);
        }

        return busiSysResultV2;
    }

    /**
     * 删除,缓存
     *
     * @param busiSysReq 商业系统要求
     * @return {@link String}
     */
    @Override
    public String deleteRedisCache(@Validated BusiSysReq busiSysReq) {
        Boolean delete = redisTemplate.delete(busiSysReq.getSourceId() + ":" + busiSysReq.getPageKey());
        if(!delete) return "刷新失败！";
        return "操作成功";
    }
}




