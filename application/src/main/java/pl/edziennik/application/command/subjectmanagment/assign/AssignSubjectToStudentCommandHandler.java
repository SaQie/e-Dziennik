package pl.edziennik.application.command.subjectmanagment.assign;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;

@Component
@AllArgsConstructor
class AssignSubjectToStudentCommandHandler implements CommandHandler<AssignSubjectToStudentCommand> {

    private final StudentSubjectCommandRepository studentSubjectCommandRepository;
    private final StudentCommandRepository studentCommandRepository;
    private final SubjectCommandRepository subjectCommandRepository;

    @Override
    public void handle(AssignSubjectToStudentCommand command) {
        Subject subject = subjectCommandRepository.getReferenceById(command.subjectId());
        Student student = studentCommandRepository.getReferenceById(command.studentId());

        StudentSubject studentSubject = StudentSubject.builder()
                .subject(subject)
                .student(student)
                .build();

        studentSubjectCommandRepository.save(studentSubject);
    }
}
