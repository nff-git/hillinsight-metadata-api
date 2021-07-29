package hillinsight.metadata.api.dto.web;

import java.util.List;

/**
 * @ClassName TemplateConfigInfoResult
 * @Description TODO 页面模板配置 信息返回实体
 * @Author wcy
 * @Date 2020/12/1
 * @Version 1.0
 */
public class TemplateConfigInfoResult {

    private  Integer thirdObjId;

    private  String thirdObjName;

    private String thirdObjShowNameCn;

    private String thirdObjShowNameEn;


    private List<TemplateConfigInfoFieldResult> templateConfigInfoFieldResults;

    public Integer getThirdObjId() {
        return thirdObjId;
    }

    public void setThirdObjId(Integer thirdObjId) {
        this.thirdObjId = thirdObjId;
    }

    public String getThirdObjName() {
        return thirdObjName;
    }

    public void setThirdObjName(String thirdObjName) {
        this.thirdObjName = thirdObjName;
    }

    public List<TemplateConfigInfoFieldResult> getTemplateConfigInfoFieldResults() {
        return templateConfigInfoFieldResults;
    }

    public void setTemplateConfigInfoFieldResults(List<TemplateConfigInfoFieldResult> templateConfigInfoFieldResults) {
        this.templateConfigInfoFieldResults = templateConfigInfoFieldResults;
    }

    public String getThirdObjShowNameCn() {
        return thirdObjShowNameCn;
    }

    public void setThirdObjShowNameCn(String thirdObjShowNameCn) {
        this.thirdObjShowNameCn = thirdObjShowNameCn;
    }

    public String getThirdObjShowNameEn() {
        return thirdObjShowNameEn;
    }

    public void setThirdObjShowNameEn(String thirdObjShowNameEn) {
        this.thirdObjShowNameEn = thirdObjShowNameEn;
    }
}
