package pl.edziennik.common.view.student;

import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.JournalNumber;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.UserId;

import java.io.Serializable;

public record StudentSummaryView(

        StudentId studentId,
        UserId userId,
        JournalNumber journalNumber,
        Username username,
        FullName fullName,
        SchoolId schoolId,
        Name schoolName

) implements Serializable {

}
