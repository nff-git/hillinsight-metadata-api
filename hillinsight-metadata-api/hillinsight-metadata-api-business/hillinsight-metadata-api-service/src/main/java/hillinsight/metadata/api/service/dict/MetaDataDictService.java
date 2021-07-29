package hillinsight.metadata.api.service.dict;

import hillinsight.metadata.api.dto.web.req.MetaDataDictReq;
import hillinsight.metadata.api.models.MetadataDict;

import java.util.List;
import java.util.Map;

public interface MetaDataDictService {

    //查询所有父级字典
    List<MetadataDict> getParentDictList();

    //根据path查询子集字典列表
    List<MetadataDict> getDictListByPath(MetaDataDictReq metaDataDictReq);

    //根据code和字典路径查询字典
    MetadataDict getDictByCode(String code,String dictPath);

    //根据code和字典路径查询字典
    MetadataDict getDictByName(String name,String dictPath);


    //根据字典路径查询字段类型高级配置字典列表
    Map<String,List<MetadataDict>> getFTExpertConfigDictListByPath(MetaDataDictReq metaDataDictReq);
}
