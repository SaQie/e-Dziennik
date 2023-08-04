package pl.edziennik.application.command.parent.create;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateParentCommandValidatorTest extends BaseUnitTest {

    private final CreateParentCommandValidator validator;

    public CreateParentCommandValidatorTest() {
        this.validator = new CreateParentCommandValidator(studentCommandRepository, userCommandRepository);
    }

    @Test
    public void shouldThrowExceptionWhenStudentNotExists() {
        // given
        CreateParentCommand command = new CreateParentCommand(
                Password.of("Test"),
                Username.of("Kamil"),
                FirstName.of("Nowak"),
                LastName.of("Test"),
                Address.of("TEST"),
                PostalCode.of("55-200"),
                City.of("Daleko"),
                Pesel.of("123123123"),
                Email.of("Kamcio@o2.pl"),
                PhoneNumber.of("999999999"),
                StudentId.create()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(
                                CreateParentCommand.STUDENT_ID));
    }

    @Test
    public void shouldAddErrorWhenStudentHasAlreadyAssignedParent() {
        // given
        School school = createSchool("Test", "123123", "123123", address);
        User user = createUser("Kamil", "Nowak@o2.pl", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        user.activate();
        Parent parent = createParent(user, null, null);
        Student student = createStudent(user, school, SchoolClass.of(null, null, null, schoolClassConfigurationProperties), null, null, parent);
        student = studentCommandRepository.save(student);

        CreateParentCommand command = new CreateParentCommand(
                Password.of("Test"),
                Username.of("Kamil1"),
                FirstName.of("Nowak"),
                LastName.of("Test"),
                Address.of("TEST"),
                PostalCode.of("55-200"),
                City.of("Daleko"),
                Pesel.of("123123123"),
                Email.of("Kamcio@o2.pl"),
                PhoneNumber.of("999999999"),
                student.studentId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateParentCommand.STUDENT_ID);
        assertEquals(errors.get(0).message(), CreateParentCommandValidator.MESSAGE_KEY_STUDENT_ALREADY_HAS_PARENT);
    }

    @Test
    public void shouldAddErrorWhenParentAlreadyExistsByUsername() {
        // given
        User user = createUser("Test", "test@o2.pl", RoleCommandMockRepo.PARENT_ROLE_NAME.value());
        Parent parent = createParent(user, null, address);
        parentCommandRepository.save(parent);

        CreateParentCommand command = new CreateParentCommand(
                Password.of("Test"),
                Username.of("Test"),
                FirstName.of("Nowak"),
                LastName.of("Test"),
                Address.of("TEST"),
                PostalCode.of("55-200"),
                City.of("Daleko"),
                Pesel.of("123123123"),
                Email.of("Kamcio@o2.pl"),
                PhoneNumber.of("999999999"),
                null
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateParentCommand.USERNAME);
        assertEquals(errors.get(0).message(), CreateParentCommandValidator.MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME);

    }

    @Test
    public void shouldAddErrorWhenParentAlreadyExistsByEmail() {
        // given
        User user = createUser("Test1", "test@o2.pl", RoleCommandMockRepo.PARENT_ROLE_NAME.value());
        Parent parent = createParent(user, null, address);
        parentCommandRepository.save(parent);

        CreateParentCommand command = new CreateParentCommand(
                Password.of("Test"),
                Username.of("Test"),
                FirstName.of("Nowak"),
                LastName.of("Test"),
                Address.of("TEST"),
                PostalCode.of("55-200"),
                City.of("Daleko"),
                Pesel.of("123123123"),
                Email.of("test@o2.pl"),
                PhoneNumber.of("999999999"),
                null
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateParentCommand.EMAIL);
        assertEquals(errors.get(0).message(), CreateParentCommandValidator.MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL);


    }

}
