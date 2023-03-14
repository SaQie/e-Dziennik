package pl.edziennik.eDziennik.domain.admin.dto.mapper;

import pl.edziennik.eDziennik.domain.admin.domain.Admin;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.AdminResponseApiDto;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.user.dto.mapper.UserMapper;

import java.util.List;

public class AdminMapper {

    private AdminMapper() {
    }


    public static Admin mapToEntity(AdminRequestApiDto dto) {
        return new Admin(UserMapper.toEntity(dto));
    }

    public static AdminResponseApiDto mapToDto(Admin entity) {
        return AdminResponseApiDto.builder()
                .id(entity.getId())
                .username(entity.getUser().getUsername())
                .email(entity.getUser().getEmail())
                .role(Role.RoleConst.ROLE_ADMIN.name())
                .userId(entity.getUser().getId())
                .build();
    }

    public static List<AdminResponseApiDto> mapToDto(List<Admin> entities) {
        return entities.stream().map(AdminMapper::mapToDto).toList();
    }
}
