package hillinsight.metadata.api.server.configurations;


import focus.core.utils.ObjectUtil;
import focus.servlet.extensions.BufferedRequestFilter;
import focus.spring.extensions.CustomAcceptHeaderLocaleResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class ObjectConfig {
    @Bean
    public FilterRegistrationBean bufferedRequestStream() {
        return ObjectUtil.initObject(new FilterRegistrationBean())
                .init(v -> v.setFilter(new BufferedRequestFilter()))
                .init(v -> v.setOrder(0))
                .getObject();
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new CustomAcceptHeaderLocaleResolver("lang");
    }
}
