package hillinsight.metadata.api.dto.web;

/**
 * 元字段列表结果
 *
 * @author wangchunyu
 * @date 2020/11/30
 */
public class MetaFieldListResult {

    private  Integer id;
    private  String fieldName;
    private  String dataOwnerId;
    private  String dataOwnerName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDataOwnerId() {
        return dataOwnerId;
    }

    public void setDataOwnerId(String dataOwnerId) {
        this.dataOwnerId = dataOwnerId;
    }

    public String getDataOwnerName() {
        return dataOwnerName;
    }

    public void setDataOwnerName(String dataOwnerName) {
        this.dataOwnerName = dataOwnerName;
    }
}
