package ar.edu.unq.desapp.grupof.backendcriptop2papi.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;

@Aspect
@Component
public class SpringAOPLogger {

    @Around(
            "execution(* ar.edu.unq.desapp.grupof.backendcriptop2papi.service.*.*(..)) || " +
                    "execution(* ar.edu.unq.desapp.grupof.backendcriptop2papi.client.*.*(..)) ||" +
                    "execution(* ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.*.*(..)) ||" +
                    "execution(* ar.edu.unq.desapp.grupof.backendcriptop2papi.webservice.*.*(..))"
    )
    public Object logMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        final Class<?> targetClass = joinPoint.getTarget().getClass();
        final Logger logger = getLogger(targetClass);
        try {
            final String className = targetClass.getSimpleName();
            final StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            final Object retVal = joinPoint.proceed();
            stopWatch.stop();
            logger.info(getPostMessage(joinPoint, className, stopWatch.getTotalTimeMillis()));
            return retVal;
        } catch ( final Throwable ex ) {
            logger.error(getErrorMessage(ex), ex);
            throw ex;
        }
    }

    private static String getPostMessage(final JoinPoint joinPoint, final String className, final long millis) {
        var authentication =
                SecurityContextHolder.getContext().getAuthentication() != null ?
                SecurityContextHolder.getContext().getAuthentication().getName() : "";
        var params =
                Arrays.stream(joinPoint.getArgs())
                        .map(a -> a.toString())
                        .map(a -> {
                            var b = a.replace(".", "/").split("/");
                            return b.length > 1 ? Arrays.stream(b).toList().get(b.length - 1): "";
                        }).toList();
        return new StringBuilder()
                .append(authentication)
                .append(" ; ")
                .append(joinPoint.getSignature().getName())
                .append("(")
                .append(params)
                .append(")")
                .append(" ; ")
                .append("Execution time: ")
                .append(millis)
                .append(" ms.")
                .toString();
    }

    private static String getErrorMessage(final Throwable ex) {
        return ex.getMessage();
    }

}