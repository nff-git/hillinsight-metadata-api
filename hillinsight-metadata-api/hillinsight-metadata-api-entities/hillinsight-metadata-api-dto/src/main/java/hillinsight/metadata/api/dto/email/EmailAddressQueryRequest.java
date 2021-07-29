package hillinsight.metadata.api.dto.email;

import hillinsight.metadata.api.criteria.email.EmailAddressCriteria;

public class EmailAddressQueryRequest {
    private EmailAddressCriteria criteria;

    public EmailAddressCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(EmailAddressCriteria criteria) {
        this.criteria = criteria;
    }
}
