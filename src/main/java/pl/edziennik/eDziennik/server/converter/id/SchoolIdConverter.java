package pl.edziennik.eDziennik.server.converter.id;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;

public class SchoolIdConverter implements Converter<String, SchoolId> {
    @Override
    public SchoolId convert(String source) {
        return SchoolId.wrap(Long.valueOf(source));
    }
}
