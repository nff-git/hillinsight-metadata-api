package hillinsight.metadata.api.server.aspects;

import focus.spring.extensions.data.MasterSlaveDataSourceAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect extends MasterSlaveDataSourceAspect {
    @Override
    @Pointcut("execution(public * hillinsight.metadata.api.dao.impl..*.*(..))")
    public void readPoint() {

    }

    @Override
    @Pointcut("execution(public * hillinsight.metadata.api.dao.impl..*.*(..))")
    public void writePoint() {

    }
}
