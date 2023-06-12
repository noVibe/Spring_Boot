package pro.sky.lessons.spring_boot.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* pro.sky.lessons.spring_boot.service.*.*.*(..))")
    public Object service(ProceedingJoinPoint joinPoint) throws Throwable {
        String classAndMethod = joinPoint.getSourceLocation().getWithinType().getSimpleName() + "." + joinPoint.getSignature().getName();
        var res = joinPoint.proceed();
        log.info("Proceeded {}: {}", classAndMethod, res);
        return res;
    }

    @AfterReturning(value = "execution(* pro.sky.lessons.spring_boot.repository.*.*.*(..))", returning = "ret")
    public void repository(JoinPoint jp, Object ret) {
        log.debug(jp.toShortString() + ": " + ret);
    }

    @AfterThrowing(pointcut = "execution(* *.*.*(..))", throwing = "e")
    public static void exception(JoinPoint jp, Throwable e) {
        log.error(jp.toShortString() + Arrays.toString(jp.getArgs()), e);
    }
}
