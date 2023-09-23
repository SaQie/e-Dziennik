package pl.edziennik.application.command.chat.chatmessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.ChatMessageId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;
import pl.edziennik.common.valueobject.vo.ChatContent;
import pl.edziennik.common.valueobject.vo.FullName;

/**
 * A command used for saving chat message
 */
@Handler(handler = CreateChatMessageCommandHandler.class)
public record CreateChatMessageCommand(

        @JsonIgnore ChatMessageId chatMessageId,
        @Schema(example = "29b8ce56-5964-11ee-8c99-0242ac120002")
        @Valid @NotNull(message = "${field.empty}") SenderId senderId,

        @Schema(example = "29b8ce56-5964-11ee-8c99-0242ac120002")
        @Valid @NotNull(message = "${field.empty}") RecipientId recipientId,

        @Schema(example = "29b8ce56-5964-11ee-8c99-0242ac120002")
        @Valid @NotNull(message = "${field.empty}") ChatId chatId,
        @Schema(example = "Kamil Nowak")
        @Valid @NotNull(message = "${field.empty}") FullName senderName,

        @Schema(example = "Dawid Nowak")
        @Valid @NotNull(message = "${field.empty}") FullName recipientName,

        @Schema(example = "Hello !")
        @Valid @NotNull(message = "${field.empty}") ChatContent chatContent

) implements Command {

    public static final String SENDER_ID = "senderId";
    public static final String RECIPIENT_ID = "recipientId";
    public static final String CHAT_ID = "chatId";
    public static final String SENDER_NAME = "senderName";
    public static final String RECIPIENT_NAME = "recipientName";
    public static final String CHAT_CONTENT = "chatContent";
    public static final String CHAT_MESSAGE_ID = "chatMessageId";


    @JsonCreator
    public CreateChatMessageCommand(SenderId senderId, RecipientId recipientId, ChatId chatId, FullName senderName,
                                    FullName recipientName, ChatContent chatContent) {
        this(ChatMessageId.create(), senderId, recipientId, chatId, senderName, recipientName, chatContent);
    }
}
