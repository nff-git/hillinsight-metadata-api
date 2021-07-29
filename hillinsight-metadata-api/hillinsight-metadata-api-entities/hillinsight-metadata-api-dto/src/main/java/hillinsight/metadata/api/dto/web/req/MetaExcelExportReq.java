package hillinsight.metadata.api.dto.web.req;

import javax.validation.constraints.NotBlank;

/**
 * 对象管理excel导出请求入参实体
 *
 * @author nff
 * @date 2021/2/18
 */
public class MetaExcelExportReq {

    private String ids;//对象管理字段id

    private  Integer metaDataObjId;//元数据对象id

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getMetaDataObjId() {
        return metaDataObjId;
    }

    public void setMetaDataObjId(Integer metaDataObjId) {
        this.metaDataObjId = metaDataObjId;
    }
}
