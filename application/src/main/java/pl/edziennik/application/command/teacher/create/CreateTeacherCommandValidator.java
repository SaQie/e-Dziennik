package pl.edziennik.application.command.teacher.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repositories.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repositories.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateTeacherCommandValidator implements IBaseValidator<CreateTeacherCommand> {

    private final TeacherCommandRepository teacherCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;

    @Override
    public void validate(CreateTeacherCommand command, ValidationErrorBuilder errorBuilder) {
        schoolCommandRepository.findById(command.schoolId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateTeacherCommand.SCHOOL_ID);
                    return null;
                });

        errorBuilder.flushErrors();

        if (teacherCommandRepository.existsByEmail(command.email())) {
            errorBuilder.addError(
                    CreateTeacherCommand.EMAIL,
                    "user.already.exists.by.email",
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.email());
        }
        if (teacherCommandRepository.existsByPesel(command.pesel())) {
            errorBuilder.addError(
                    CreateTeacherCommand.PESEL,
                    "teacher.pesel.not.unique",
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.pesel()
            );
        }
        if (teacherCommandRepository.existsByUsername(command.username())) {
            errorBuilder.addError(
                    CreateTeacherCommand.USERNAME,
                    "user.already.exists",
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.username());
        }
    }
}
