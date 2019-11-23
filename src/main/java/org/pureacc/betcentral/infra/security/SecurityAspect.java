package org.pureacc.betcentral.infra.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
class SecurityAspect {
	private final SecurityControl securityControl;

	SecurityAspect(SecurityControl securityControl) {
		this.securityControl = securityControl;
	}

	@Pointcut("execution(public * *(..))")
	void method() {}

	@Pointcut("@within(security)")
	void securedClass(Security security) {}

	@Pointcut("!within(@Security *)")
	void unsecuredClass() {}

	@Pointcut("@annotation(security)")
	void securedMethod(Security security) {}

	@Pointcut("!@annotation(Security)")
	void unsecuredMethod() {}

	@Before("method() && securedClass(security) && unsecuredMethod()")
	public void unsecuredMethodInSecuredClass(JoinPoint joinPoint, Security security) {
		handle(joinPoint, security);
	}

	@Before("method() && unsecuredClass() && securedMethod(security)")
	public void securedMethodInUnsecuredClass(JoinPoint joinPoint, Security security) {
		handle(joinPoint, security);
	}

	@Before("method() && securedClass(securityClass) && securedMethod(securityMethod)")
	public void securedMethodInSecuredClass(JoinPoint joinPoint, Security securityClass, Security securityMethod) {
		handle(joinPoint, securityClass, securityMethod);
	}

	private void handle(JoinPoint joinPoint, Security... securities) {
		securityControl.check(securities);
	}
}
