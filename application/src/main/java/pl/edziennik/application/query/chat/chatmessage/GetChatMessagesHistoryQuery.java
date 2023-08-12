package pl.edziennik.application.query.chat.chatmessage;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.chat.ChatMessageDto;
import pl.edziennik.common.valueobject.id.ChatId;

/**
 * A query used for getting chat history for specific chatId
 * <br>
 * <b>Return DTO {@link ChatMessageDto}</b>
 */
@HandledBy(handler = GetChatMessagesHistoryQueryHandler.class)
public record GetChatMessagesHistoryQuery(

        @Valid @NotNull(message = "${field.empty}") ChatId chatId,
        Pageable pageable

) implements IQuery<PageDto<ChatMessageDto>> {

    public static final String CHAT_ID = "chatId";

}
