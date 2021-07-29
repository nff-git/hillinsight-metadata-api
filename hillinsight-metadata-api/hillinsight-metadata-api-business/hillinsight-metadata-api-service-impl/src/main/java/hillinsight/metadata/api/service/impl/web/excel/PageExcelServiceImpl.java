package hillinsight.metadata.api.service.impl.web.excel;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hillinsight.metadata.api.dao.web.MetaDataInfoDao;
import hillinsight.metadata.api.dao.web.PageConfigDao;
import hillinsight.metadata.api.dao.web.ThirdInfoDao;
import hillinsight.metadata.api.dto.web.MsgGroupTempDetailResult;
import hillinsight.metadata.api.dto.web.req.MessageGroupReq;
import hillinsight.metadata.api.dto.web.req.MetaDataDictReq;
import hillinsight.metadata.api.dto.web.req.PageConfigImportReq;
import hillinsight.metadata.api.dto.web.PageConfigImportResult;
import hillinsight.metadata.api.models.MetadataDict;
import hillinsight.metadata.api.service.dict.MetaDataDictService;
import hillinsight.metadata.api.service.web.PageConfigService;
import hillinsight.metadata.api.service.web.excel.PageExcelService;
import hillinsight.metadata.api.utils.convention.Constant;
import hillinsight.metadata.api.utils.convention.PageTransferUtils;
import hillinsight.metadata.api.web.*;
import org.apache.ibatis.builder.BuilderException;
import org.ehcache.core.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName PageExcelServiceImpl
 * @Description TODO
 * @Author wcy
 * @Date 2020/12/2
 * @Version 1.0
 */
@Service
public class PageExcelServiceImpl implements PageExcelService {

    @Autowired
    private PageConfigDao pageConfigDao;

    @Autowired
    private ThirdInfoDao thirdInfoDao;

    @Autowired
    private MetaDataInfoDao metaDataInfoDao;

    @Autowired
    private PageConfigService pageConfigService;


    /**
     * 根据ids页面配置信息
     *
     * @param ids id
     * @return {@link List<MetadataPageInfo>}
     */
    @Override
    public List<MetadataPageInfo> getPageConfigInfoByIds(String ids) {
        List<MetadataPageInfo> results = new ArrayList<>();
        if (StrUtil.isEmpty(ids)) {
            throw new BuilderException("页面配置id集合不能为空");
        }
        String[] pageIds = ids.split(",");
        for (String pageId : pageIds) {
            //获取页面信息
            MetadataPageInfo metadataPageInfo = pageConfigDao.getPageInfoById(Integer.valueOf(pageId));
            if (null == metadataPageInfo) {
                throw new BuilderException("页面配置id为:" + pageId + "的配置不存在");
            }
            //通过pageId查询信息分组信息
            List<MessageGroup> msgGroupList = pageConfigDao.getMsgGroupListByPageId(metadataPageInfo.getId());
            for (MessageGroup msgGroup : msgGroupList) {
                //通过分组key查询模板信息
                List<MessageGroupTemplate> msgGroupTempList = pageConfigDao.getMsgGroupTempListByGroupKey(msgGroup.getMessageGroupKey());
                for (MessageGroupTemplate msgGroupTemp : msgGroupTempList) {
                    //信息分组key和模板信息中的key一致时插入到该组
                    if (msgGroupTemp.getMessageGroupKey().equals(msgGroup.getMessageGroupKey())) {
                        msgGroup.setMsgGroupTempList(msgGroupTempList);
                    }
                }
            }
            metadataPageInfo.setMsgGroupList(msgGroupList);
            results.add(metadataPageInfo);
        }
        return results;
    }


    /**
     * 导入页面信息
     *
     * @param pageConfigImportReq 导入信息
     * @return {@link PageConfigImportResult}
     */
    @Override
    public List<PageConfigImportResult> importPageInfo(@Validated PageConfigImportReq pageConfigImportReq) {
        //校验结果
        boolean flag = false;
        //页面配置导入结果
        PageConfigImportResult pageConfigImportResult = null;
        List<PageConfigImportResult> results = new ArrayList<>();//返回导入结果
        List<MetadataPageInfo> metadataPageInfos = pageConfigImportReq.getMetadataPageInfos();//页面配置信息
        Integer groupId = pageConfigImportReq.getGroupId();//分组id
        String sourceId = pageConfigImportReq.getSourceId();//系统来源id
        String sourceName = pageConfigImportReq.getSourceName();//系统来源名称
        for (MetadataPageInfo metadataPageInfo : metadataPageInfos) {
            pageConfigImportResult = new PageConfigImportResult();
            //数据填充
            metadataPageInfo.setGroupId(groupId);
            metadataPageInfo.setSourceId(sourceId);
            metadataPageInfo.setGroupName(sourceName);
            //校验数据
            flag = pageConfigService.validatedImportPageInfo(pageConfigImportResult, metadataPageInfo, sourceId);
            pageConfigService.importPage(groupId, metadataPageInfo, pageConfigImportResult, flag);
            results.add(pageConfigImportResult);
        }
        return results;
    }
}
