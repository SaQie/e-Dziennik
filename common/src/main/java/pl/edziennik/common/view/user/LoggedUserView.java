package pl.edziennik.common.view.user;

import pl.edziennik.common.valueobject.vo.FullName;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.common.valueobject.id.UserId;

public record LoggedUserView(

        UserId userId,
        Name username,
        FullName fullName,
        Name roleName

) {

}
