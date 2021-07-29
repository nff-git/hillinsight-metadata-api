package hillinsight.metadata.api.dto.web;

/**
 * @ClassName PageConfigImportResult
 * @Description TODO 页面配置导入 返回结果
 * @Author wcy
 * @Date 2020/12/3
 * @Version 1.0
 */
public class PageConfigImportResult {

    private String pageTitle;//页面标题

    private String resultStatus;//导入结果

    private String errorMsg;//异常信息

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
