package hillinsight.metadata.api.service.email;

import focus.core.ResponseResult;
import hillinsight.metadata.api.dto.email.EmailAddressQueryRequest;
import hillinsight.metadata.api.dto.email.SendEmailRequest;
import hillinsight.metadata.api.email.EmailAddress;

public interface EmailAddressService {
    EmailAddress getEmailAddressByCode(EmailAddressQueryRequest queryRequest);


    ResponseResult<String> sendEmail(SendEmailRequest sendEmailRequest);
}
