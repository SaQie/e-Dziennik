package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.domain.subject.SubjectId;

public class SubjectIdConverter implements Converter<String, SubjectId> {
    @Override
    public SubjectId convert(String source) {
        return SubjectId.wrap(Long.valueOf(source));
    }
}
