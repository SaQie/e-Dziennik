package pl.edziennik.common.view.student;

import pl.edziennik.common.valueobject.id.*;
import pl.edziennik.common.valueobject.vo.*;

import java.io.Serializable;

public record DetailedStudentView(

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

) implements Serializable {

}
