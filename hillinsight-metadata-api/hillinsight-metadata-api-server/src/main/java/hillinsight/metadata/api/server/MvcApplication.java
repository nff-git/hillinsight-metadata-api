package hillinsight.metadata.api.server;

import focus.api.infrastructure.starter.annotations.EnableApiErrorLogs;
import focus.api.infrastructure.starter.annotations.EnableServerInfo;
import focus.api.messaging.starter.annotations.EnableSubscriberInfo;
import focus.apiclient.starter.EnableApiClient;
import focus.core.starter.annotations.SystemLogger;
import focus.spring.extensions.starter.annotations.AutoSetupCloudConfig;
import focus.spring.extensions.web.WebMvcExceptionHandler;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = {"focus", "hillinsight"}, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebMvcExceptionHandler.class)})
@EnableTransactionManagement
@AutoSetupCloudConfig
@SystemLogger(name = "business")
@EnableServerInfo
@EnableCaching
@EnableApiErrorLogs
@EnableDiscoveryClient
@SpringBootApplication
@EnableSubscriberInfo
@EnableApiClient
public class MvcApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(MvcApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
