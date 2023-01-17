package pl.edziennik.eDziennik.server.admin.domain.dto.mapper;

import pl.edziennik.eDziennik.server.admin.domain.Admin;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminResponseApiDto;
import pl.edziennik.eDziennik.server.user.domain.UserMapper;

public class AdminMapper {

    private AdminMapper() {
    }


    public static Admin mapToEntity(AdminRequestApiDto dto){
        return new Admin(UserMapper.toEntity(dto));
    }

    public static AdminResponseApiDto mapToDto(Admin entity) {
        return new AdminResponseApiDto(entity.getId(), entity.getUser().getUsername(), entity.getUser().getEmail());
    }
}
