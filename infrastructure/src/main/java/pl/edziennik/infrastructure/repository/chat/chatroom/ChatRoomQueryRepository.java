package pl.edziennik.infrastructure.repository.chat.chatroom;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.ChatRoomId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;
import pl.edziennik.domain.chat.ChatRoom;

import java.util.Optional;

@RepositoryDefinition(domainClass = ChatRoom.class, idClass = ChatRoomId.class)
public interface ChatRoomQueryRepository {

    @Query("SELECT cr.chatId FROM ChatRoom cr " +
            " WHERE cr.recipientId = :recipientId " +
            " AND cr.senderId = :senderId ")
    Optional<ChatId> findChatRoomIdByRecipientIdAndSenderId(RecipientId recipientId, SenderId senderId);

}
