package hillinsight.metadata.api.mappers.dict;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hillinsight.metadata.api.mappers.dict.provider.MetadataDictProvider;
import hillinsight.metadata.api.mappers.web.provider.ThirdInfoProvider;
import hillinsight.metadata.api.models.MetadataDict;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetadataDictMapper extends BaseMapper<MetadataDict> {


    //查询所有父级字典
    @SelectProvider(type = MetadataDictProvider.class, method = "getParentDictList")
    List<MetadataDict> getParentDictList();

    //根据path查询 字典列表
    @SelectProvider(type = MetadataDictProvider.class, method = "getDictListByPath")
    List<MetadataDict> getDictListByPath(String dictPath);

    //根据code和path查询 字典列表
    @SelectProvider(type = MetadataDictProvider.class, method = "getDictByCode")
    MetadataDict getDictByCode(String code, String dictPath);

    //根据name和path查询字典
    @SelectProvider(type = MetadataDictProvider.class, method = "getDictByName")
    MetadataDict getDictByName(String name, String dictPath);

    //获取业务字段类型中高级配置的所有字典列表
    @SelectProvider(type = MetadataDictProvider.class, method = "getFTExpertConfigDictList")
    List<MetadataDict> getFTExpertConfigDictList();
}
