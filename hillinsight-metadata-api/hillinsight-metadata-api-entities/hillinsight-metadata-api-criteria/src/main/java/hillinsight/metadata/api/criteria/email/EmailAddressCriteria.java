package hillinsight.metadata.api.criteria.email;

import focus.core.BaseCriteria;
import focus.core.annotations.Criteria;
import focus.core.annotations.CriteriaColumn;
import focus.core.annotations.DbType;
import hillinsight.metadata.api.email.EmailAddress;

import java.util.List;

@Criteria(dbType = DbType.SqlServer)
public class EmailAddressCriteria extends BaseCriteria {
    @CriteriaColumn(entityType = EmailAddress.class, jdbcType = CriteriaColumn.JdbcType.NVARCHAR)
    private String emailAddressCode;

    public String getEmailAddressCode() {
        return emailAddressCode;
    }

    public void setEmailAddressCode(String emailAddressCode) {
        this.emailAddressCode = emailAddressCode;
    }
}
