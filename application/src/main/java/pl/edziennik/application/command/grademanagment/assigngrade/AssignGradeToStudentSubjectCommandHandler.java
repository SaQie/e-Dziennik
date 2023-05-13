package pl.edziennik.application.command.grademanagment.assigngrade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.repositories.grade.GradeCommandRepository;
import pl.edziennik.infrastructure.repositories.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.repositories.teacher.TeacherCommandRepository;

@Component
@AllArgsConstructor
class AssignGradeToStudentSubjectCommandHandler implements ICommandHandler<AssignGradeToStudentSubjectCommand, OperationResult> {

    private final StudentSubjectCommandRepository studentSubjectCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;
    private final GradeCommandRepository gradeCommandRepository;

    @Override
    public OperationResult handle(AssignGradeToStudentSubjectCommand command) {
        StudentSubject studentSubject = studentSubjectCommandRepository.getReferenceByStudentStudentIdAndSubjectSubjectId(command.studentId(), command.subjectId());
        Teacher teacher = teacherCommandRepository.getReferenceById(command.teacherId());

        Grade grade = Grade.of(command.grade(), command.weight(), command.description(), studentSubject, teacher);

        gradeCommandRepository.save(grade);

        return OperationResult.success();
    }
}
