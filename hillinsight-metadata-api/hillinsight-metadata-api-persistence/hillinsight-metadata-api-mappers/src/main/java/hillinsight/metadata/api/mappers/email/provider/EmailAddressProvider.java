package hillinsight.metadata.api.mappers.email.provider;

import focus.mybatis.extensions.CriteriaToWhereBuilder;
import hillinsight.metadata.api.criteria.email.EmailAddressCriteria;
import hillinsight.metadata.api.dto.email.SendEmailRequest;
import org.apache.ibatis.jdbc.SQL;

public class   EmailAddressProvider {

    public String getEmailByCode(EmailAddressCriteria criteria) {
        return new SQL() {{
            SELECT("id, email_address, email_address_code, create_time, delete_flag");
            FROM("t_email_address");
            CriteriaToWhereBuilder.buildWhereSql(criteria, this::WHERE);
            WHERE("delete_flag=1");
        }}.toString();
    }

    public String sendEmail(SendEmailRequest sendEmailRequest) {
        return new SQL() {{
            SELECT("id, email_address, email_address_code, create_time, delete_flag,email_type,authorization_code");
            FROM("t_email_address");
            WHERE("delete_flag=1 and email_address_code="+sendEmailRequest.getEmailAddCodeRequest());
        }}.toString();
    }
}
