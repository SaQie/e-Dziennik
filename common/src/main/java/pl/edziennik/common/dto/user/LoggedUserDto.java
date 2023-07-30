package pl.edziennik.common.dto.user;

import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.UserId;

public record LoggedUserDto(

        UserId userId,
        Name username,
        FullName fullName,
        Name roleName

) {

}
