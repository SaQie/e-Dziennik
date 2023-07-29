package pl.edziennik.application.query.users;

import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.user.LoggedUserDto;

import java.util.List;

@HandledBy(handler = GetLoggedUsersQueryHandler.class)
public record GetLoggedUsersQuery(

) implements IQuery<List<LoggedUserDto>> {

}
