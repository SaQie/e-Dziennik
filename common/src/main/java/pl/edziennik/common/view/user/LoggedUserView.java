package pl.edziennik.common.view.user;

import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.UserId;

public record LoggedUserView(

        UserId userId,
        Name username,
        FullName fullName,
        Name roleName

) {

}
