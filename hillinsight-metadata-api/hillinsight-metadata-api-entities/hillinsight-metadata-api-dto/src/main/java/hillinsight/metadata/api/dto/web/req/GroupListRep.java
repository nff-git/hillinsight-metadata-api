package hillinsight.metadata.api.dto.web.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName GroupListRep
 * @Description TODO 获取分组集合条件入参实体
 * @Author wcy
 * @Date 2020/11/26
 * @Version 1.0
 */
public class GroupListRep {

    @NotBlank(message = "系统来源id不能为空")
    private String sourceId;

    private String pageGroup;

    private String userCode;//用户id  也是开发者id

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getPageGroup() {
        return pageGroup;
    }

    public void setPageGroup(String pageGroup) {
        this.pageGroup = pageGroup;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
