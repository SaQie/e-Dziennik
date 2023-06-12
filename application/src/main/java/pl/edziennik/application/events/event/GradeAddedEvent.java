package pl.edziennik.application.events.event;

import pl.edziennik.common.valueobject.id.StudentSubjectId;

public record GradeAddedEvent(

        StudentSubjectId studentSubjectId

) {

    public static final String STUDENT_SUBJECT_ID = "studentSubjectId";

}
