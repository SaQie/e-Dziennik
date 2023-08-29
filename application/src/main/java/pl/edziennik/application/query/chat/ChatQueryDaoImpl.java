package pl.edziennik.application.query.chat;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.chat.ChatMessageView;
import pl.edziennik.infrastructure.repository.chat.chatmessage.ChatMessageQueryRepository;
import pl.edziennik.infrastructure.repository.chat.chatroom.ChatRoomCommandRepository;

import java.util.Optional;

@Repository
@AllArgsConstructor
class ChatQueryDaoImpl implements ChatQueryDao {

    private final ChatMessageQueryRepository queryRepository;
    private final ChatRoomCommandRepository chatRoomCommandRepository;

    @Override
    public Optional<ChatId> getChatIdByRecipientAndSender(SenderId senderId, RecipientId recipientId) {
        return chatRoomCommandRepository.findChatRoomIdByRecipientIdAndSenderId(recipientId, senderId);
    }

    @Override
    public PageView<ChatMessageView> getChatMessageHistoryView(Pageable pageable, ChatId chatId) {
        return PageView.fromPage(queryRepository.getChatMessagesByChatId(pageable, chatId));
    }
}
