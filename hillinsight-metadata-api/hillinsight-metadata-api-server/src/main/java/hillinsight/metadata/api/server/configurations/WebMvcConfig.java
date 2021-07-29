package hillinsight.metadata.api.server.configurations;

import focus.spring.extensions.web.interceptors.MethodInfoInterceptor;
import focus.spring.extensions.web.interceptors.RequestBagInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 方法信息拦截器
        registry.addInterceptor(new MethodInfoInterceptor());

        // RequestBag 拦截器
        registry.addInterceptor(new RequestBagInterceptor());

        super.addInterceptors(registry);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
    }
}
