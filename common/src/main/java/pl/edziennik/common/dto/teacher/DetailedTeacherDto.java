package pl.edziennik.common.dto.teacher;

import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;

public record DetailedTeacherDto(

        TeacherId teacherId,
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
