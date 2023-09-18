package pl.edziennik.application.command.subjectmanagment.assign;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.Validator;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class AssignSubjectToStudentCommandValidator implements Validator<AssignSubjectToStudentCommand> {

    public static final String MESSAGE_KEY_SUBJECT_IS_FROM_ANOTHER_SCHOOL_CLASS = "student.assign.subject.from.another.class";
    public static final String MESSAGE_KEY_STUDENT_STUDENT_ALREADY_ASSIGNED_TO_SUBJECT = "student.subject.already.exists";
    public static final String MESSAGE_KEY_ACCOUNT_INACTIVE = "account.inactive";

    private final SubjectCommandRepository subjectCommandRepository;
    private final StudentCommandRepository studentCommandRepository;
    private final StudentSubjectCommandRepository studentSubjectCommandRepository;

    @Override
    public void validate(AssignSubjectToStudentCommand command, ValidationErrorBuilder errorBuilder) {
        checkSubjectExists(command, errorBuilder);
        checkStudentExists(command, errorBuilder);

        errorBuilder.flush();

        checkStudentAccountIsActive(command, errorBuilder);
        checkStudentIsFromTheSameSchoolClassAsSubject(command, errorBuilder);
        checkStudentIsAlreadyAssignedToSubject(command, errorBuilder);
    }

    /**
     * Check student is already assigned to provided subject
     */
    private void checkStudentIsAlreadyAssignedToSubject(AssignSubjectToStudentCommand command, ValidationErrorBuilder errorBuilder) {
        if (studentSubjectCommandRepository.isStudentAlreadyAssignedToSubject(command.studentId(), command.subjectId())) {
            errorBuilder.addError(
                    AssignSubjectToStudentCommand.SUBJECT_ID,
                    MESSAGE_KEY_STUDENT_STUDENT_ALREADY_ASSIGNED_TO_SUBJECT,
                    ErrorCode.OBJECT_ALREADY_EXISTS
            );
        }
    }

    /**
     * Check student is from the same school class as subject
     */
    private void checkStudentIsFromTheSameSchoolClassAsSubject(AssignSubjectToStudentCommand command, ValidationErrorBuilder errorBuilder) {
        if (!subjectCommandRepository.isStudentFromTheSameSchoolClass(command.studentId(), command.subjectId())) {
            errorBuilder.addError(
                    AssignSubjectToStudentCommand.STUDENT_ID,
                    MESSAGE_KEY_SUBJECT_IS_FROM_ANOTHER_SCHOOL_CLASS,
                    ErrorCode.ANOTHER_SCHOOL
            );
        }
    }

    /**
     * Check student account is active
     */
    private void checkStudentAccountIsActive(AssignSubjectToStudentCommand command, ValidationErrorBuilder errorBuilder) {
        if (studentCommandRepository.isStudentAccountNotActive(command.studentId())) {
            errorBuilder.addError(
                    AssignSubjectToStudentCommand.STUDENT_ID,
                    MESSAGE_KEY_ACCOUNT_INACTIVE,
                    ErrorCode.ACCOUNT_INACTIVE
            );
        }
    }

    /**
     * Check student with given id exists
     */
    private void checkStudentExists(AssignSubjectToStudentCommand command, ValidationErrorBuilder errorBuilder) {
        studentCommandRepository.findById(command.studentId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignSubjectToStudentCommand.STUDENT_ID);
                    return null;
                });
    }

    /**
     * Check subject with given id exists
     */
    private void checkSubjectExists(AssignSubjectToStudentCommand command, ValidationErrorBuilder errorBuilder) {
        subjectCommandRepository.findById(command.subjectId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignSubjectToStudentCommand.SUBJECT_ID);
                    return null;
                });
    }
}
