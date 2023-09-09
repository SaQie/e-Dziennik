package pl.edziennik.application.common.dispatcher;

public class EmptyValidator implements Validator<Command>{

    @Override
    public void validate(Command command, ValidationErrorBuilder errorBuilder) {

    }
}
