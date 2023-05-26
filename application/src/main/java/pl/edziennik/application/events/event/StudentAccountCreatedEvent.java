package pl.edziennik.application.events.event;

import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.StudentId;

public record StudentAccountCreatedEvent(
        StudentId studentId,
        SchoolClassId schoolClassId
) {
}
