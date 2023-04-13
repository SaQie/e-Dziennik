package pl.edziennik.eDziennik.server.converters.ids;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.eDziennik.domain.schoollevel.domain.wrapper.SchoolLevelId;

public class SchoolLevelIdConverter implements Converter<String, SchoolLevelId> {
    @Override
    public SchoolLevelId convert(String source) {
        return SchoolLevelId.wrap(Long.valueOf(source));
    }
}
