package org.pureacc.betcentral.infra.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Allow {
	enum Role {
		UNAUTHENTICATED,
		AUTHENTICATED
	}

	Allow.Role[] value();
}
