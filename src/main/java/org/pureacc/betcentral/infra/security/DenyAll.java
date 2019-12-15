package org.pureacc.betcentral.infra.security;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DenyAll {
}
