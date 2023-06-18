package pl.edziennik.common.dto.director;

import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.common.valueobject.id.SchoolId;

public record DetailedDirectorDto(

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
