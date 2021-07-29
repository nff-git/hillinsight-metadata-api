package hillinsight.metadata.api.portal.email;


import focus.core.PagedResult;
import focus.core.ResponseResult;
import hillinsight.metadata.api.dto.email.EmailAddressQueryRequest;
import hillinsight.metadata.api.dto.email.SendEmailRequest;
import hillinsight.metadata.api.email.EmailAddress;
import hillinsight.metadata.api.service.email.EmailAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/email")
public class EmailController {

    @Autowired
    private EmailAddressService emailAddressService;


    /**
     * 根据邮件地址code获取邮件地址信息
     *
     * @param request
     * @return 返回邮件地址信息
     */
    @RequestMapping(path = "/getEmailByCode", method = RequestMethod.POST)
    public ResponseResult<EmailAddress> queryCompanyInfo(@RequestBody EmailAddressQueryRequest request) {
        return ResponseResult.success(this.emailAddressService.getEmailAddressByCode(request));
    }

    /**
     * 发送邮件
     *
     * @param sendEmailRequest
     *@return 发送结果状态信息
     */
    @RequestMapping(path = "/sendEmail", method = RequestMethod.POST)
    public ResponseResult<String> sendEmail(@RequestBody @Validated SendEmailRequest sendEmailRequest) {
        return this.emailAddressService.sendEmail(sendEmailRequest);
    }
}
