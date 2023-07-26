package pl.edziennik.common.chat;

public record ChatMessage(

        String content,
        String sender,
        ChatMessageType messageType

) {
}
