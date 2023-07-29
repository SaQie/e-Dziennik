package pl.edziennik.infrastructure.repository.chat.chatmessage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.ChatMessageId;
import pl.edziennik.domain.chat.ChatMessage;

import java.util.List;

@RepositoryDefinition(domainClass = ChatMessage.class, idClass = ChatMessageId.class)
public interface ChatMessageQueryRepository {

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.chatId = :chatId")
    List<ChatMessage> getChatMessagesByChatId(ChatId chatId);

}
