package pl.edziennik.web.query.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.users.GetLoggedUsersQuery;
import pl.edziennik.common.dto.user.LoggedUserDto;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserQueryController {

    private final Dispatcher dispatcher;


    @GetMapping("/logged-users")
    public List<LoggedUserDto> getLoggedUsers() {
        GetLoggedUsersQuery getLoggedUsersQuery = new GetLoggedUsersQuery();

        return dispatcher.dispatch(getLoggedUsersQuery);
    }

}
