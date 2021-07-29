package hillinsight.metadata.api.web;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName MetaDataDeveloperInfo
 * @Description TODO 开发者实体类
 * @Author wcy
 * @Date 2020/11/26
 * @Version 1.0
 */
public class MetaDataDeveloperInfo {

    private  Integer id;

    @NotBlank(message = "开发者id不能为空")
    private String developerId;

    @NotBlank(message = "开发者id不能为空")
    private String developerName;

    private Integer groupId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
