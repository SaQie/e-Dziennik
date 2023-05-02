package pl.edziennik.eDziennik.server.converter.id;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;

public class StudentIdConverter implements Converter<String, StudentId> {
    @Override
    public StudentId convert(String source) {
        return StudentId.wrap(Long.valueOf(source));
    }
}
