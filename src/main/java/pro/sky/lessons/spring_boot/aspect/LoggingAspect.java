package pro.sky.lessons.spring_boot.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import pro.sky.lessons.spring_boot.service.EmployeeServiceImpl;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = Logger.getLogger(EmployeeServiceImpl.class.getSimpleName());

    @Around("execution(* pro.sky.lessons.spring_boot.service.*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
            logger.info("start");
            var res = joinPoint.proceed();
            logger.info("end");
            return res;
    }
}
