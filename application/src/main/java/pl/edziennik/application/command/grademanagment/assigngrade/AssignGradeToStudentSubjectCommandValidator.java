package pl.edziennik.application.command.grademanagment.assigngrade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class AssignGradeToStudentSubjectCommandValidator implements IBaseValidator<AssignGradeToStudentSubjectCommand> {

    public static final String MESSAGE_KEY_STUDENT_SUBJECT_NOT_EXISTS = "student.subject.not.exists";

    private final StudentSubjectCommandRepository studentSubjectCommandRepository;
    private final StudentCommandRepository studentCommandRepository;
    private final SubjectCommandRepository subjectCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;

    @Override
    public void validate(AssignGradeToStudentSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        studentCommandRepository.findById(command.studentId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignGradeToStudentSubjectCommand.STUDENT_ID);
                    return null;
                });

        subjectCommandRepository.findById(command.subjectId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignGradeToStudentSubjectCommand.SUBJECT_ID);
                    return null;
                });

        teacherCommandRepository.findById(command.teacherId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignGradeToStudentSubjectCommand.TEACHER_ID);
                    return null;
                });

        errorBuilder.flush();

        if (!studentSubjectCommandRepository.isStudentAlreadyAssignedToSubject(command.studentId(), command.subjectId())) {
            errorBuilder.addError(
                    AssignGradeToStudentSubjectCommand.SUBJECT_ID,
                    MESSAGE_KEY_STUDENT_SUBJECT_NOT_EXISTS,
                    ErrorCode.NOT_ASSIGNED_TO_SUBJECT
            );
        }

    }
}
