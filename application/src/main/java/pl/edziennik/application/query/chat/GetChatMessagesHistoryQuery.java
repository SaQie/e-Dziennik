package pl.edziennik.application.query.chat;

import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.domain.chat.ChatMessage;

import java.util.List;

@HandledBy(handler = GetChatMessagesHistoryQueryHandler.class)
public record GetChatMessagesHistoryQuery(

        ChatId chatId

) implements IQuery<List<ChatMessage>> {
}
