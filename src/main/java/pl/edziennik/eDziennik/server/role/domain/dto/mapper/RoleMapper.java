package pl.edziennik.eDziennik.server.role.domain.dto.mapper;

import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.role.domain.dto.RoleResponseApiDto;

public class RoleMapper {

    private RoleMapper() {
    }

    public static RoleResponseApiDto toDto(Role entity) {
        return new RoleResponseApiDto(
                entity.getId(),
                entity.getName()
        );
    }

    public static Role toEntity(RoleResponseApiDto dto) {
        return new Role(
                dto.getId(),
                dto.getName());
    }
}
