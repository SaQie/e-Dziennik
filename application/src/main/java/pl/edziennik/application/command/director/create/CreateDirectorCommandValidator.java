package pl.edziennik.application.command.director.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.user.UserCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateDirectorCommandValidator implements IBaseValidator<CreateDirectorCommand> {

    public static final String MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL = "user.already.exists.by.email";
    public static final String MESSAGE_KEY_PESEL_NOT_UNIQUE = "pesel.not.unique";
    public static final String MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME = "user.already.exists";
    public static final String MESSAGE_KEY_SCHOOL_ALREADY_HAS_ASSIGNED_DIRECTOR = "school.already.has.assigned.director";

    private final UserCommandRepository userCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;

    @Override
    public void validate(CreateDirectorCommand command, ValidationErrorBuilder errorBuilder) {
        checkSchoolExists(command, errorBuilder);

        checkSchoolAlreadyHasAssignedDirector(command, errorBuilder);

        checkUserByEmailAlreadyExists(command, errorBuilder);
        checkUserByUsernameAlreadyExists(command, errorBuilder);
        checkUserByPeselAlreadyExists(command, errorBuilder);
    }

    /**
     * Check if school already has assigned director
     */
    private void checkSchoolAlreadyHasAssignedDirector(CreateDirectorCommand command, ValidationErrorBuilder errorBuilder) {
        if (schoolCommandRepository.isSchoolHasAssignedDirector(command.schoolId())) {
            errorBuilder.addError(
                    CreateDirectorCommand.SCHOOL_ID,
                    MESSAGE_KEY_SCHOOL_ALREADY_HAS_ASSIGNED_DIRECTOR,
                    ErrorCode.SCHOOL_ALREADY_HAS_ASSIGNED_DIRECTOR);
        }
    }


    /**
     * Check if user with given username already exists
     */
    private void checkUserByUsernameAlreadyExists(CreateDirectorCommand command, ValidationErrorBuilder errorBuilder) {
        if (userCommandRepository.existsByUsername(command.username())) {
            errorBuilder.addError(
                    CreateDirectorCommand.USERNAME,
                    MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.username());
        }
    }

    /**
     * Check if user with given pesel already exists
     */
    private void checkUserByPeselAlreadyExists(CreateDirectorCommand command, ValidationErrorBuilder errorBuilder) {
        if (userCommandRepository.existsByPesel(command.pesel())) {
            errorBuilder.addError(
                    CreateDirectorCommand.PESEL,
                    MESSAGE_KEY_PESEL_NOT_UNIQUE,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.pesel()
            );
        }
    }

    /**
     * Check if user with given email already exists
     */
    private void checkUserByEmailAlreadyExists(CreateDirectorCommand command, ValidationErrorBuilder errorBuilder) {
        if (userCommandRepository.existsByEmail(command.email())) {
            errorBuilder.addError(
                    CreateDirectorCommand.EMAIL,
                    MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.email());
        }
    }

    /**
     * Check if school with given id exists
     */
    private void checkSchoolExists(CreateDirectorCommand command, ValidationErrorBuilder errorBuilder) {
        schoolCommandRepository.findById(command.schoolId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateDirectorCommand.SCHOOL_ID);
                    return null;
                });
        errorBuilder.flush();
    }
}
