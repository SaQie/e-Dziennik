package pl.edziennik.common.view.schoolclass;

import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.JournalNumber;
import pl.edziennik.common.valueobject.id.StudentId;

public record StudentSummaryForSchoolClassView(
        StudentId studentId,
        JournalNumber journalNumber,
        FullName fullName
) {

}
