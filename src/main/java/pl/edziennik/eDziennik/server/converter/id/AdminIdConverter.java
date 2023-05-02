package pl.edziennik.eDziennik.server.converter.id;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.eDziennik.domain.admin.domain.wrapper.AdminId;

public class AdminIdConverter implements Converter<String, AdminId> {
    @Override
    public AdminId convert(String source) {
        return AdminId.wrap(Long.valueOf(source));
    }
}
