package hillinsight.metadata.api.server.aspects;

import focus.spring.extensions.web.aspects.ValidationAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ApiValidationAspect extends ValidationAspect {
    @Override
    @Before("execution(public * hillinsight.metadata.api.service.impl..*.*(..))")
    public void before(JoinPoint joinPoint) {
        super.before(joinPoint);
    }
}
