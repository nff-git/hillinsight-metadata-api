package hillinsight.metadata.api.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import focus.mybatis.extensions.annotations.Id;
import hillinsight.metadata.api.models.BaseModel;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @ClassName MetaDataGroupInfo
 * @Description TODO 页面配置分组实体类
 * @Author wcy
 * @Date 2020/11/26
 * @Version 1.0
 */
public class MetaDataGroupInfo extends BaseModel {


    @TableId(type = IdType.AUTO)
    @Id
    private Integer id;

    private String pageGroup;

    @NotBlank(message = "系统来源id不能为空")
    private String sourceId;

    @NotBlank(message = "系统来源名称不能为空")
    private String sourceName;

    private String describe;

    private Integer status;//使用状态  1启用0禁用

    private List<MetaDataDeveloperInfo> metaDataDeveloperInfos;//开发者集合 非数据库字段

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPageGroup() {
        return pageGroup;
    }

    public void setPageGroup(String pageGroup) {
        this.pageGroup = pageGroup;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<MetaDataDeveloperInfo> getMetaDataDeveloperInfos() {
        return metaDataDeveloperInfos;
    }

    public void setMetaDataDeveloperInfos(List<MetaDataDeveloperInfo> metaDataDeveloperInfos) {
        this.metaDataDeveloperInfos = metaDataDeveloperInfos;
    }


    public  void initializedUserInfo(String userCode, String  userName){
        Date date = new Date();
        super.setCreatorId(userCode);
        super.setCreatorName(userName);
        super.setCreatorTime(date);
        super.setUpdatorId(userCode);
        super.setUpdatorName(userName);
        super.setUpdateTime(date);
    }
}

