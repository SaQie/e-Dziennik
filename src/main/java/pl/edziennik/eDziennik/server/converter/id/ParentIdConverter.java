package pl.edziennik.eDziennik.server.converter.id;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.eDziennik.domain.parent.domain.wrapper.ParentId;

public class ParentIdConverter implements Converter<String, ParentId> {
    @Override
    public ParentId convert(String source) {
        return ParentId.wrap(Long.valueOf(source));
    }
}
