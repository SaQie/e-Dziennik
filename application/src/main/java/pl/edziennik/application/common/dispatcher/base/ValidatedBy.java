package pl.edziennik.application.common.dispatcher.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines Validator that runs for specific Command/Query
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ValidatedBy {

    Class<? extends IBaseValidator<?>> validator();
}
