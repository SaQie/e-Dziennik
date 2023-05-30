package pl.edziennik.application.command.subject.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateSubjectCommandValidator implements IBaseValidator<CreateSubjectCommand> {

    public static final String MESSAGE_KEY_SUBJECT_ALREADY_EXISTS = "subject.already.exist";
    public static final String MESSAGE_KEY_TEACHER_IS_FROM_ANOTHER_SCHOOL = "subject.teacher.is.from.another.school";
    public static final String MESSAGE_KEY_ACCOUNT_INACTIVE = "account.inactive";


    private final SubjectCommandRepository subjectCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;
    private final SchoolClassCommandRepository schoolClassCommandRepository;

    @Override
    public void validate(CreateSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        checkTeacherExists(command, errorBuilder);
        checkSchoolClassExists(command, errorBuilder);

        errorBuilder.flush();

        checkSubjectExistsByName(command, errorBuilder);
        checkTeacherAccountIsActive(command, errorBuilder);
        checkTeacherIsFromTheSameSchoolClass(command, errorBuilder);

    }

    /**
     * Check if teacher account is active
     */
    private void checkTeacherAccountIsActive(CreateSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        if (teacherCommandRepository.isTeacherAccountNotActive(command.teacherId())) {
            errorBuilder.addError(
                    CreateSubjectCommand.TEACHER_ID,
                    MESSAGE_KEY_ACCOUNT_INACTIVE,
                    ErrorCode.ACCOUNT_INACTIVE
            );
        }
    }

    /**
     * Check if teacher is from the same given school class
     */
    private void checkTeacherIsFromTheSameSchoolClass(CreateSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        if (!subjectCommandRepository.isTeacherFromTheSameSchool(command.schoolClassId(), command.teacherId())) {
            errorBuilder.addError(
                    CreateSubjectCommand.TEACHER_ID,
                    MESSAGE_KEY_TEACHER_IS_FROM_ANOTHER_SCHOOL,
                    ErrorCode.ANOTHER_SCHOOL
            );
        }
    }

    /**
     * Check subject exists in given school class with given name
     */
    private void checkSubjectExistsByName(CreateSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        if (subjectCommandRepository.existsByName(command.name(), command.schoolClassId())) {
            errorBuilder.addError(
                    CreateSubjectCommand.NAME,
                    MESSAGE_KEY_SUBJECT_ALREADY_EXISTS,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.name()
            );
        }
    }


    /**
     * Check school Class with given id exists
     */
    private void checkSchoolClassExists(CreateSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        schoolClassCommandRepository.findById(command.schoolClassId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateSubjectCommand.SCHOOL_CLASS_ID);
                    return null;
                });
    }

    /**
     * Check teacher with given id exists
     */
    private void checkTeacherExists(CreateSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        teacherCommandRepository.findById(command.teacherId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateSubjectCommand.TEACHER_ID);
                    return null;
                });
    }
}
