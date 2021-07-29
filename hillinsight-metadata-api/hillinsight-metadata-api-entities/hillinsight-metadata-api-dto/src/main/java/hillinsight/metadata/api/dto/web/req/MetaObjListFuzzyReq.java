package hillinsight.metadata.api.dto.web.req;

/**
 * 获取对象列表请求入参实体
 *
 * @author wangchunyu
 * @date 2020/11/25
 */
public class MetaObjListFuzzyReq {

    private Integer thirdObjId;//业务对象id

    private  String objectName;//对象名

    private  String sourceId;//系统来源 id

    private  Integer metaDataObjId;//元数据对象id

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getThirdObjId() {
        return thirdObjId;
    }

    public void setThirdObjId(Integer thirdObjId) {
        this.thirdObjId = thirdObjId;
    }

    public Integer getMetaDataObjId() {
        return metaDataObjId;
    }

    public void setMetaDataObjId(Integer metaDataObjId) {
        this.metaDataObjId = metaDataObjId;
    }
}
