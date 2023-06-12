package pl.edziennik.application.command.grademanagment.assigngrade;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.application.events.event.GradeAddedEvent;
import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.repository.grade.GradeCommandRepository;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;

@Component
@AllArgsConstructor
class AssignGradeToStudentSubjectCommandHandler implements ICommandHandler<AssignGradeToStudentSubjectCommand, OperationResult> {

    private final StudentSubjectCommandRepository studentSubjectCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;
    private final GradeCommandRepository gradeCommandRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public OperationResult handle(AssignGradeToStudentSubjectCommand command) {
        StudentSubject studentSubject = studentSubjectCommandRepository.getReferenceByStudentStudentIdAndSubjectSubjectId(command.studentId(), command.subjectId());
        Teacher teacher = teacherCommandRepository.getReferenceById(command.teacherId());

        Grade grade = Grade.of(command.grade(), command.weight(), command.description(), studentSubject, teacher);

        GradeId gradeId = gradeCommandRepository.save(grade).getGradeId();

        eventPublisher.publishEvent(new GradeAddedEvent(studentSubject.getStudentSubjectId()));

        return OperationResult.success(gradeId);
    }
}
