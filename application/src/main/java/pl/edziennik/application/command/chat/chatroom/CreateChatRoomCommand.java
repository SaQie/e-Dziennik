package pl.edziennik.application.command.chat.chatroom;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;

/**
 * A command used for creating new chat room
 */
@HandledBy(handler = CreateChatRoomCommandHandler.class)
public record CreateChatRoomCommand(

        @Valid @NotNull(message = "${field.empty}") SenderId senderId,
        @Valid @NotNull(message = "${field.empty}") RecipientId recipientId

) implements ICommand<OperationResult> {

    public static final String RECIPIENT_ID = "recipientId";
    public static final String SENDER_ID = "senderId";

}
