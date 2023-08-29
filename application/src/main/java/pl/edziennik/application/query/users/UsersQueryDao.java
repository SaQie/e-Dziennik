package pl.edziennik.application.query.users;

import pl.edziennik.common.view.user.LoggedUserView;

import java.util.List;

public interface UsersQueryDao {

    List<LoggedUserView> getLoggedUsersView();

}
