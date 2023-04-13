package pl.edziennik.eDziennik.server.converters.ids;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.eDziennik.domain.grade.domain.wrapper.GradeId;

public class GradeIdConverter implements Converter<String, GradeId> {
    @Override
    public GradeId convert(String source) {
        return GradeId.wrap(Long.valueOf(source));
    }
}
