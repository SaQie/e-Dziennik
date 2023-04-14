package pl.edziennik.eDziennik.domain.user.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.role.domain.wrapper.RoleId;
import pl.edziennik.eDziennik.domain.role.repository.RoleRepository;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.domain.wrapper.UserId;
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
    public User createUser(final UserRequestDto dto) {
        runValidators(dto);
        User user = UserMapper.toEntity(dto);
        Role role = roleRepository.findByName(dto.role())
                .orElse(roleRepository.getReferenceById(RoleId.wrap(Role.RoleConst.ROLE_STUDENT.getId())));
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(dto.password()));
        return repository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(final UserId userId, final UserRequestDto dto) {
        Optional<User> userOptional = repository.findById(userId);
        if (userOptional.isPresent()) {
            runValidators(dto);
            User user = userOptional.get();
            user.setUsername(dto.username());
            user.setEmail(dto.email());
            user.setPassword(passwordEncoder.encode(dto.password()));
            Role role = roleRepository.findByName(dto.role())
                    .orElse(roleRepository.getReferenceById(RoleId.wrap(Role.RoleConst.ROLE_STUDENT.getId())));
            user.setRole(role);
        }
    }

    @Override
    public void updateUserLastLoginDate(final String username) {
        User user = repository.getByUsername(username);
        if (user != null) {
            user.setLastLoginDate(LocalDateTime.now());
            repository.save(user);
        }
    }


}
