package pl.edziennik.eDziennik.domain.admin.service;

import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.AdminResponseApiDto;

import java.util.List;

public interface AdminService {

    AdminResponseApiDto createNewAdminAccount(final AdminRequestApiDto dto);

    AdminResponseApiDto getAdminByUsername(final String username);

    void updateAdminLastLoginDate(final String username);

    List<AdminResponseApiDto> getAdminList();
}
