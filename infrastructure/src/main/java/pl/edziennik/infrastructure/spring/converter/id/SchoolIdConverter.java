package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.domain.school.SchoolId;

public class SchoolIdConverter implements Converter<String, SchoolId> {
    @Override
    public SchoolId convert(String source) {
        return SchoolId.wrap(Long.valueOf(source));
    }
}
