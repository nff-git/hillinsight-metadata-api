package hillinsight.metadata.api.dao.impl.dict;

import hillinsight.metadata.api.dao.dict.MetadataDictDao;
import hillinsight.metadata.api.mappers.dict.MetadataDictMapper;
import hillinsight.metadata.api.models.MetadataDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MetadataDaoImpl implements MetadataDictDao {

    @Autowired
    private MetadataDictMapper metadataDictMapper;

    @Override
    public List<MetadataDict> getParentDictList() {
        return metadataDictMapper.getParentDictList();
    }

    @Override
    public List<MetadataDict> getDictListByPath(String dictPath) {
        return metadataDictMapper.getDictListByPath(dictPath);
    }

    @Override
    public MetadataDict getDictByCode(String code, String dictPath) {
        return metadataDictMapper.getDictByCode(code, dictPath);
    }

    @Override
    public MetadataDict getDictByName(String name, String dictPath) {
        return metadataDictMapper.getDictByName(name, dictPath);
    }

    @Override
    public List<MetadataDict> getFTExpertConfigDictList() {
        return metadataDictMapper.getFTExpertConfigDictList();
    }
}
