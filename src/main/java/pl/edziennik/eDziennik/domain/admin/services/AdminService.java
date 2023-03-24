package pl.edziennik.eDziennik.domain.admin.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.AdminResponseApiDto;

public interface AdminService {

    AdminResponseApiDto createNewAdminAccount(final AdminRequestApiDto dto);

    AdminResponseApiDto getAdminByUsername(final String username);

    void updateAdminLastLoginDate(final String username);

    Page<AdminResponseApiDto> getAdminList(Pageable pageable);

    AdminResponseApiDto getAdminById(Long id);

    void deleteAdminById(Long id);

    AdminResponseApiDto updateAdmin(AdminRequestApiDto dto, Long id);
}
