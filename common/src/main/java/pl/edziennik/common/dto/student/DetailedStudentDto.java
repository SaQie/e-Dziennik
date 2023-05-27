package pl.edziennik.common.dto.student;

import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.*;

public record DetailedStudentDto(

        StudentId studentId,
        UserId userId,
        JournalNumber journalNumber,
        Username username,
        Email email,
        FullName fullName,
        Address address,
        PostalCode postalCode,
        City city,
        Pesel pesel,
        PhoneNumber phoneNumber,
        ParentId parentId,
        FullName parentFullName,
        SchoolId schoolId,
        Name schoolName,
        SchoolClassId schoolClassId,
        Name schoolClassIdName

) {

}
