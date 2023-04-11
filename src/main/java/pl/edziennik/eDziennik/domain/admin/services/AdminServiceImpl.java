package pl.edziennik.eDziennik.domain.admin.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.admin.domain.Admin;
import pl.edziennik.eDziennik.domain.admin.domain.wrapper.AdminId;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.AdminResponseApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.mapper.AdminMapper;
import pl.edziennik.eDziennik.domain.admin.repository.AdminRepository;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.dto.mapper.UserMapper;
import pl.edziennik.eDziennik.domain.user.services.UserService;
import pl.edziennik.eDziennik.server.basics.page.PageDto;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
class AdminServiceImpl extends BaseService implements AdminService {

    private final AdminValidatorService validator;
    private final AdminRepository repository;
    private final UserService userService;

    @Override
    @Transactional
    public AdminResponseApiDto createNewAdminAccount(final AdminRequestApiDto dto) {
        validator.valid(dto);
        Admin admin = new Admin();
        User user = userService.createUser(UserMapper.toDto(dto));
        admin.setUser(user);
        return AdminMapper.mapToDto(repository.save(admin));
    }

    @Override
    public AdminResponseApiDto getAdminByUsername(final String username) {
        Admin admin = repository.getAdminByUsername(username);
        return admin == null ? null : AdminMapper.mapToDto(admin);
    }

    @Override
    @Transactional
    public void updateAdminLastLoginDate(final String username) {
        Admin admin = repository.getAdminByUsername(username);
        admin.getUser().setLastLoginDate(LocalDateTime.now());
    }

    @Override
    public PageDto<AdminResponseApiDto> getAdminList(final Pageable pageRequest) {
        Page<AdminResponseApiDto> page = repository.findAll(pageRequest).map(AdminMapper::mapToDto);
        return PageDto.fromPage(page);
    }

    @Override
    public AdminResponseApiDto getAdminById(final AdminId adminId) {
        Admin admin = repository.findById(adminId.id())
                .orElseThrow(notFoundException(adminId.id(), Admin.class));
        return AdminMapper.mapToDto(admin);
    }

    @Override
    public void deleteAdminById(final AdminId adminId) {
        if (repository.findAll().size() == 1) {
            throw new BusinessException(getMessage("cannot.delete.last.admin.account"));
        }
        repository.deleteById(adminId.id());
    }

    @Override
    public AdminResponseApiDto updateAdmin(final AdminRequestApiDto dto, final AdminId adminId) {
        Optional<Admin> optionalStudent = repository.findById(adminId.id());
        if (optionalStudent.isPresent()) {
            validator.valid(dto);
            Admin admin = optionalStudent.get();

            // update user data
            User user = admin.getUser();
            userService.updateUser(user.getUserId(), UserMapper.toDto(dto));

            return AdminMapper.mapToDto(admin);
        }
        return createNewAdminAccount(dto);

    }
}
