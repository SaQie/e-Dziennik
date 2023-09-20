package pl.edziennik.web.query.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edziennik.application.query.users.UsersQueryDao;
import pl.edziennik.common.view.user.LoggedUserView;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User API")
public class UserQueryController {

    private final UsersQueryDao dao;


    @Operation(summary = "Returns list of logged users")
    @GetMapping("/logged-users")
    public List<LoggedUserView> getLoggedUsers() {
        return dao.getLoggedUsersView();
    }

}
