package pl.edziennik.eDziennik.server.admin.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.admin.dao.AdminDao;
import pl.edziennik.eDziennik.server.admin.domain.Admin;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminResponseApiDto;
import pl.edziennik.eDziennik.server.admin.domain.dto.mapper.AdminMapper;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.user.domain.User;
import pl.edziennik.eDziennik.server.user.domain.UserMapper;
import pl.edziennik.eDziennik.server.user.services.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
class AdminServiceImpl implements AdminService {

    private final AdminValidatorService validator;
    private final AdminDao dao;
    private final UserService userService;

    @Override
    @Transactional
    public AdminResponseApiDto createNewAdminAccount(AdminRequestApiDto dto) {
        validator.valid(dto);
        Admin admin = new Admin();
        User user = userService.createUser(UserMapper.toDto(dto));
        admin.setUser(user);
        return AdminMapper.mapToDto(dao.saveOrUpdate(admin));
    }

    @Override
    public AdminResponseApiDto getAdminByUsername(String username) {
        Admin admin = dao.getByUsername(username);
        return admin == null ? null : AdminMapper.mapToDto(admin);
    }

    @Override
    @Transactional
    public void updateAdminLastLoginDate(String username) {
        Admin admin = dao.getByUsername(username);
        admin.getUser().setLastLoginDate(LocalDateTime.now());
    }

    @Override
    public List<AdminResponseApiDto> getAdminList() {
        return dao.findAll().stream().map(AdminMapper::mapToDto).toList();
    }
}
