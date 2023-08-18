package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.valueobject.ChatContent;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;
import pl.edziennik.common.view.chat.ChatMessageView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class ChatQueryProjectionTest extends BaseIntegrationTest {

    @Test
    public void shouldReturnChatMessagesView() {
        // given
        ChatContent chatContent = ChatContent.of("Testowy content message");
        ChatId chatId = createChatMessage(RecipientId.create(), SenderId.create(), chatContent);

        // when
        Page<ChatMessageView> chatMessagesByChatId = chatMessageQueryRepository.getChatMessagesByChatId(Pageable.unpaged(), chatId);

        // then
        assertNotNull(chatMessagesByChatId);
        List<ChatMessageView> chatMessageViews = chatMessagesByChatId.getContent();
        assertEquals(chatMessageViews.size(), 1);
        assertEquals(chatMessageViews.get(0).chatId(), chatId);
        assertEquals(chatMessageViews.get(0).senderName().value(), "Test");
        assertEquals(chatMessageViews.get(0).receipientName().value(), "Test");
        assertEquals(chatMessageViews.get(0).chatContent(), chatContent);
    }

}
