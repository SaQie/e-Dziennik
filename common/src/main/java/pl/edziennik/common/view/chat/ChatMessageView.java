package pl.edziennik.common.view.chat;

import pl.edziennik.common.valueobject.ChatContent;
import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;

public record ChatMessageView(

        SenderId senderId,
        RecipientId recipientId,
        ChatId chatId,
        FullName senderName,
        FullName receipientName,
        ChatContent chatContent

) {
}
