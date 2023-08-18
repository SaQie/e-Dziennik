package pl.edziennik.web.chat;

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
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.query.chat.chatmessage.GetChatMessagesHistoryQuery;
import pl.edziennik.application.query.chat.chatroom.GetChatRoomQuery;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.chat.ChatMessageView;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;

@RestController
@RequestMapping("/api/v1/chat")
@AllArgsConstructor
public class ChatController {

    private final Dispatcher dispatcher;
    private final SimpMessagingTemplate template;

    @GetMapping("/history/{chatId}")
    public PageView<ChatMessageView> getChatHistory(Pageable pageable, @PathVariable ChatId chatId) {
        GetChatMessagesHistoryQuery getChatMessagesHistoryQuery = new GetChatMessagesHistoryQuery(chatId, pageable);

        return dispatcher.dispatch(getChatMessagesHistoryQuery);
    }

    @GetMapping("/chatroom/{recipientId}/{senderId}")
    public ChatId getChatRoomId(@PathVariable RecipientId recipientId, @PathVariable SenderId senderId) {
        GetChatRoomQuery query = new GetChatRoomQuery(recipientId, senderId);

        return dispatcher.dispatch(query)
                .orElseGet(() -> {
                    // If chatRoom doesn't exist yet
                    CreateChatRoomCommand command = new CreateChatRoomCommand(senderId, recipientId);
                    OperationResult operationResult = dispatcher.dispatch(command);

                    return ChatId.of(operationResult.identifier().id());
                });
    }


    @MessageMapping("/chat")
    public void sendMessage(@Payload CreateChatMessageCommand command) {
        dispatcher.dispatch(command);

        template.convertAndSendToUser(command.chatId().id().toString(), "/messages/chat", command);
    }
}
