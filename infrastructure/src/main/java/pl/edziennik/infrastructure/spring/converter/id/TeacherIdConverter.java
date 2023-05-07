package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.domain.teacher.TeacherId;

public class TeacherIdConverter implements Converter<String, TeacherId> {
    @Override
    public TeacherId convert(String source) {
        return TeacherId.wrap(Long.valueOf(source));
    }
}
