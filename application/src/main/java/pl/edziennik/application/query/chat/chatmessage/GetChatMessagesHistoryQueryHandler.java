package pl.edziennik.application.query.chat.chatmessage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.chat.ChatMessageDto;
import pl.edziennik.infrastructure.repository.chat.chatmessage.ChatMessageQueryRepository;

import java.util.List;

@Component
@AllArgsConstructor
class GetChatMessagesHistoryQueryHandler implements IQueryHandler<GetChatMessagesHistoryQuery, List<ChatMessageDto>> {

    private final ChatMessageQueryRepository queryRepository;


    @Override
    public List<ChatMessageDto> handle(GetChatMessagesHistoryQuery command) {
        return queryRepository.getChatMessagesByChatId(command.chatId());
    }
}
