package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.common.valueobject.id.ParentId;

public class ParentIdConverter implements Converter<String, ParentId> {
    @Override
    public ParentId convert(String source) {
        return ParentId.of(source);
    }
}
