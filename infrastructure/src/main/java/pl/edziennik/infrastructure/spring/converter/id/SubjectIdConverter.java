package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.common.valueobject.id.SubjectId;

public class SubjectIdConverter implements Converter<String, SubjectId> {
    @Override
    public SubjectId convert(String source) {
        return SubjectId.of(source);
    }
}
