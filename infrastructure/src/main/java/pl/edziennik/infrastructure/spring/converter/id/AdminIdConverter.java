package pl.edziennik.infrastructure.spring.converter.id;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.common.valueobject.id.AdminId;

public class AdminIdConverter implements Converter<String, AdminId> {
    @Override
    public AdminId convert(String source) {
        return AdminId.of(source);
    }
}
