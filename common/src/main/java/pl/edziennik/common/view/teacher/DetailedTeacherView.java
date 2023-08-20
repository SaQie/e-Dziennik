package pl.edziennik.common.view.teacher;

import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.*;

public record DetailedTeacherView(

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
