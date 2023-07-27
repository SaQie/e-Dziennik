package pl.edziennik.infrastructure.repository.chat.chatmessage;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.ChatMessageId;
import pl.edziennik.domain.chat.ChatMessage;

@RepositoryDefinition(domainClass = ChatMessage.class, idClass = ChatMessageId.class)
public interface ChatMessageCommandRepository {

    ChatMessage save(ChatMessage chatMessage);

}
