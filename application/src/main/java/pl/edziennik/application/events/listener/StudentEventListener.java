package pl.edziennik.application.events.listener;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import pl.edziennik.application.events.event.StudentAccountCreatedEvent;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;

import java.util.List;

@Component
@AllArgsConstructor
public class StudentEventListener {

    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final StudentSubjectCommandRepository studentSubjectCommandRepository;
    private final StudentCommandRepository studentCommandRepository;

    @TransactionalEventListener(classes = StudentAccountCreatedEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT)
    public void onStudentCreatedEvent(StudentAccountCreatedEvent event) {
        SchoolClass schoolClass = schoolClassCommandRepository.getBySchoolClassIdWithSubjects(event.schoolClassId());

        if (!Boolean.TRUE.equals(schoolClass.getSchoolClassConfiguration().getAutoAssignSubjects())) {
            // check,the school class configuration allows assigning student subjects automatically
            return;
        }

        List<Subject> subjects = schoolClass.getSubjects();
        Student student = studentCommandRepository.getByStudentId(event.studentId());

        for (Subject subject : subjects) {
            StudentSubject studentSubject = StudentSubject.of(student, subject);
            studentSubjectCommandRepository.save(studentSubject);
        }
    }

}
