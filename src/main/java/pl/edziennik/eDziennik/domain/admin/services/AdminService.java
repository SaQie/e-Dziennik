package pl.edziennik.eDziennik.domain.admin.services;

import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.admin.domain.wrapper.AdminId;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.AdminResponseApiDto;
import pl.edziennik.eDziennik.server.basics.page.PageDto;

public interface AdminService {

    AdminResponseApiDto createNewAdminAccount(final AdminRequestApiDto dto);

    AdminResponseApiDto getAdminByUsername(final String username);

    void updateAdminLastLoginDate(final String username);

    PageDto<AdminResponseApiDto> getAdminList(final Pageable pageable);

    AdminResponseApiDto getAdminById(final AdminId adminId);

    void deleteAdminById(final AdminId adminId);

    AdminResponseApiDto updateAdmin(final AdminRequestApiDto dto, final AdminId adminId);
}
