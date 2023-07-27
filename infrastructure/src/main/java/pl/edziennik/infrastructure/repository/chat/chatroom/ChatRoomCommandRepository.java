package pl.edziennik.infrastructure.repository.chat.chatroom;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.ChatRoomId;
import pl.edziennik.domain.chat.ChatRoom;

@RepositoryDefinition(domainClass = ChatRoom.class, idClass = ChatRoomId.class)
public interface ChatRoomCommandRepository {

    ChatRoom save(ChatRoom chatRoom);

}
