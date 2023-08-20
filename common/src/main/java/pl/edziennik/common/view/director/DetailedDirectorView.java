package pl.edziennik.common.view.director;

import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.*;

public record DetailedDirectorView(

        DirectorId directorId,
        Username username,
        Email email,
        FullName fullName,
        Address address,
        PostalCode postalCode,
        City city,
        Pesel pesel,
        PhoneNumber phoneNumber,
        SchoolId schoolId,
        Name schoolName

) {
}
