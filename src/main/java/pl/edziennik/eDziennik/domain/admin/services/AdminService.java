package pl.edziennik.eDziennik.domain.admin.services;

import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.AdminResponseApiDto;

import java.util.List;

public interface AdminService {

    AdminResponseApiDto createNewAdminAccount(final AdminRequestApiDto dto);

    AdminResponseApiDto getAdminByUsername(final String username);

    void updateAdminLastLoginDate(final String username);

    List<AdminResponseApiDto> getAdminList();

    AdminResponseApiDto getAdminById(Long id);

    void deleteAdminById(Long id);

    AdminResponseApiDto updateAdmin(AdminRequestApiDto dto, Long id);
}
