package org.pureacc.betcentral.vocabulary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DenyAll
@Transactional
@Validated
@Component
public @interface Command {
}
