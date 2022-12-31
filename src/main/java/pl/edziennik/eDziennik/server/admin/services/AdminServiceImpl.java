package pl.edziennik.eDziennik.server.admin.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.admin.dao.AdminDao;
import pl.edziennik.eDziennik.server.admin.domain.Admin;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminResponseApiDto;
import pl.edziennik.eDziennik.server.admin.domain.dto.mapper.AdminMapper;

@Service
@AllArgsConstructor
class AdminServiceImpl implements AdminService{

    private final AdminValidatorService validator;
    private final AdminDao dao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminResponseApiDto createNewAdminAccount(AdminRequestApiDto dto) {
        Admin admin = validator.validateDtoAndMapToEntity(dto);
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        return AdminMapper.mapToDto(dao.saveOrUpdate(admin));
    }

    @Override
    public AdminResponseApiDto getAdminByUsername(String username) {
        Admin admin = dao.getByUsername(username);
        return admin == null ? null : AdminMapper.mapToDto(admin);
    }
}
