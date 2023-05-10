package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.common.valueobject.id.StudentId;

public class StudentIdConverter implements Converter<String, StudentId> {
    @Override
    public StudentId convert(String source) {
        return StudentId.of(source);
    }
}
