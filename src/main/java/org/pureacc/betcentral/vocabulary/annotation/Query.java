package org.pureacc.betcentral.vocabulary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.pureacc.betcentral.vocabulary.annotation.DenyAll;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DenyAll
@Transactional(readOnly = true)
@Validated
@Component
public @interface Query {
}
