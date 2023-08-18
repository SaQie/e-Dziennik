package pl.edziennik.application.query.users;

import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.view.user.LoggedUserView;

import java.util.List;

@HandledBy(handler = GetLoggedUsersQueryHandler.class)
public record GetLoggedUsersQuery(

) implements IQuery<List<LoggedUserView>> {

}
