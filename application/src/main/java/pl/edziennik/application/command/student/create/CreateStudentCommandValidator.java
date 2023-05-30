package pl.edziennik.application.command.student.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.domain.role.Role;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.user.UserCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateStudentCommandValidator implements IBaseValidator<CreateStudentCommand> {

    public static final String MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL = "user.already.exists.by.email";
    public static final String MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME = "user.already.exists";
    public static final String MESSAGE_KEY_STUDENT_PESEL_NOT_UNIQUE = "student.pesel.not.unique";
    public static final String MESSAGE_KEY_SCHOOL_CLASS_NOT_BELONGS_TO_SCHOOL = "school.class.not.belong.to.school";
    public static final String MESSAGE_KEY_SCHOOL_CLASS_STUDENT_LIMIT_REACHED = "school.class.student.limit.reached";

    private final StudentCommandRepository studentCommandRepository;
    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;
    private final UserCommandRepository userCommandRepository;

    @Override
    public void validate(CreateStudentCommand command, ValidationErrorBuilder errorBuilder) {
        checkSchoolExists(command, errorBuilder);
        checkSchoolClassExists(command, errorBuilder);

        errorBuilder.flush();

        checkUserWithEmailExists(command, errorBuilder);
        checkUserWithUsernameExists(command, errorBuilder);
        checkStudentWithPeselExists(command, errorBuilder);
        checkSchoolClassBelongToSchool(command, errorBuilder);
        checkSchoolClassStudentLimit(command, errorBuilder);

    }

    /**
     * Check if school class student limit reached
     */
    private void checkSchoolClassStudentLimit(CreateStudentCommand command, ValidationErrorBuilder errorBuilder) {
        if (schoolClassCommandRepository.isStudentLimitReached(command.schoolClassId())) {
            errorBuilder.addError(
                    CreateStudentCommand.SCHOOL_CLASS_ID,
                    MESSAGE_KEY_SCHOOL_CLASS_STUDENT_LIMIT_REACHED,
                    ErrorCode.LIMIT_REACHED
            );
        }
    }

    /**
     * Check if given school class belong to given school
     */
    private void checkSchoolClassBelongToSchool(CreateStudentCommand command, ValidationErrorBuilder errorBuilder) {
        if (!schoolClassCommandRepository.isSchoolClassBelongToSchool(command.schoolClassId(), command.schoolId())) {
            errorBuilder.addError(
                    CreateStudentCommand.SCHOOL_ID,
                    MESSAGE_KEY_SCHOOL_CLASS_NOT_BELONGS_TO_SCHOOL,
                    ErrorCode.SCHOOL_CLASS_IS_NOT_PART_OF_SCHOOL
            );
        }
    }

    /**
     * Check if student with given pesel already exists
     */
    private void checkStudentWithPeselExists(CreateStudentCommand command, ValidationErrorBuilder errorBuilder) {
        if (studentCommandRepository.isStudentExistsByPesel(command.pesel(), Role.RoleConst.ROLE_STUDENT.roleName())) {
            errorBuilder.addError(
                    CreateStudentCommand.PESEL,
                    MESSAGE_KEY_STUDENT_PESEL_NOT_UNIQUE,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.pesel()
            );
        }
    }

    /**
     * Check if user with given username already exists
     */
    private void checkUserWithUsernameExists(CreateStudentCommand command, ValidationErrorBuilder errorBuilder) {
        if (userCommandRepository.existsByUsername(command.username())) {
            errorBuilder.addError(
                    CreateStudentCommand.USERNAME,
                    MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.username());
        }
    }

    /**
     * Check if user with given email address already exists
     */
    private void checkUserWithEmailExists(CreateStudentCommand command, ValidationErrorBuilder errorBuilder) {
        if (userCommandRepository.existsByEmail(command.email())) {
            errorBuilder.addError(
                    CreateStudentCommand.EMAIL,
                    MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.email());
        }
    }

    /**
     * Check if school class with given id exists
     */
    private void checkSchoolClassExists(CreateStudentCommand command, ValidationErrorBuilder errorBuilder) {
        schoolClassCommandRepository.findById(command.schoolClassId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateStudentCommand.SCHOOL_CLASS_ID);
                    return null;
                });
    }

    /**
     * Check if school with given id exists
     */
    private void checkSchoolExists(CreateStudentCommand command, ValidationErrorBuilder errorBuilder) {
        schoolCommandRepository.findById(command.schoolId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateStudentCommand.SCHOOL_ID);
                    return null;
                });
    }
}
