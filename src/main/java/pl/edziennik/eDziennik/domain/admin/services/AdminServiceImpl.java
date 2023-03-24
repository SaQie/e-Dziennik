package pl.edziennik.eDziennik.domain.admin.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.admin.domain.Admin;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.AdminResponseApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.mapper.AdminMapper;
import pl.edziennik.eDziennik.domain.admin.repository.AdminRepository;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.dto.mapper.UserMapper;
import pl.edziennik.eDziennik.domain.user.services.UserService;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;

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
    public AdminResponseApiDto createNewAdminAccount(AdminRequestApiDto dto) {
        validator.valid(dto);
        Admin admin = new Admin();
        User user = userService.createUser(UserMapper.toDto(dto));
        admin.setUser(user);
        return AdminMapper.mapToDto(repository.save(admin));
    }

    @Override
    public AdminResponseApiDto getAdminByUsername(String username) {
        Admin admin = repository.getAdminByUsername(username);
        return admin == null ? null : AdminMapper.mapToDto(admin);
    }

    @Override
    @Transactional
    public void updateAdminLastLoginDate(String username) {
        Admin admin = repository.getAdminByUsername(username);
        admin.getUser().setLastLoginDate(LocalDateTime.now());
    }

    @Override
    public Page<AdminResponseApiDto> getAdminList(Pageable pageRequest) {
        return repository.findAll(pageRequest).map(AdminMapper::mapToDto);
    }

    @Override
    public AdminResponseApiDto getAdminById(Long id) {
        Admin admin = repository.findById(id)
                .orElseThrow(notFoundException(id, Admin.class));
        return AdminMapper.mapToDto(admin);
    }

    @Override
    public void deleteAdminById(Long id) {
        if (repository.findAll().size() == 1) {
            throw new BusinessException(getMessage("cannot.delete.last.admin.account"));
        }
        repository.deleteById(id);
    }

    @Override
    public AdminResponseApiDto updateAdmin(AdminRequestApiDto dto, Long id) {
        Optional<Admin> optionalStudent = repository.findById(id);
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
