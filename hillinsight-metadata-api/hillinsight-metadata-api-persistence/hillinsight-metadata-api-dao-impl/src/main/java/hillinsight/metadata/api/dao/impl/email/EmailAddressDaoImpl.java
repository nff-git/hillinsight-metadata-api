package hillinsight.metadata.api.dao.impl.email;

import hillinsight.metadata.api.criteria.email.EmailAddressCriteria;
import hillinsight.metadata.api.dao.email.EmailAddressDao;
import hillinsight.metadata.api.dto.email.SendEmailRequest;
import hillinsight.metadata.api.email.EmailAddress;
import hillinsight.metadata.api.mappers.email.EmailAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmailAddressDaoImpl implements EmailAddressDao {

    @Autowired
    private EmailAddressMapper mapper;

    @Override
    public EmailAddress getEmailAddressByCode(EmailAddressCriteria criteria) {
        return mapper.getEmailByCode(criteria);
    }

    @Override
    public EmailAddress sendEmail(SendEmailRequest sendEmailRequest) {
        return mapper.sendEmail(sendEmailRequest);
    }
}
