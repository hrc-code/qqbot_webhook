package com.hrc.bot.aspect;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Slf4j
@Component
@Aspect
public class ParamAspect {

    @Resource
    HttpServletRequest request;

    @Pointcut("execution(* com.hrc.bot.controller..*.*(..))")
    public void controller() {
    }

    @Before("controller()")
    public void before(JoinPoint joinPoint) {
        StringJoiner sj = new StringJoiner("\n", "\n", "\n");
        for (Object arg : joinPoint.getArgs()) {
            sj.add(arg.toString());
        }
        log.info("请求uri:{} \n 请求参数：{}", request.getRequestURI(), sj);
    }
}
