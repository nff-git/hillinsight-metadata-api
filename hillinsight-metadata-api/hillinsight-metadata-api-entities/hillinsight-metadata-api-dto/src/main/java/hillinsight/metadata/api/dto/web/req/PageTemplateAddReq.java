package hillinsight.metadata.api.dto.web.req;

import hillinsight.metadata.api.models.BaseModel;
import hillinsight.metadata.api.web.PageTemplate;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName PageTemplateAddReq
 * @Description TODO 新增页面模板请求入参实体
 * @Author wcy
 * @Date 2020/11/30
 * @Version 1.0
 */
public class PageTemplateAddReq  {


    /**
     * 新增页面模板信息使用
     */
    private Integer pageId;

    private Integer thirdObjId;

    private String thirdObjName;

    @Valid
    private List<PageTemplate> pageTemplateList;//页面模板新增 业务字段列表


    /**
     * 更新自定义模板信息使用
     */
    @Valid
    private  PageTemplate pageTemplate;

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

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

    public PageTemplate getPageTemplate() {
        return pageTemplate;
    }

    public void setPageTemplate(PageTemplate pageTemplate) {
        this.pageTemplate = pageTemplate;
    }

    public List<PageTemplate> getPageTemplateList() {
        return pageTemplateList;
    }

    public void setPageTemplateList(List<PageTemplate> pageTemplateList) {
        this.pageTemplateList = pageTemplateList;
    }
}

