package pl.edziennik.application.command.subjectmanagment.assigntostudent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class AssignSubjectToStudentCommandValidator implements IBaseValidator<AssignSubjectToStudentCommand> {

    public static final String MESSAGE_KEY_SUBJECT_IS_FROM_ANOTHER_SCHOOL_CLASS = "student.assign.subject.from.another.class";
    public static final String MESSAGE_KEY_STUDENT_STUDENT_ALREADY_ASSIGNED_TO_SUBJECT = "student.subject.already.exists";

    private final SubjectCommandRepository subjectCommandRepository;
    private final StudentCommandRepository studentCommandRepository;
    private final StudentSubjectCommandRepository studentSubjectCommandRepository;

    @Override
    public void validate(AssignSubjectToStudentCommand command, ValidationErrorBuilder errorBuilder) {
        subjectCommandRepository.findById(command.subjectId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignSubjectToStudentCommand.SUBJECT_ID);
                    return null;
                });

        studentCommandRepository.findById(command.studentId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignSubjectToStudentCommand.STUDENT_ID);
                    return null;
                });

        errorBuilder.flush();

        if (!subjectCommandRepository.isStudentFromTheSameSchoolClass(command.studentId(), command.subjectId())) {
            errorBuilder.addError(
                    AssignSubjectToStudentCommand.STUDENT_ID,
                    MESSAGE_KEY_SUBJECT_IS_FROM_ANOTHER_SCHOOL_CLASS,
                    ErrorCode.ANOTHER_SCHOOL
            );
        }

        if (studentSubjectCommandRepository.isStudentAlreadyAssignedToSubject(command.studentId(), command.subjectId())) {
            errorBuilder.addError(
                    AssignSubjectToStudentCommand.SUBJECT_ID,
                    MESSAGE_KEY_STUDENT_STUDENT_ALREADY_ASSIGNED_TO_SUBJECT,
                    ErrorCode.OBJECT_ALREADY_EXISTS
            );
        }
    }
}
