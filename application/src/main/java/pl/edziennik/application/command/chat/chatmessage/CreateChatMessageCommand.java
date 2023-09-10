package pl.edziennik.application.command.chat.chatmessage;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;
import pl.edziennik.common.valueobject.vo.ChatContent;
import pl.edziennik.common.valueobject.vo.FullName;

/**
 * A command used for saving chat message
 */
@Handler(handler = CreateChatMessageCommandHandler.class)
public record CreateChatMessageCommand(

        @Valid @NotNull(message = "${field.empty}") SenderId senderId,
        @Valid @NotNull(message = "${field.empty}") RecipientId recipientId,
        @Valid @NotNull(message = "${field.empty}") ChatId chatId,
        @Valid @NotNull(message = "${field.empty}") FullName senderName,
        @Valid @NotNull(message = "${field.empty}") FullName recipientName,
        @Valid @NotNull(message = "${field.empty}") ChatContent chatContent

) implements Command {

    public static final String SENDER_ID = "senderId";
    public static final String RECIPIENT_ID = "recipientId";
    public static final String CHAT_ID = "chatId";
    public static final String SENDER_NAME = "senderName";
    public static final String RECIPIENT_NAME = "recipientName";
    public static final String CHAT_CONTENT = "chatContent";


}
