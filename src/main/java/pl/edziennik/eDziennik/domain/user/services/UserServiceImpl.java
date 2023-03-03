package pl.edziennik.eDziennik.domain.user.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.address.domain.Address;
import pl.edziennik.eDziennik.domain.address.dto.mapper.AddressMapper;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.personinformation.dto.mapper.PersonInformationMapper;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.user.dto.UserRequestDto;
import pl.edziennik.eDziennik.domain.user.services.validator.UserValidators;
import pl.edziennik.eDziennik.domain.user.dao.UserDao;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.dto.mapper.UserMapper;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
class UserServiceImpl extends ServiceValidator<UserValidators, UserRequestDto> implements UserService {

    private final UserDao dao;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(UserRequestDto dto) {
        valid(dto);
        User user = UserMapper.toEntity(dto);
        user.setRole(basicValidator.checkRoleExistOrReturnDefault(dto.getRole()));
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return dao.saveOrUpdate(user);
    }


    @Override
    public void updateUserLastLoginDate(String username) {
        User user = dao.getByUsername(username);
        if (user != null) {
            user.setLastLoginDate(LocalDateTime.now());
            dao.saveOrUpdate(user);
        }
    }

    @Override
    protected void valid(UserRequestDto dto) {
        runValidatorChain(dto);
    }


}
