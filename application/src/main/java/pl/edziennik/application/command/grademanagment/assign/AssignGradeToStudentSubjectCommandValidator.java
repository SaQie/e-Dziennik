package pl.edziennik.application.command.grademanagment.assign;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.Validator;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class AssignGradeToStudentSubjectCommandValidator implements Validator<AssignGradeToStudentSubjectCommand> {

    public static final String MESSAGE_KEY_STUDENT_SUBJECT_NOT_EXISTS = "student.subject.not.exists";
    public static final String MESSAGE_KEY_ACCOUNT_INACTIVE = "account.inactive";

    private final StudentSubjectCommandRepository studentSubjectCommandRepository;
    private final StudentCommandRepository studentCommandRepository;
    private final SubjectCommandRepository subjectCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;

    @Override
    public void validate(AssignGradeToStudentSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        checkStudentExists(command, errorBuilder);
        checkSubjectExists(command, errorBuilder);
        checkTeacherExists(command, errorBuilder);

        errorBuilder.flush();

        checkTeacherAccountIsActive(command, errorBuilder);
        checkStudentAccountIsActive(command, errorBuilder);
        checkStudentIsAssignedToSubject(command, errorBuilder);


    }

    /**
     * Check given student account is active
     */
    private void checkStudentAccountIsActive(AssignGradeToStudentSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        if (studentCommandRepository.isStudentAccountNotActive(command.studentId())) {
            errorBuilder.addError(
                    AssignGradeToStudentSubjectCommand.STUDENT_ID,
                    MESSAGE_KEY_ACCOUNT_INACTIVE,
                    ErrorCode.ACCOUNT_INACTIVE
            );
        }
    }

    /**
     * Check given teacher account is active
     */
    private void checkTeacherAccountIsActive(AssignGradeToStudentSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        if (teacherCommandRepository.isTeacherAccountNotActive(command.teacherId())) {
            errorBuilder.addError(
                    AssignGradeToStudentSubjectCommand.TEACHER_ID,
                    MESSAGE_KEY_ACCOUNT_INACTIVE,
                    ErrorCode.ACCOUNT_INACTIVE
            );
        }
    }

    /**
     * Check student is assigned to given subject
     */
    private void checkStudentIsAssignedToSubject(AssignGradeToStudentSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        if (!studentSubjectCommandRepository.isStudentAlreadyAssignedToSubject(command.studentId(), command.subjectId())) {
            errorBuilder.addError(
                    AssignGradeToStudentSubjectCommand.SUBJECT_ID,
                    MESSAGE_KEY_STUDENT_SUBJECT_NOT_EXISTS,
                    ErrorCode.NOT_ASSIGNED_TO_SUBJECT
            );
        }
    }

    /**
     * Check teacher with given id exists
     */
    private void checkTeacherExists(AssignGradeToStudentSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        teacherCommandRepository.findById(command.teacherId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignGradeToStudentSubjectCommand.TEACHER_ID);
                    return null;
                });
    }

    /**
     * Check subject with given id exists
     */
    private void checkSubjectExists(AssignGradeToStudentSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        subjectCommandRepository.findById(command.subjectId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignGradeToStudentSubjectCommand.SUBJECT_ID);
                    return null;
                });
    }

    /**
     * Check student with given id exists
     */
    private void checkStudentExists(AssignGradeToStudentSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        studentCommandRepository.findById(command.studentId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignGradeToStudentSubjectCommand.STUDENT_ID);
                    return null;
                });
    }
}
