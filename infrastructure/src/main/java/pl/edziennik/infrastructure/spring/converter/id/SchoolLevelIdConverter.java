package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.domain.schoollevel.SchoolLevelId;

public class SchoolLevelIdConverter implements Converter<String, SchoolLevelId> {
    @Override
    public SchoolLevelId convert(String source) {
        return SchoolLevelId.wrap(Long.valueOf(source));
    }
}
