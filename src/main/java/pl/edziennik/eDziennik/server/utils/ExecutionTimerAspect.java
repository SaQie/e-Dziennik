package pl.edziennik.eDziennik.server.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimerAspect {

    /**
     * This method allows to show method execution time
     */
    @Around("@annotation(pl.edziennik.eDziennik.server.utils.ExecutionTimer)")
    public Object runTimer(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;
        log.info("Class Name: "+ joinPoint.getSignature().getDeclaringTypeName() +". Method Name: "+ joinPoint.getSignature().getName() + ". Time taken for Execution is : " + resultTime +"ms");
        return object;
    }

}
