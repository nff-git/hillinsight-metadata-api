package hillinsight.metadata.api.dto.email;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class SendEmailRequest {

    private String emailAddressRequest;//邮件地址
    private String emailTypeRequest;//邮件类型
    private String authorizationCodeRequest;//授权码
    @NotBlank(message = "接收地址不能为空！")
    private String receiverAddressList;//接收地址 字符串数组：(59595@qq.com,1844848@qq.com)
    @NotBlank(message = "邮件标题不能为空！")
    private String subject;//邮件标题
    @NotBlank(message = "邮件内容不能为空！")
    private String content;//邮件内容

    @Pattern(regexp = "^[0-9]*$",message = "code码格式不正确")
    @NotBlank(message = "邮件地址code码不能为空！")
    private String emailAddCodeRequest;//邮件地址code码

    public String getEmailAddressRequest() {
        return emailAddressRequest;
    }

    public void setEmailAddressRequest(String emailAddressRequest) {
        this.emailAddressRequest = emailAddressRequest;
    }

    public String getEmailTypeRequest() {
        return emailTypeRequest;
    }

    public void setEmailTypeRequest(String emailTypeRequest) {
        this.emailTypeRequest = emailTypeRequest;
    }

    public String getAuthorizationCodeRequest() {
        return authorizationCodeRequest;
    }

    public void setAuthorizationCodeRequest(String authorizationCodeRequest) {
        this.authorizationCodeRequest = authorizationCodeRequest;
    }

    public String getReceiverAddressList() {
        return receiverAddressList;
    }

    public void setReceiverAddressList(String receiverAddressList) {
        this.receiverAddressList = receiverAddressList;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmailAddCodeRequest() {
        return emailAddCodeRequest;
    }

    public void setEmailAddCodeRequest(String emailAddCodeRequest) {
        this.emailAddCodeRequest = emailAddCodeRequest;
    }
}
