package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.common.valueobject.id.SchoolLevelId;

public class SchoolLevelIdConverter implements Converter<String, SchoolLevelId> {
    @Override
    public SchoolLevelId convert(String source) {
        return SchoolLevelId.of(source);
    }
}
