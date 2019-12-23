package org.pureacc.betcentral.infra.security.application;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.pureacc.betcentral.vocabulary.annotation.Allow;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Aspect
@Component
class SecurityAspect {
	private final SecurityControl securityControl;

	SecurityAspect(SecurityControl securityControl) {
		this.securityControl = securityControl;
	}

	@Pointcut("within(@(@org.pureacc.betcentral.vocabulary.annotation.DenyAll *) *)")
	void nestedDenyAllOnClass() {
	}

	@Pointcut("execution(* *(..))")
	void method() {
	}

	@Before("method() && nestedDenyAllOnClass()")
	public void aspect(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Allow allow = AnnotationUtils.findAnnotation(methodSignature.getMethod(), Allow.class);
		securityControl.check(allow);
	}
}
