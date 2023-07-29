package pl.edziennik.infrastructure.spring.converter.id;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.common.valueobject.id.ChatId;

public class ChatIdConverter implements Converter<String, ChatId> {
    @Override
    public ChatId convert(String source) {
        return ChatId.of(source);
    }
}
