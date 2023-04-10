package pl.edziennik.eDziennik.domain.role.dto.mapper;

import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.role.dto.RoleResponseApiDto;

public class RoleMapper {

    private RoleMapper() {
    }

    public static RoleResponseApiDto toDto(Role entity) {
        return new RoleResponseApiDto(
                entity.getId(),
                entity.getName()
        );
    }

    public static Role toEntity(String role){
        return new Role(role);
    }

    public static Role toEntity(RoleResponseApiDto dto) {
        return new Role(
                dto.id(),
                dto.name());
    }
}
