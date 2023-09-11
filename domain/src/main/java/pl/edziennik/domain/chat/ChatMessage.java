package pl.edziennik.domain.chat;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.ChatMessageId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;
import pl.edziennik.common.valueobject.vo.ChatContent;
import pl.edziennik.common.valueobject.vo.FullName;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ChatMessage {

    @EmbeddedId
    private ChatMessageId chatMessageId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "chat_id", nullable = false))
    })
    private ChatId chatId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "sender_id", nullable = false))
    })
    private SenderId senderId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "recipient_id", nullable = false))
    })
    private RecipientId recipientId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "sender_name", nullable = false))
    })
    private FullName senderName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "recipient_name", nullable = false))
    })
    private FullName recipientName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "content", nullable = false))
    })
    private ChatContent chatContent;

    @Column(nullable = false)
    private LocalDateTime date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_status_id", referencedColumnName = "id", nullable = false)
    private MessageStatus messageStatus;

    @Version
    private Long version;

    @Builder
    public static ChatMessage of(ChatMessageId chatMessageId, ChatId chatId, SenderId senderId, RecipientId recipientId, FullName senderName,
                                 FullName recipientName, ChatContent chatContent, MessageStatus messageStatus) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.chatMessageId = chatMessageId;
        chatMessage.chatId = chatId;
        chatMessage.chatContent = chatContent;
        chatMessage.senderName = senderName;
        chatMessage.senderId = senderId;
        chatMessage.recipientName = recipientName;
        chatMessage.recipientId = recipientId;
        chatMessage.messageStatus = messageStatus;
        chatMessage.date = LocalDateTime.now();

        return chatMessage;

    }

}
