package pl.edziennik.application.common.dispatcher;

public interface Validator<T extends Command> {

    void validate(T command, ValidationErrorBuilder errorBuilder);

}
