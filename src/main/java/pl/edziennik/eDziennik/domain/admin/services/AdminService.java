package pl.edziennik.eDziennik.domain.admin.services;

import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.AdminResponseApiDto;
import pl.edziennik.eDziennik.server.basics.dto.Page;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;

import java.util.List;

public interface AdminService {

    AdminResponseApiDto createNewAdminAccount(final AdminRequestApiDto dto);

    AdminResponseApiDto getAdminByUsername(final String username);

    void updateAdminLastLoginDate(final String username);

    Page<List<AdminResponseApiDto>> getAdminList(PageRequest pageRequest);

    AdminResponseApiDto getAdminById(Long id);

    void deleteAdminById(Long id);

    AdminResponseApiDto updateAdmin(AdminRequestApiDto dto, Long id);
}
