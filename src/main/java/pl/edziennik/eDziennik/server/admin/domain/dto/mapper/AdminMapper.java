package pl.edziennik.eDziennik.server.admin.domain.dto.mapper;

import pl.edziennik.eDziennik.server.admin.domain.Admin;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminResponseApiDto;

public class AdminMapper {

    private AdminMapper() {
    }


    public static Admin mapToEntity(AdminRequestApiDto dto){
        return new Admin(dto.getUsername(), dto.getEmail(), dto.getPassword());
    }

    public static AdminResponseApiDto mapToDto(Admin entity) {
        return new AdminResponseApiDto(entity.getId(), entity.getUsername(), entity.getEmail());
    }
}
