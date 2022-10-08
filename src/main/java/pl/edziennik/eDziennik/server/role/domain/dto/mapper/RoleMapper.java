package pl.edziennik.eDziennik.server.role.domain.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.basics.AbstractMapper;
import pl.edziennik.eDziennik.server.role.domain.dto.RoleResponseApiDto;

@Component
@AllArgsConstructor
public class RoleMapper implements AbstractMapper<RoleResponseApiDto, Role> {

    @Override
    public RoleResponseApiDto toDto(Role entity) {
        return new RoleResponseApiDto(
                entity.getId(),
                entity.getName()
        );
    }

    @Override
    public Role toEntity(RoleResponseApiDto dto) {
        return new Role(
                dto.getId(),
                dto.getName());
    }
}
