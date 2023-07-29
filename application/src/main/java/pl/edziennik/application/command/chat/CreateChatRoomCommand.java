package pl.edziennik.application.command.chat;

import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;

@HandledBy(handler = CreateChatRoomCommandHandler.class)
public record CreateChatRoomCommand(

        SenderId senderId,
        RecipientId recipientId

) implements ICommand<OperationResult> {

    public static final String RECIPIENT_ID = "recipientId";
    public static final String SENDER_ID = "senderId";

}
