package pl.edziennik.application.query.chat.chatroom;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.infrastructure.repository.chat.chatroom.ChatRoomCommandRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
class GetChatRoomQueryHandler implements IQueryHandler<GetChatRoomQuery, Optional<ChatId>> {

    private final ChatRoomCommandRepository chatRoomCommandRepository;

    @Override
    public Optional<ChatId> handle(GetChatRoomQuery query) {
        return chatRoomCommandRepository.findChatRoomIdByRecipientIdAndSenderId(query.recipientId(), query.senderId());
    }
}
