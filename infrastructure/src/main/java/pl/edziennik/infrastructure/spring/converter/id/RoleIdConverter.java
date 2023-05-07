package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.domain.role.RoleId;

public class RoleIdConverter implements Converter<RoleId, Long> {
    @Override
    public Long convert(RoleId source) {
        return source.id();
    }


}
