package hillinsight.metadata.api.server.configurations;

import hillinsight.metadata.api.utils.convention.StaticVar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@Configuration
public class TransactionConfig implements TransactionManagementConfigurer {

    @Autowired
    private DataSource dataSource;

    @Override
    @Bean(name = StaticVar.BeanNames.TRANSACTION_MANAGER)
    public TransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(this.dataSource);
    }
}
