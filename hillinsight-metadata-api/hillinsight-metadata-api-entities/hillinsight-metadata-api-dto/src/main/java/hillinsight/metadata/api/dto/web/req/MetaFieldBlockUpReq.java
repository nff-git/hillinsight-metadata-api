package hillinsight.metadata.api.dto.web.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 设置字段状态请求入参实体
 *
 * @author wangchunyu
 * @date 2020/11/25
 */
public class MetaFieldBlockUpReq {


    @NotNull(message = "字段id不能为空！")
    private Integer id;

    @NotNull(message = "字段状态不能为空！")
    private Integer status;

    private String  updatorId;

    private String  updatorName;

    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public  void initializedUserInfo(String userCode,String  userName){
        this.setUpdatorId(userCode);
        this.setUpdatorName(userName);
        this.setUpdateTime(new Date());
    }
}
