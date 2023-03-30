package pl.edziennik.eDziennik.domain.user.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.role.repository.RoleRepository;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.dto.UserRequestDto;
import pl.edziennik.eDziennik.domain.user.dto.mapper.UserMapper;
import pl.edziennik.eDziennik.domain.user.repository.UserRepository;
import pl.edziennik.eDziennik.domain.user.services.validator.UserValidators;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
class UserServiceImpl extends ServiceValidator<UserValidators, UserRequestDto> implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(UserRequestDto dto) {
        runValidators(dto);
        User user = UserMapper.toEntity(dto);
        Role role = roleRepository.findByName(dto.getRole())
                .orElse(roleRepository.getReferenceById(Role.RoleConst.ROLE_STUDENT.getId()));
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return repository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, UserRequestDto dto) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            runValidators(dto);
            User user = userOptional.get();
            user.setUsername(dto.getUsername());
            user.setEmail(dto.getEmail());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            Role role = roleRepository.findByName(dto.getRole())
                    .orElse(roleRepository.getReferenceById(Role.RoleConst.ROLE_STUDENT.getId()));
            user.setRole(role);
        }
    }

    @Override
    public void updateUserLastLoginDate(String username) {
        User user = repository.getByUsername(username);
        if (user != null) {
            user.setLastLoginDate(LocalDateTime.now());
            repository.save(user);
        }
    }


}
