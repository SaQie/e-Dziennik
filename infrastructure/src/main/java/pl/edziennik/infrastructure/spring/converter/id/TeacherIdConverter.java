package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.common.valueobject.id.TeacherId;

public class TeacherIdConverter implements Converter<String, TeacherId> {
    @Override
    public TeacherId convert(String source) {
        return TeacherId.of(source);
    }
}
