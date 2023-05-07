package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.domain.schoolclass.SchoolClassId;

public class SchoolClassIdConverter implements Converter<String, SchoolClassId> {
    @Override
    public SchoolClassId convert(String source) {
        return SchoolClassId.wrap(Long.valueOf(source));
    }

}
