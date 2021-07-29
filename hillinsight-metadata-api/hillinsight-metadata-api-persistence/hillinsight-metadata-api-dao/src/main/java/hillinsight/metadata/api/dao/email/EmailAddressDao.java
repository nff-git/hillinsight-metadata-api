package hillinsight.metadata.api.dao.email;

import hillinsight.metadata.api.criteria.email.EmailAddressCriteria;
import hillinsight.metadata.api.dto.email.SendEmailRequest;
import hillinsight.metadata.api.email.EmailAddress;

public interface EmailAddressDao {

    EmailAddress getEmailAddressByCode(EmailAddressCriteria criteria);

    EmailAddress sendEmail(SendEmailRequest sendEmailRequest);
}
