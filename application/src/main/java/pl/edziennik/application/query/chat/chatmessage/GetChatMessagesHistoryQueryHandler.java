package pl.edziennik.application.query.chat.chatmessage;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.chat.ChatMessageView;
import pl.edziennik.infrastructure.repository.chat.chatmessage.ChatMessageQueryRepository;

@Component
@AllArgsConstructor
class GetChatMessagesHistoryQueryHandler implements IQueryHandler<GetChatMessagesHistoryQuery, PageView<ChatMessageView>> {

    private final ChatMessageQueryRepository queryRepository;


    @Override
    public PageView<ChatMessageView> handle(GetChatMessagesHistoryQuery command) {
        Page<ChatMessageView> chatMessageDtoPage = queryRepository.getChatMessagesByChatId(command.pageable(), command.chatId());

        return PageView.fromPage(chatMessageDtoPage);
    }
}
