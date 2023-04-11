package pl.edziennik.eDziennik.server.converters;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;

public class TeacherIdConverter implements Converter<String, TeacherId> {
    @Override
    public TeacherId convert(String source) {
        return TeacherId.wrap(Long.valueOf(source));
    }
}
