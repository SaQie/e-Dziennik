package pl.edziennik.application.common.dispatcher.newapi;

import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.application.common.dispatcher.Validator;

public record CommandBundle<T extends Command>(
        CommandHandler<T> handler,
        Validator<T> validator
) {

}
