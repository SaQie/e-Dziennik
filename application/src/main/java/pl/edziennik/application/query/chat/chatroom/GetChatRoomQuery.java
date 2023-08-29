package pl.edziennik.application.query.chat.chatroom;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.IQuery;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;

import java.util.Optional;

/**
 * Query used for getting chat room id by recipient and sender id
 * <br>
 * Return: Optional<ChatRoomId>
 */
@HandledBy(handler = GetChatRoomQueryHandler.class)
public record GetChatRoomQuery(

        @NotNull(message = "{field.empty}") RecipientId recipientId,
        @NotNull(message = "{field.empty}") SenderId senderId

) implements IQuery<Optional<ChatId>> {

    public static final String RECIPIENT_ID = "recipientId";
    public static final String SENDER_ID = "senderId";

}
