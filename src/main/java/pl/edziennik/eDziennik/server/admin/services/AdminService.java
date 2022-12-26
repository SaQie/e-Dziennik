package pl.edziennik.eDziennik.server.admin.services;

import pl.edziennik.eDziennik.server.admin.domain.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminResponseApiDto;

public interface AdminService {

    AdminResponseApiDto createNewAdminAccount(AdminRequestApiDto dto);

}
