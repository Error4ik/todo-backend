package com.backend.todo.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Alexey Voronin.
 * @since 18.01.2023.
 */
@Aspect
@Component
public class CommonPointcuts {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void isControllerLayer() {
    }
}
