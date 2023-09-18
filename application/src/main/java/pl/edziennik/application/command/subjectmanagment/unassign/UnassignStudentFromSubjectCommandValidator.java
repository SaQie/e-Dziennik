package pl.edziennik.application.command.subjectmanagment.unassign;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.Validator;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class UnassignStudentFromSubjectCommandValidator implements Validator<UnassignStudentFromSubjectCommand> {

    public static final String MESSAGE_KEY_STUDENT_SUBJECT_HAS_ALREADY_ASSIGNED_GRADES = "student.subject.has.already.assigned.grades";
    private static final String MESSAGE_KEY_STUDENT_IS_NOT_ASSIGNED_TO_SUBJECT = "student.is.not.assigned.to.subject";

    private final StudentSubjectCommandRepository studentSubjectCommandRepository;
    private final SubjectCommandRepository subjectCommandRepository;
    private final StudentCommandRepository studentCommandRepository;

    @Override
    public void validate(UnassignStudentFromSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        checkStudentExists(command, errorBuilder);
        checkSubjectExists(command, errorBuilder);

        errorBuilder.flush();

        checkStudentIsAssignedToGivenSubject(command,errorBuilder);
        checkStudentSubjectHasAlreadyAssignedGrades(command, errorBuilder);

    }

    /**
     * Check student exists
     */
    private void checkStudentExists(UnassignStudentFromSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        studentCommandRepository.findById(command.studentId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(UnassignStudentFromSubjectCommand.STUDENT_ID);
                    return null;
                });
    }

    /**
     * Check subject exists
     */
    private void checkSubjectExists(UnassignStudentFromSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        subjectCommandRepository.findById(command.subjectId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(UnassignStudentFromSubjectCommand.SUBJECT_ID);
                    return null;
                });
    }

    /**
     * Check student subject has already assigned grades
     */
    private void checkStudentSubjectHasAlreadyAssignedGrades(UnassignStudentFromSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        boolean existsGradesAssignedToStudentSubject = studentSubjectCommandRepository.existsGradesAssignedToStudentSubject(command.studentId()
                , command.subjectId());
        if (existsGradesAssignedToStudentSubject) {
            errorBuilder.addError(
                    UnassignStudentFromSubjectCommand.STUDENT_ID,
                    MESSAGE_KEY_STUDENT_SUBJECT_HAS_ALREADY_ASSIGNED_GRADES,
                    ErrorCode.RELEATED_OBJECT_EXISTS
            );
        }
    }

    /**
     * Check student is assigned to given subject
     */
    private void checkStudentIsAssignedToGivenSubject(UnassignStudentFromSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        StudentSubject studentSubject = studentSubjectCommandRepository.getByStudentStudentIdAndSubjectSubjectId(command.studentId(), command.subjectId());

        if (studentSubject == null){
            errorBuilder.addError(
                    UnassignStudentFromSubjectCommand.STUDENT_ID,
                    MESSAGE_KEY_STUDENT_IS_NOT_ASSIGNED_TO_SUBJECT,
                    ErrorCode.NOT_ASSIGNED_TO_SUBJECT
            );
        }
    }
}
