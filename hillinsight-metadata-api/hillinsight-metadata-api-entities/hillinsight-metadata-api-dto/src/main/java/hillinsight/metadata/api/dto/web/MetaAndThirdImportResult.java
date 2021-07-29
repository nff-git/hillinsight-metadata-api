package hillinsight.metadata.api.dto.web;

import java.util.List;
import java.util.Map;

/**
 * @ClassName MetaFieldImportResult
 * @Description TODO 元数据字段导入返回结果
 * @Author wcy
 * @Date 2020/11/23
 * @Version 1.0
 */
public class MetaAndThirdImportResult {

    private Integer totalCount;//导入行数

    private  Integer successCount;//成功条数

    private Map<String, List<Map<String, Object>>> failureList;//key：失败行数 value：失败原因

    private boolean isExSuccess = false;//是否全部导入成功  true  是  false  不是

    private String fieldId;//文件存储唯一标识

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Map<String, List<Map<String, Object>>> getFailureList() {
        return failureList;
    }

    public void setFailureList(Map<String, List<Map<String, Object>>> failureList) {
        this.failureList = failureList;
    }

    public boolean isExSuccess() {
        return isExSuccess;
    }

    public void setExSuccess(boolean exSuccess) {
        isExSuccess = exSuccess;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }
}
