package pl.edziennik.application.command.student.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.domain.role.Role;
import pl.edziennik.infrastructure.repositories.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repositories.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repositories.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repositories.user.UserCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateStudentCommandValidator implements IBaseValidator<CreateStudentCommand> {

    private final StudentCommandRepository studentCommandRepository;
    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final UserCommandRepository userCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;

    @Override
    public void validate(CreateStudentCommand command, ValidationErrorBuilder errorBuilder) {
        schoolCommandRepository.findById(command.schoolId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateStudentCommand.SCHOOL_ID);
                    return null;
                });

        schoolClassCommandRepository.findById(command.schoolClassId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateStudentCommand.SCHOOL_CLASS_ID);
                    return null;
                });

        errorBuilder.flushErrors();

        if (userCommandRepository.existsByEmail(command.email())) {
            errorBuilder.addError(
                    CreateStudentCommand.EMAIL,
                    "user.already.exists.by.email",
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.email());
        }

        if (userCommandRepository.existsByUsername(command.username())) {
            errorBuilder.addError(
                    CreateStudentCommand.USERNAME,
                    "user.already.exists",
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.username());
        }

        if (studentCommandRepository.isStudentExistsByPesel(command.pesel(), Role.RoleConst.ROLE_STUDENT.roleName())) {
            errorBuilder.addError(
                    CreateStudentCommand.PESEL,
                    "student.pesel.not.unique",
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.pesel()
            );
        }

        if (!schoolClassCommandRepository.isSchoolClassBelongToSchool(command.schoolClassId(), command.schoolId())) {
            errorBuilder.addError(
                    CreateStudentCommand.SCHOOL_ID,
                    "school.class.not.belong.to.school",
                    ErrorCode.SCHOOL_CLASS_IS_NOT_PART_OF_SCHOOL
            );
        }
    }
}
