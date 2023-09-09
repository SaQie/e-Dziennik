package pl.edziennik.application.common.dispatcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {

    Class<? extends CommandHandler<? extends Command>> handler();

    Class<? extends Validator<? extends Command>> validator() default EmptyValidator.class;

}
