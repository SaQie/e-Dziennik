package pl.edziennik.infrastructure.spring.converter.id;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.domain.admin.AdminId;

public class AdminIdConverter implements Converter<String, AdminId> {
    @Override
    public AdminId convert(String source) {
        return AdminId.wrap(Long.valueOf(source));
    }
}
