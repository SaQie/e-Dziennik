package pl.edziennik.eDziennik.server.converter.id;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.eDziennik.domain.role.domain.wrapper.RoleId;

public class RoleIdConverter implements Converter<RoleId, Long> {
    @Override
    public Long convert(RoleId source) {
        return source.id();
    }


}
