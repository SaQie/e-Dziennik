package pl.edziennik.common.dto.student;

import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.UserId;

public record StudentDto(

        StudentId studentId,
        UserId userId,
        Username username,
        Email email,
        FullName fullName,
        Address address,
        PostalCode postalCode,
        City city,
        Pesel pesel,
        PhoneNumber phoneNumber,
        ParentId parentId,
        FullName parentFullName

) {

}
