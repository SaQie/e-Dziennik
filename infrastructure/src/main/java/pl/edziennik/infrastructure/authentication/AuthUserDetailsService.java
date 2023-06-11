package pl.edziennik.infrastructure.authentication;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.user.UserQueryRepository;


@Service
@AllArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UserQueryRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getByUsername(Username.of(username));
        if (user != null) {
            return new AuthUserDetails(user);
        }
        throw new EntityNotFoundException("User with name " + username + " could not be found.");
    }
}
