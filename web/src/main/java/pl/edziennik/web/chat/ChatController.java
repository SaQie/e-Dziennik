package pl.edziennik.web.chat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edziennik.application.command.chat.chatmessage.CreateChatMessageCommand;
import pl.edziennik.application.command.chat.chatroom.CreateChatRoomCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.chat.ChatQueryDao;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.chat.ChatMessageView;

@RestController
@RequestMapping("/api/v1/chat")
@AllArgsConstructor
@Tag(name = "Chat API")
public class ChatController {

    private final SimpMessagingTemplate template;
    private final ChatQueryDao dao;
    private final Dispatcher dispatcher;

    @Operation(summary = "Return list of messages for specific chatId",
            description = "This api endpoint returns all messages that has been written for specific chatId, result is paginated")
    @GetMapping("/history/{chatId}")
    public PageView<ChatMessageView> getChatHistory(Pageable pageable, @PathVariable ChatId chatId) {
        return dao.getChatMessageHistoryView(pageable, chatId);
    }

    @Operation(summary = "Return or create chatId for given senderId and recipientId",
            description = "This api endpoint returns a new chatId if not exist yet for the given senderId and recipientId, " +
                    "or returns existing chatId if chatId for given recipientId and senderId has been created before")
    @GetMapping("/chatroom/{recipientId}/{senderId}")
    public ChatId getChatRoomId(@PathVariable RecipientId recipientId, @PathVariable SenderId senderId) {
        return dao.getChatIdByRecipientAndSender(senderId, recipientId)
                .orElseGet(() -> {
                    // If chatRoom doesn't exist yet
                    CreateChatRoomCommand command = new CreateChatRoomCommand(senderId, recipientId);
                    dispatcher.run(command);

                    return command.chatId();
                });
    }


    @MessageMapping("/chat")
    public void sendMessage(@Payload CreateChatMessageCommand command) {
        dispatcher.run(command);

        template.convertAndSendToUser(command.chatId().id().toString(), "/messages/chat", command);
    }
}
