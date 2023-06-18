package pl.edziennik.infrastructure.spring.converter.id;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.common.valueobject.id.DirectorId;

public class DirectorIdConverter implements Converter<String, DirectorId> {
    @Override
    public DirectorId convert(String source) {
        return DirectorId.of(source);
    }
}
