package pl.edziennik.infrastructure.spring.websocket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import pl.edziennik.common.chat.ChatMessage;
import pl.edziennik.common.chat.ChatMessageType;

@Component
@AllArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageSendingOperations;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null) {
            log.info(event.getUser().getName() + " disconnected !");
            log.info("User {} disconnected", username);

            ChatMessage chatMessage = new ChatMessage(null, username, ChatMessageType.LEAVE);

            messageSendingOperations.convertAndSend(chatMessage);
        }

    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event){
        System.out.println("xx");
    }

}
