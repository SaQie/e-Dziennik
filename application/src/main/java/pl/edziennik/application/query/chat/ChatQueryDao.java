package pl.edziennik.application.query.chat;

import org.springframework.data.domain.Pageable;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.chat.ChatMessageView;

import java.util.Optional;

public interface ChatQueryDao {

    Optional<ChatId> getChatIdByRecipientAndSender(SenderId senderId, RecipientId recipientId);

    PageView<ChatMessageView> getChatMessageHistoryView(Pageable pageable, ChatId chatId);

}
