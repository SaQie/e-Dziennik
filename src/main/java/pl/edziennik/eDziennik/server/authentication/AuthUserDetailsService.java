package pl.edziennik.eDziennik.server.authentication;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.repository.UserRepository;
import pl.edziennik.eDziennik.server.exception.EntityNotFoundException;


@Service
@AllArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getByUsername(username);
        if (user != null){
            return new AuthUserDetails(user);
        }
        throw new EntityNotFoundException("User with name " + username + " could not be found.");
    }
}
