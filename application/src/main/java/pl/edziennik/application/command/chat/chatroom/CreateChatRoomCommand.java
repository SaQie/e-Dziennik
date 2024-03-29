package pl.edziennik.application.command.chat.chatroom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;

/**
 * A command used for creating new chat room
 */
@Handler(handler = CreateChatRoomCommandHandler.class)
public record CreateChatRoomCommand(

        @JsonIgnore ChatId chatId,

        @Schema(example = "29b8ce56-5964-11ee-8c99-0242ac120002")
        @Valid @NotNull(message = "${field.empty}") SenderId senderId,

        @Schema(example = "29b8ce56-5964-11ee-8c99-0242ac120002")
        @Valid @NotNull(message = "${field.empty}") RecipientId recipientId

) implements Command {

    public static final String RECIPIENT_ID = "recipientId";
    public static final String SENDER_ID = "senderId";
    public static final String CHAT_ID = "chatId";

    @JsonCreator
    public CreateChatRoomCommand(SenderId senderId, RecipientId recipientId) {
        this(ChatId.create(), senderId, recipientId);
    }
}
