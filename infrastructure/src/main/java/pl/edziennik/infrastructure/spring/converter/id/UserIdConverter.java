package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.domain.user.UserId;

public class UserIdConverter implements Converter<String, UserId> {
    @Override
    public UserId convert(String source) {
        return UserId.wrap(Long.valueOf(source));
    }
}
