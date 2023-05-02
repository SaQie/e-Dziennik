package pl.edziennik.eDziennik.server.converter.id;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;

public class SubjectIdConverter implements Converter<String, SubjectId> {
    @Override
    public SubjectId convert(String source) {
        return SubjectId.wrap(Long.valueOf(source));
    }
}
