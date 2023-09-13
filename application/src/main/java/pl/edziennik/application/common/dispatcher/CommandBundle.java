package pl.edziennik.application.common.dispatcher;

public record CommandBundle<T extends Command>(
        CommandHandler<T> handler,
        Validator<T> validator
) {

}
