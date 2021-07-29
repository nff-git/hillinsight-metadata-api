package hillinsight.metadata.api.service.impl.email;

import com.alibaba.fastjson.JSONObject;
import focus.core.ResponseResult;
import focus.core.utils.StringUtil;
import hillinsight.metadata.api.dao.email.EmailAddressDao;
import hillinsight.metadata.api.dto.email.EmailAddressQueryRequest;
import hillinsight.metadata.api.dto.email.SendEmailRequest;
import hillinsight.metadata.api.email.EmailAddress;
import hillinsight.metadata.api.service.email.EmailAddressService;
import hillinsight.metadata.api.utils.email.SendEmailutil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EmailAddressServiceImpl implements EmailAddressService {

    private static final Logger logger = LoggerFactory.getLogger(EmailAddressServiceImpl.class);

    private static final String EMAIL_ADDRESS_PREFIX = "email_address_";
    @Autowired
    private EmailAddressDao emailAddressDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 根据邮件地址code查询邮件地址信息
     *
     * @param queryRequest
     * @return
     */
    @Override
    public EmailAddress getEmailAddressByCode(EmailAddressQueryRequest queryRequest) {
        return this.emailAddressDao.getEmailAddressByCode(queryRequest.getCriteria());
    }

    /**
     * 发送邮件
     *
     * @param sendEmailRequest
     * @return
     */
    @Override
    public ResponseResult<String> sendEmail(SendEmailRequest sendEmailRequest) {
        EmailAddress emailAddress   = null;
        String emailRedis = redisTemplate.opsForValue().
                get(EMAIL_ADDRESS_PREFIX + sendEmailRequest.getEmailAddCodeRequest());
        //判断redis中是否存在此对象
        if (StringUtil.isEmpty(emailRedis)) {
            emailAddress = this.emailAddressDao.sendEmail(sendEmailRequest);
            if (null == emailAddress) {
                return ResponseResult.businessError
                        ("", "未查询到此发送方地址！", 2);
            } else {
                redisTemplate.opsForValue().set(EMAIL_ADDRESS_PREFIX + sendEmailRequest.getEmailAddCodeRequest(),
                        JSONObject.toJSON(emailAddress).toString());
                redisTemplate.expire(EMAIL_ADDRESS_PREFIX +
                        sendEmailRequest.getEmailAddCodeRequest(), 60 * 60 * 6, TimeUnit.SECONDS);
            }
        } else {
            emailAddress = JSONObject.toJavaObject(JSONObject.parseObject(emailRedis), EmailAddress.class);
        }

        sendEmailRequest.setEmailAddressRequest(emailAddress.getEmailAddress());
        sendEmailRequest.setAuthorizationCodeRequest(emailAddress.getAuthorizationCode());
        sendEmailRequest.setEmailTypeRequest(emailAddress.getEmailType());
        boolean sendEmailFlag = SendEmailutil.sendEmail(sendEmailRequest);
        logger.info("发送邮件入参：{" + JSONObject.toJSON(sendEmailRequest) + "}**************************");
        if (!sendEmailFlag) {
            return ResponseResult.businessError
                    ("", "发送邮件失败，请稍后再试！", 2);
        }
        return ResponseResult.success("发送邮件成功！");
    }
}
