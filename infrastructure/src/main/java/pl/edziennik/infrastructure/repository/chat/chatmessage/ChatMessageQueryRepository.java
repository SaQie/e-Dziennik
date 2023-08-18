package pl.edziennik.infrastructure.repository.chat.chatmessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.view.chat.ChatMessageView;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.ChatMessageId;
import pl.edziennik.domain.chat.ChatMessage;

@RepositoryDefinition(domainClass = ChatMessage.class, idClass = ChatMessageId.class)
public interface ChatMessageQueryRepository {

    @Query("SELECT NEW pl.edziennik.common.view.chat.ChatMessageView(cm.senderId,cm.recipientId,cm.chatId,cm.senderName, cm.recipientName, cm.chatContent) " +
            "FROM ChatMessage cm WHERE cm.chatId = :chatId ORDER BY cm.date desc ")
    Page<ChatMessageView> getChatMessagesByChatId(Pageable pageable, ChatId chatId);

}
