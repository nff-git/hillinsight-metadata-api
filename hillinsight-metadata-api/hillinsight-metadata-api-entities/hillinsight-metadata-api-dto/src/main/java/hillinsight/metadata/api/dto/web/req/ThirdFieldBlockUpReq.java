package hillinsight.metadata.api.dto.web.req;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 业务字段启停请求入参实体
 *
 * @author wangchunyu
 * @date 2020/11/25
 */
public class ThirdFieldBlockUpReq {

    @NotNull(message = "业务字段id不能为空！")
    private Integer id;

    @NotNull(message = "业务字段id不能为空！")
    private Integer status;//1启用 0 停用

    private String updatorId;

    private String updatorName;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public  void initializedUserInfo(String userCode, String  userName){
        Date date = new Date();
        this.setUpdatorId(userCode);
        this.setUpdatorName(userName);
        this.setUpdateTime(date);
    }
}
