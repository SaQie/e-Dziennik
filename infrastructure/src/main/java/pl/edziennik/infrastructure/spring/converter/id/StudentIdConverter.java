package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.domain.student.StudentId;

public class StudentIdConverter implements Converter<String, StudentId> {
    @Override
    public StudentId convert(String source) {
        return StudentId.wrap(Long.valueOf(source));
    }
}
