package pl.edziennik.application.command.student.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.domain.role.Role;
import pl.edziennik.infrastructure.command.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.command.student.StudentCommandRepository;
import pl.edziennik.infrastructure.command.user.UserCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateStudentCommandValidator implements IBaseValidator<CreateStudentCommand> {

    private final ResourceCreator res;
    private final StudentCommandRepository studentCommandRepository;
    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final UserCommandRepository userCommandRepository;

    @Override
    public void validate(CreateStudentCommand command, ValidationErrorBuilder errorBuilder) {
        if (userCommandRepository.existsByEmail(command.email())) {
            errorBuilder.addError(
                    CreateStudentCommand.EMAIL,
                    res.of("user.already.exists.by.email", command.email()),
                    ErrorCode.OBJECT_ALREADY_EXISTS);
        }

        if (userCommandRepository.existsByUsername(command.username())) {
            errorBuilder.addError(
                    CreateStudentCommand.USERNAME,
                    res.of("user.already.exists", command.username()),
                    ErrorCode.OBJECT_ALREADY_EXISTS);
        }

        if (studentCommandRepository.isStudentExistsByPesel(command.pesel(), Role.RoleConst.ROLE_STUDENT.getId())) {
            errorBuilder.addError(
                    CreateStudentCommand.PESEL,
                    res.of("student.pesel.not.unique", command.pesel()),
                    ErrorCode.OBJECT_ALREADY_EXISTS
            );
        }

        if (!schoolClassCommandRepository.isSchoolClassBelongToSchool(command.schoolClassId(), command.schoolId())) {
            errorBuilder.addError(
                    CreateStudentCommand.ID_SCHOOL,
                    res.of("school.class.not.belong.to.school"),
                    ErrorCode.SCHOOL_CLASS_IS_NOT_PART_OF_SCHOOL
            );
        }
    }
}
