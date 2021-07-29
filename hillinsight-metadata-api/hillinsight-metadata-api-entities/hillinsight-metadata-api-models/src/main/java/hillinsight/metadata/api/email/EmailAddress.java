package hillinsight.metadata.api.email;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import focus.hibernatevalidator.extensions.annotations.IntValues;
import focus.mybatis.extensions.annotations.Id;
import focus.mybatis.extensions.annotations.Table;
import org.apache.ibatis.type.JdbcType;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 邮件地址
 * @author  wcy
 * @date 2020年9月18日15:24:43
 */
@TableName("t_email_address")
@Table(tableName = "t_email_address")
public class EmailAddress {
    @TableId(type = IdType.AUTO)
    @Id
    private int  id;
    private String emailAddress;//邮件地址
    private String emailAddressCode;//邮件地址code码
    private Date createTime;
    private int deleteFlag;//删除标记 1未删除 0已删除
    private String emailType;//邮件类型
    private String authorizationCode;//授权码

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddressCode() {
        return emailAddressCode;
    }

    public void setEmailAddressCode(String emailAddressCode) {
        this.emailAddressCode = emailAddressCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
}
