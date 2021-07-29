package hillinsight.metadata.api.dto.web.req;

import javax.validation.constraints.NotBlank;

/**
 * 业务对象excel导出请求入参实体
 *
 * @author nff
 * @date 2021/2/18
 */
public class ThirdObjectExcelExportReq {

    private String ids;//对象管理字段id

    private Integer thirdObjectId;//业务对象id

    private String sourceId;//系统id

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getThirdObjectId() {
        return thirdObjectId;
    }

    public void setThirdObjectId(Integer thirdObjectId) {
        this.thirdObjectId = thirdObjectId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}
