package pl.edziennik.application.command.teacher.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.Validator;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.repository.user.UserCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateTeacherCommandValidator implements Validator<CreateTeacherCommand> {

    public static final String MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL = "user.already.exists.by.email";
    public static final String MESSAGE_KEY_TEACHER_PESEL_NOT_UNIQUE = "teacher.pesel.not.unique";
    public static final String MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME = "user.already.exists";

    private final TeacherCommandRepository teacherCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;
    private final UserCommandRepository userCommandRepository;

    @Override
    public void validate(CreateTeacherCommand command, ValidationErrorBuilder errorBuilder) {
        checkSchoolExists(command, errorBuilder);

        checkUserByEmailAlreadyExists(command, errorBuilder);
        checkUserByUsernameAlreadyExists(command, errorBuilder);
        checkUserByPeselAlreadyExists(command, errorBuilder);
    }

    /**
     * Check if user with given username already exists
     */
    private void checkUserByUsernameAlreadyExists(CreateTeacherCommand command, ValidationErrorBuilder errorBuilder) {
        if (userCommandRepository.existsByUsername(command.username())) {
            errorBuilder.addError(
                    CreateTeacherCommand.USERNAME,
                    MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.username());
        }
    }

    /**
     * Check if teacher with given pesel already exists
     */
    private void checkUserByPeselAlreadyExists(CreateTeacherCommand command, ValidationErrorBuilder errorBuilder) {
        if (userCommandRepository.existsByPesel(command.pesel())) {
            errorBuilder.addError(
                    CreateTeacherCommand.PESEL,
                    MESSAGE_KEY_TEACHER_PESEL_NOT_UNIQUE,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.pesel()
            );
        }
    }

    /**
     * Check if user with given email already exists
     */
    private void checkUserByEmailAlreadyExists(CreateTeacherCommand command, ValidationErrorBuilder errorBuilder) {
        if (userCommandRepository.existsByEmail(command.email())) {
            errorBuilder.addError(
                    CreateTeacherCommand.EMAIL,
                    MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.email());
        }
    }

    /**
     * Check if school with given id exists
     */
    private void checkSchoolExists(CreateTeacherCommand command, ValidationErrorBuilder errorBuilder) {
        schoolCommandRepository.findById(command.schoolId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateTeacherCommand.SCHOOL_ID);
                    return null;
                });
        errorBuilder.flush();
    }
}
