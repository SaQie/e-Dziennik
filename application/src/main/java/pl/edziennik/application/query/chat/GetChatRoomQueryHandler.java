package pl.edziennik.application.query.chat;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.infrastructure.repository.chat.chatroom.ChatRoomQueryRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
class GetChatRoomQueryHandler implements IQueryHandler<GetChatRoomQuery, Optional<ChatId>> {

    private final ChatRoomQueryRepository chatRoomQueryRepository;

    @Override
    public Optional<ChatId> handle(GetChatRoomQuery query) {
        return chatRoomQueryRepository.findChatRoomIdByRecipientIdAndSenderId(query.recipientId(), query.senderId());
    }
}
