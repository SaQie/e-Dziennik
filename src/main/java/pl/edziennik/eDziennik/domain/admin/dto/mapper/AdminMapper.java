package pl.edziennik.eDziennik.domain.admin.dto.mapper;

import pl.edziennik.eDziennik.domain.admin.domain.Admin;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.AdminResponseApiDto;
import pl.edziennik.eDziennik.domain.user.dto.mapper.UserMapper;

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
