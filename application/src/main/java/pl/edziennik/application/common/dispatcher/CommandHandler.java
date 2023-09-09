package pl.edziennik.application.common.dispatcher;

public interface CommandHandler<T extends Command> {

    void handle(T command);

}
