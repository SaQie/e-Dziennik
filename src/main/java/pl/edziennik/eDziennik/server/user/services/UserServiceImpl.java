package pl.edziennik.eDziennik.server.user.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.address.Address;
import pl.edziennik.eDziennik.server.address.AddressMapper;
import pl.edziennik.eDziennik.server.basics.ServiceValidator;
import pl.edziennik.eDziennik.server.personinformation.PersonInformation;
import pl.edziennik.eDziennik.server.personinformation.PersonInformationMapper;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.user.domain.dto.UserRequestDto;
import pl.edziennik.eDziennik.server.user.services.validator.UserValidators;
import pl.edziennik.eDziennik.server.user.dao.UserDao;
import pl.edziennik.eDziennik.server.user.domain.User;
import pl.edziennik.eDziennik.server.user.domain.UserMapper;

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
        setUserInformation(user, dto);
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


    private void setUserInformation(User user, UserRequestDto dto) {
        if (!dto.getRole().equals(Role.RoleConst.ROLE_ADMIN.name())) {
            // I have to do it because administrator not have address, person information etc.
            PersonInformation personInformation = PersonInformationMapper.mapToPersonInformation(dto.getFirstName(), dto.getLastName(), dto.getPesel());
            Address address = AddressMapper.mapToAddress(dto.getAddress(), dto.getCity(), dto.getPostalCode());
            user.setAddress(address);
            user.setPersonInformation(personInformation);
        }
    }
}
