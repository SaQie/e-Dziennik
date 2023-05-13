package pl.edziennik.application.command.subjectmanagment.assigntostudent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.infrastructure.repositories.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repositories.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.repositories.subject.SubjectCommandRepository;

@Component
@AllArgsConstructor
class AssignSubjectToStudentCommandHandler implements ICommandHandler<AssignSubjectToStudentCommand, OperationResult> {

    private final StudentSubjectCommandRepository studentSubjectCommandRepository;
    private final StudentCommandRepository studentCommandRepository;
    private final SubjectCommandRepository subjectCommandRepository;

    @Override
    public OperationResult handle(AssignSubjectToStudentCommand command) {
        Subject subject = subjectCommandRepository.getReferenceById(command.subjectId());
        Student student = studentCommandRepository.getReferenceById(command.studentId());

        StudentSubject studentSubject = StudentSubject.of(student, subject);

        studentSubjectCommandRepository.save(studentSubject);

        return OperationResult.success();
    }
}