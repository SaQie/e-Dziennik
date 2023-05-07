package pl.edziennik.eDziennik.command.student.service.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.command.user.createuser.CreateUserCommand;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.Pesel;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.infrastructure.repository.query.schoolclass.SchoolClassQueryRepository;
import pl.edziennik.eDziennik.infrastructure.repository.query.student.StudentQueryRepository;
import pl.edziennik.eDziennik.infrastructure.repository.query.user.UserQueryRepository;
import pl.edziennik.eDziennik.infrastructure.spring.base.IBaseValidator;
import pl.edziennik.eDziennik.infrastructure.spring.dispatcher.ValidationErrorBuilder;
import pl.edziennik.eDziennik.infrastructure.validator.errorcode.ErrorCode;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

@Component
@AllArgsConstructor
public class CreateStudentCommandValidator implements IBaseValidator<CreateStudentCommand> {

    private final ResourceCreator res;
    private final StudentQueryRepository studentQueryRepository;
    private final SchoolClassQueryRepository schoolClassQueryRepository;
    private final UserQueryRepository userRepository;

    @Override
    public void validate(CreateStudentCommand command, ValidationErrorBuilder errorBuilder) {
        if (userRepository.existsByEmail(command.email())) {
            errorBuilder.addError(
                    CreateUserCommand.EMAIL,
                    res.of("user.already.exists.by.email"),
                    ErrorCode.OBJECT_ALREADY_EXISTS);
        }

        if (userRepository.existsByUsername(command.username())) {
            errorBuilder.addError(
                    CreateUserCommand.USERNAME,
                    res.of("user.already.exists"),
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
