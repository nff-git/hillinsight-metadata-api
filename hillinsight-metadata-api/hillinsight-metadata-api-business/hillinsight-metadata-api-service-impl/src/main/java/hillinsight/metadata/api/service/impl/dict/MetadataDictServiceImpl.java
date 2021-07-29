package hillinsight.metadata.api.service.impl.dict;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import focus.core.ResponseResult;
import hillinsight.metadata.api.dao.dict.MetadataDictDao;
import hillinsight.metadata.api.dto.web.req.MetaDataDictReq;
import hillinsight.metadata.api.models.MetadataDict;
import hillinsight.metadata.api.service.dict.MetaDataDictService;
import hillinsight.metadata.api.utils.convention.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class MetadataDictServiceImpl  implements MetaDataDictService {

    @Autowired
    private MetadataDictDao metadataDictDao;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 查询所有父级字典
     * @return
     */
    @Override
    public List<MetadataDict> getParentDictList() {
        List<MetadataDict> parentDictList = new ArrayList<>();
        List<String> redisString = redisTemplate.opsForList().range(Constant.REDIS_DICT_PARENT_ALL, 0, -1);
        for (String str : redisString) {
            MetadataDict metadataDict = JSONObject.
                    toJavaObject(JSONObject.parseObject(str), MetadataDict.class);
            parentDictList.add(metadataDict);
        }
        if(null == parentDictList  || parentDictList.size() < 1){
            parentDictList = metadataDictDao.getParentDictList();
            redisTemplate.opsForList().rightPush(Constant.REDIS_DICT_PARENT_ALL, String.valueOf(parentDictList));
            redisTemplate.expire(Constant.REDIS_DICT_PARENT_ALL, 60 * 60 * 24, TimeUnit.SECONDS);
        }else{

        }


        return parentDictList;
    }

    /**
     * 根据path查询子集字典列表
     * @param metaDataDictReq
     * @return
     */
    @Override
    public List<MetadataDict> getDictListByPath(@Validated  MetaDataDictReq metaDataDictReq) {
        String dictPath = metaDataDictReq.getDictPath();
        return metadataDictDao.getDictListByPath(dictPath);
    }

    /**
     * 根据code和字典路径查询字典
     * @param code
     * @param dictPath
     * @return
     */
    @Override
    public MetadataDict getDictByCode(String code, String dictPath) {
        return metadataDictDao.getDictByCode(code, dictPath);
    }

    @Override
    public MetadataDict getDictByName(String name, String dictPath) {
        return metadataDictDao.getDictByName(name,dictPath);
    }

    /**
     * 根据字典路径查询字段类型高级配置字典列表
     *
     * @param metaDataDictReq 元数据dict点播
     * @return {@link ResponseResult <List<MetadataDict>>}
     */
    @Override
    public Map<String, List<MetadataDict>> getFTExpertConfigDictListByPath(MetaDataDictReq metaDataDictReq) {
        Map<String, List<MetadataDict>> dictMap = new HashMap<>();//返回结果
        //获取业务字段类型中高级配置的所有字典列表
        List<MetadataDict> metadataDicts = metadataDictDao.getFTExpertConfigDictList();
        String dictPath = metaDataDictReq.getDictPath();
        //字典过滤
        if(StrUtil.isNotEmpty(dictPath)){
            String[] split = dictPath.split(",");
            Iterator<MetadataDict> iterator = metadataDicts.iterator();
            while (iterator.hasNext()){
                boolean flag = false;
                MetadataDict next = iterator.next();
                for (String s : split) {
                    if(s.equals(next.getDictPath())){//匹配字典路径
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    //删除字典元素
                    iterator.remove();
                }
            }
        }
        //字典分组
        for (MetadataDict metadataDict : metadataDicts) {
            if(dictMap.containsKey(metadataDict.getDictPath())){
                dictMap.get(metadataDict.getDictPath()).add(metadataDict);
            }else {
                List<MetadataDict> newDict = new ArrayList<>();
                newDict.add(metadataDict);
                dictMap.put(metadataDict.getDictPath(),newDict);
            }
        }
        return dictMap;
    }


}
