package pl.edziennik.web.chat;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.edziennik.application.command.chat.CreateChatMessageCommand;
import pl.edziennik.application.command.chat.CreateChatRoomCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.query.chat.GetChatMessagesHistoryQuery;
import pl.edziennik.application.query.chat.GetChatRoomQuery;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;
import pl.edziennik.domain.chat.ChatMessage;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ChatController {

    private final Dispatcher dispatcher;
    private final SimpMessagingTemplate template;


    @GetMapping("/chat/history/{chatId}")
    public List<ChatMessage> getChatHistory(@PathVariable ChatId chatId) {
        GetChatMessagesHistoryQuery getChatMessagesHistoryQuery = new GetChatMessagesHistoryQuery(chatId);
        return dispatcher.dispatch(getChatMessagesHistoryQuery);
    }

    @GetMapping("/chat/chatroom/{recipientId}/{senderId}")
    public ChatId getChatRoomId(@PathVariable RecipientId recipientId, @PathVariable SenderId senderId) {
        GetChatRoomQuery query = new GetChatRoomQuery(recipientId, senderId);

        // Jesli jeszcze chatRoom nie istnieje
        Optional<ChatId> chatIdOptional = dispatcher.dispatch(query)
                .or(() -> {
                    CreateChatRoomCommand commnad = new CreateChatRoomCommand(senderId, recipientId);
                    OperationResult operationResult = dispatcher.dispatch(commnad);

                    ChatId chatId = ChatId.of(operationResult.identifier().id());

                    return Optional.of(chatId);
                });

        return chatIdOptional.get();
    }


    @MessageMapping("/chat")
    public void sendMessage(@Payload pl.edziennik.domain.chat.ChatMessage chatMessage) {
        CreateChatMessageCommand createChatMessageCommand = new CreateChatMessageCommand(chatMessage);
        dispatcher.dispatch(createChatMessageCommand);


        template.convertAndSendToUser(chatMessage.getChatId().id().toString(), "/messages/chat", chatMessage);
    }
}
