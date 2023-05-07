package pl.edziennik.application.command.student.service.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.command.user.createuser.CreateUserCommand;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.domain.personinfromation.Pesel;
import pl.edziennik.domain.role.Role;
import pl.edziennik.infrastructure.query.schoolclass.SchoolClassQueryRepository;
import pl.edziennik.infrastructure.query.student.StudentQueryRepository;
import pl.edziennik.infrastructure.query.user.UserQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateStudentCommandValidator implements IBaseValidator<CreateStudentCommand> {

    private final ResourceCreator res;
    private final StudentQueryRepository studentQueryRepository;
    private final SchoolClassQueryRepository schoolClassQueryRepository;
    private final UserQueryRepository userRepository;

    @Override
    public void validate(CreateStudentCommand command, ValidationErrorBuilder errorBuilder) {
        if (userRepository.existsByEmail(command.email())) {
            errorBuilder.addError(
                    CreateUserCommand.EMAIL,
                    res.of("user.already.exists.by.email", command.email()),
                    ErrorCode.OBJECT_ALREADY_EXISTS);
        }

        if (userRepository.existsByUsername(command.username())) {
            errorBuilder.addError(
                    CreateUserCommand.USERNAME,
                    res.of("user.already.exists", command.username()),
                    ErrorCode.OBJECT_ALREADY_EXISTS);
        }

        if (studentQueryRepository.isStudentExistsByPesel(Pesel.of(command.pesel()), Role.RoleConst.ROLE_STUDENT.getId())) {
            errorBuilder.addError(
                    CreateStudentCommand.PESEL,
                    res.of("student.pesel.not.unique", command.pesel()),
                    ErrorCode.OBJECT_ALREADY_EXISTS
            );
        }

        if (!schoolClassQueryRepository.isSchoolClassBelongToSchool(command.schoolClassId(), command.schoolId())) {
            errorBuilder.addError(
                    CreateStudentCommand.ID_SCHOOL,
                    res.of("school.class.not.belong.to.school"),
                    ErrorCode.SCHOOL_CLASS_IS_NOT_PART_OF_SCHOOL
            );
        }
    }
}
