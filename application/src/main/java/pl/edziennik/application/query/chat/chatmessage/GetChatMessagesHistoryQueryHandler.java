package pl.edziennik.application.query.chat.chatmessage;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.chat.ChatMessageDto;
import pl.edziennik.infrastructure.repository.chat.chatmessage.ChatMessageQueryRepository;

@Component
@AllArgsConstructor
class GetChatMessagesHistoryQueryHandler implements IQueryHandler<GetChatMessagesHistoryQuery, PageDto<ChatMessageDto>> {

    private final ChatMessageQueryRepository queryRepository;


    @Override
    public PageDto<ChatMessageDto> handle(GetChatMessagesHistoryQuery command) {
        Page<ChatMessageDto> chatMessageDtoPage = queryRepository.getChatMessagesByChatId(command.pageable(), command.chatId());

        return PageDto.fromPage(chatMessageDtoPage);
    }
}
