package pl.edziennik.application.query.chat.chatmessage;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.IQuery;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.chat.ChatMessageView;
import pl.edziennik.common.valueobject.id.ChatId;

/**
 * A query used for getting chat history for specific chatId
 * <br>
 * <b>Return DTO {@link ChatMessageView}</b>
 */
@HandledBy(handler = GetChatMessagesHistoryQueryHandler.class)
public record GetChatMessagesHistoryQuery(

        @Valid @NotNull(message = "${field.empty}") ChatId chatId,
        Pageable pageable

) implements IQuery<PageView<ChatMessageView>> {

    public static final String CHAT_ID = "chatId";

}
