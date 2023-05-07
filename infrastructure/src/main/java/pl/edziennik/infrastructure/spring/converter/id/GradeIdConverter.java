package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.domain.grade.GradeId;

public class GradeIdConverter implements Converter<String, GradeId> {
    @Override
    public GradeId convert(String source) {
        return GradeId.wrap(Long.valueOf(source));
    }
}
