package hillinsight.metadata.api.dao.dict;

import hillinsight.metadata.api.models.MetadataDict;

import java.util.List;

public interface MetadataDictDao {
    List<MetadataDict> getParentDictList();

    List<MetadataDict> getDictListByPath(String dictPath);

    MetadataDict getDictByCode(String code, String dictPath);

    MetadataDict getDictByName(String name, String dictPath);

    List<MetadataDict> getFTExpertConfigDictList();
}
