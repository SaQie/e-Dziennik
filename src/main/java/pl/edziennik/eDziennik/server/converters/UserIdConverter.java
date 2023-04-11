package pl.edziennik.eDziennik.server.converters;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.eDziennik.domain.user.domain.wrapper.UserId;

public class UserIdConverter implements Converter<String, UserId> {
    @Override
    public UserId convert(String source) {
        return UserId.wrap(Long.valueOf(source));
    }
}
