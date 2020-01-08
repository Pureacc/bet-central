package org.pureacc.betcentral.infra.exceptionhandling;

import javax.validation.ConstraintViolationException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.pureacc.betcentral.domain.DomainException;
import org.pureacc.betcentral.vocabulary.exception.SystemException;
import org.pureacc.betcentral.vocabulary.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

// TODO: translate ConstraintViolationException
@Aspect
@Order(0)
class UseCaseExceptionAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(UseCaseExceptionAspect.class);

	private final ErrorTranslator errorTranslator;

	UseCaseExceptionAspect(ErrorTranslator errorTranslator) {
		this.errorTranslator = errorTranslator;
	}

	@Pointcut("@within(org.pureacc.betcentral.vocabulary.annotation.Command)")
	public void command() {
	}

	@Pointcut("@within(org.pureacc.betcentral.vocabulary.annotation.Query)")
	public void query() {
	}

	@Pointcut("execution(* *(..))")
	void method() {
	}

	@Around("(command() || query()) && method()")
	public Object useCase(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			return joinPoint.proceed();
		} catch (DomainException domainException) {
			String message = errorTranslator.translate(domainException);
			throw new UserException(message);
		} catch (ConstraintViolationException constraintViolationException) {
			throw new UserException(constraintViolationException.getMessage());
		} catch (SystemException systemException) {
			String message = errorTranslator.translate("error.generic");
			LOGGER.error(message, systemException);
			throw new UserException(message);
		}
	}
}
