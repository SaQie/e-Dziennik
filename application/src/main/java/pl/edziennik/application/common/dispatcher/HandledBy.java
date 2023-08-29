package pl.edziennik.application.common.dispatcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines Handler that runs for specific Command/Query
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HandledBy {

    Class<? extends IBaseHandler<? extends IDispatchable<?>, ?>> handler();

}
