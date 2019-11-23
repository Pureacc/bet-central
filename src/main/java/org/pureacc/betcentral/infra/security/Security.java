package org.pureacc.betcentral.infra.security;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Security {
	enum Require {
		AUTHENTICATED
	}

	Require[] value() default Require.AUTHENTICATED;

	Require[] drop() default {};
}
