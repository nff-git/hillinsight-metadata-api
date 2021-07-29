package hillinsight.metadata.api.server.configurations;


import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import focus.core.exceptions.SystemException;
import hillinsight.metadata.api.utils.convention.StaticVar;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"hillinsight.metadata.api.mappers"}, sqlSessionTemplateRef = StaticVar.BeanNames.SQL_SESSION_TEMPLATE)
public class MybatisConfig {

    @Bean(name = StaticVar.BeanNames.SQL_SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();

        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setPlugins(new Interceptor[]{interceptor});

        try {
            return sqlSessionFactoryBean.getObject();
        } catch (Throwable ce) {
            throw new SystemException("Init mybatis session error", ce);
        }
    }

    @Bean(name = StaticVar.BeanNames.SQL_SESSION_TEMPLATE)
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier(StaticVar.BeanNames.SQL_SESSION_FACTORY) SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
