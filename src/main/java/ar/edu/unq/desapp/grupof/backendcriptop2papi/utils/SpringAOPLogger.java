package ar.edu.unq.desapp.grupof.backendcriptop2papi.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
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
            logger.info(getPreMessage(joinPoint, className));
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

    private static String getPreMessage(final JoinPoint joinPoint, final String className) {
        var param = Arrays.stream(joinPoint.getSignature().getName().split(".")).toList();
        var paramObject = param.size() > 1 ? param.get(param.size() - 1) : "";
        final StringBuilder builder = new StringBuilder()
                .append("Entered in ").append(className).append(".")
                .append(paramObject)
                .append("(");
        appendTo(builder, joinPoint);
        return builder
                .append(")")
                .toString();
    }

    private static String getPostMessage(final JoinPoint joinPoint, final String className, final long millis) {
        return new StringBuilder()
                .append("Exit from ").append(className).append(".")
                .append(joinPoint.getSignature().getName())
                .append("(..); Execution time: ")
                .append(millis)
                .append(" ms;")
                .toString();
    }

    private static String getErrorMessage(final Throwable ex) {
        return ex.getMessage();
    }

    private static void appendTo(final StringBuilder builder, final JoinPoint joinPoint) {
        final Object[] args = joinPoint.getArgs();
        for ( int i = 0; i < args.length; i++ ) {
            if ( i != 0 ) {
                builder.append(", ");
            }
            builder.append(args[i]);
        }
    }

}