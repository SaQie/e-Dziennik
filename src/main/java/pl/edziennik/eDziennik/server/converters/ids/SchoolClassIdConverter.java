package pl.edziennik.eDziennik.server.converters.ids;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;

public class SchoolClassIdConverter implements Converter<String, SchoolClassId> {
    @Override
    public SchoolClassId convert(String source) {
        return SchoolClassId.wrap(Long.valueOf(source));
    }

}
