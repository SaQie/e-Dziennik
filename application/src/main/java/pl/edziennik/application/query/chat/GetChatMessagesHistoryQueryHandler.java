package pl.edziennik.application.query.chat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.domain.chat.ChatMessage;
import pl.edziennik.infrastructure.repository.chat.chatmessage.ChatMessageQueryRepository;

import java.util.List;

@Component
@AllArgsConstructor
class GetChatMessagesHistoryQueryHandler implements IQueryHandler<GetChatMessagesHistoryQuery, List<ChatMessage>> {

    private final ChatMessageQueryRepository queryRepository;


    @Override
    public List<ChatMessage> handle(GetChatMessagesHistoryQuery command) {
        return queryRepository.getChatMessagesByChatId(command.chatId());
    }
}
