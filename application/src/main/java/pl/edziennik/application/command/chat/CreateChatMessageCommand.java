package pl.edziennik.application.command.chat;

import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.domain.chat.ChatMessage;

@HandledBy(handler = CreateChatMessageCommandHandler.class)
public record CreateChatMessageCommand(

        ChatMessage chatMessage

) implements ICommand<OperationResult> {
}
