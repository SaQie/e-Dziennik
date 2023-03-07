package pl.edziennik.eDziennik.domain.admin.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.admin.dao.AdminDao;
import pl.edziennik.eDziennik.domain.admin.domain.Admin;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.AdminResponseApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.mapper.AdminMapper;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.dto.mapper.UserMapper;
import pl.edziennik.eDziennik.domain.user.services.UserService;
import pl.edziennik.eDziennik.server.basics.dto.Page;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Page<List<AdminResponseApiDto>> getAdminList(PageRequest pageRequest) {
        return dao.findAll(pageRequest).map(AdminMapper::mapToDto);
    }

    @Override
    public AdminResponseApiDto getAdminById(Long id) {
        Admin admin = dao.get(id);
        return AdminMapper.mapToDto(admin);
    }

    @Override
    public void deleteAdminById(Long id) {
        if (dao.findAll().size() == 1) {
            throw new BusinessException("You cannot delete last admin account");
        }
        dao.remove(id);
    }

    @Override
    public AdminResponseApiDto updateAdmin(AdminRequestApiDto dto, Long id) {
        Optional<Admin> optionalStudent = dao.find(id);
        if (optionalStudent.isPresent()) {
//            validatorService.valid(requestApiDto);
            Admin admin = optionalStudent.get();
            admin.getUser().setUsername(dto.getUsername());
            admin.getUser().setEmail(dto.getEmail());
            return AdminMapper.mapToDto(admin);
        }
        return createNewAdminAccount(dto);

    }
}
