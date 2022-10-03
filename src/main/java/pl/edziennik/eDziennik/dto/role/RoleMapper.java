package pl.edziennik.eDziennik.dto.role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.role.Role;
import pl.edziennik.eDziennik.server.basics.AbstractMapper;

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
