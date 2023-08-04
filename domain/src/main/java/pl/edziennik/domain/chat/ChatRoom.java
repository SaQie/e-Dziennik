package pl.edziennik.domain.chat;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.ChatRoomId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ChatRoom {

    @EmbeddedId
    private ChatRoomId chatRoomId = ChatRoomId.create();

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


    @Builder
    public static ChatRoom of(SenderId senderId, RecipientId recipientId, ChatId chatId) {
        ChatRoom chatRoom = new ChatRoom();

        chatRoom.senderId = senderId;
        chatRoom.recipientId = recipientId;
        chatRoom.chatId = chatId;

        return chatRoom;
    }

}
