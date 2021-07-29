package hillinsight.metadata.api.mappers.email;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hillinsight.metadata.api.criteria.email.EmailAddressCriteria;
import hillinsight.metadata.api.dto.email.SendEmailRequest;
import hillinsight.metadata.api.email.EmailAddress;
import hillinsight.metadata.api.mappers.email.provider.EmailAddressProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailAddressMapper  extends BaseMapper<EmailAddress> {

    @SelectProvider(type = EmailAddressProvider.class, method = "getEmailByCode")
    EmailAddress getEmailByCode(EmailAddressCriteria criteria);
    @SelectProvider(type = EmailAddressProvider.class, method = "sendEmail")
    EmailAddress sendEmail(SendEmailRequest sendEmailRequest);
}
