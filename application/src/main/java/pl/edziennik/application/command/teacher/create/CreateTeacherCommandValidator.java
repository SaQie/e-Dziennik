package pl.edziennik.application.command.teacher.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateTeacherCommandValidator implements IBaseValidator<CreateTeacherCommand> {

    public static final String MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL = "user.already.exists.by.email";
    public static final String MESSAGE_KEY_TEACHER_PESEL_NOT_UNIQUE = "teacher.pesel.not.unique";
    public static final String MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME = "user.already.exists";

    private final TeacherCommandRepository teacherCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;

    @Override
    public void validate(CreateTeacherCommand command, ValidationErrorBuilder errorBuilder) {
        schoolCommandRepository.findById(command.schoolId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateTeacherCommand.SCHOOL_ID);
                    return null;
                });

        errorBuilder.flush();

        if (teacherCommandRepository.isExistsByEmail(command.email())) {
            errorBuilder.addError(
                    CreateTeacherCommand.EMAIL,
                    MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.email());
        }
        if (teacherCommandRepository.isExistsByPesel(command.pesel())) {
            errorBuilder.addError(
                    CreateTeacherCommand.PESEL,
                    MESSAGE_KEY_TEACHER_PESEL_NOT_UNIQUE,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.pesel()
            );
        }
        if (teacherCommandRepository.isExistsByUsername(command.username())) {
            errorBuilder.addError(
                    CreateTeacherCommand.USERNAME,
                    MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.username());
        }
    }
}
