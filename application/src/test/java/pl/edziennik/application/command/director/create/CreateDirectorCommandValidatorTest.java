package pl.edziennik.application.command.director.create;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.command.teacher.create.CreateTeacherCommand;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateDirectorCommandValidatorTest extends BaseUnitTest {

    private final CreateDirectorCommandValidator validator;

    public CreateDirectorCommandValidatorTest() {
        this.validator = new CreateDirectorCommandValidator(userCommandRepository, schoolCommandRepository);
    }

    @Test
    public void shouldThrowErrorWhenSchoolNotExists() {
        // given
        CreateDirectorCommand command = new CreateDirectorCommand(
                Password.of("password"),
                Username.of("Test"),
                FirstName.of("Test"),
                LastName.of("Test"),
                Address.of("address"),
                PostalCode.of("12-12"),
                City.of("Test"),
                Pesel.of("123123123"),
                Email.of("test@example.com"),
                PhoneNumber.of("123000000"),
                SchoolId.create()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(CreateTeacherCommand.SCHOOL_ID));
    }

    @Test
    public void shouldAddErrorWhenSchoolHasAlreadyAssignedDirector() {
        // given
        User user = createUser("Test1", "test@example.com", RoleCommandMockRepo.DIRECTOR_ROLE_NAME.value());
        School school = createSchool("Test", "123123123", "123132123", address);
        school = schoolCommandRepository.save(school);

        createDirector(user, school, personInformation, address);

        CreateDirectorCommand command = new CreateDirectorCommand(
                Password.of("password"),
                Username.of("Test"),
                FirstName.of("Test"),
                LastName.of("Test"),
                Address.of("address"),
                PostalCode.of("12-12"),
                City.of("Test"),
                Pesel.of("123123123"),
                Email.of("test1@example.com"),
                PhoneNumber.of("123000000"),
                school.getSchoolId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateDirectorCommand.SCHOOL_ID);
        assertEquals(errors.get(0).message(), CreateDirectorCommandValidator.MESSAGE_KEY_SCHOOL_ALREADY_HAS_ASSIGNED_DIRECTOR);

    }

    @Test
    public void shouldAddErrorWhenUserWithGivenUsernameAlreadyExists() {
        // given
        User user = createUser("Test1", "test@example.com", RoleCommandMockRepo.DIRECTOR_ROLE_NAME.value());
        School school = createSchool("Test", "123123123", "123132123", address);
        school = schoolCommandRepository.save(school);

        School secondSchool = createSchool("Test2", "1231222", "12332", address);
        secondSchool = schoolCommandRepository.save(secondSchool);

        createDirector(user, school, personInformation, address);

        CreateDirectorCommand command = new CreateDirectorCommand(
                Password.of("password"),
                Username.of("Test1"),
                FirstName.of("Test"),
                LastName.of("Test"),
                Address.of("address"),
                PostalCode.of("12-12"),
                City.of("Test"),
                Pesel.of("123123123"),
                Email.of("test1@example.com"),
                PhoneNumber.of("123000000"),
                secondSchool.getSchoolId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateDirectorCommand.USERNAME);
        assertEquals(errors.get(0).message(), CreateDirectorCommandValidator.MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME);
    }

    @Test
    public void shouldAddErrorWhenUserWithGivenEmailAlreadyExists() {
        // given
        User user = createUser("Test1", "test@example.com", RoleCommandMockRepo.DIRECTOR_ROLE_NAME.value());
        School school = createSchool("Test", "123123123", "123132123", address);
        school = schoolCommandRepository.save(school);

        School secondSchool = createSchool("Test2", "1231222", "12332", address);
        secondSchool = schoolCommandRepository.save(secondSchool);

        createDirector(user, school, personInformation, address);

        CreateDirectorCommand command = new CreateDirectorCommand(
                Password.of("password"),
                Username.of("Test"),
                FirstName.of("Test"),
                LastName.of("Test"),
                Address.of("address"),
                PostalCode.of("12-12"),
                City.of("Test"),
                Pesel.of("123123123"),
                Email.of("test@example.com"),
                PhoneNumber.of("123000000"),
                secondSchool.getSchoolId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateDirectorCommand.EMAIL);
        assertEquals(errors.get(0).message(), CreateDirectorCommandValidator.MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL);
    }

    @Test
    public void shouldAddErrorWhenUserWithGivenPeselAlreadyExists() {
        // given
        User user = createUser("Test1", "test@example.com", RoleCommandMockRepo.DIRECTOR_ROLE_NAME.value());
        School school = createSchool("Test", "123123123", "123132123", address);
        school = schoolCommandRepository.save(school);

        School secondSchool = createSchool("Test2", "1231222", "12332", address);
        secondSchool = schoolCommandRepository.save(secondSchool);

        createDirector(user, school, personInformation, address);

        CreateDirectorCommand command = new CreateDirectorCommand(
                Password.of("password"),
                Username.of("Test"),
                FirstName.of("Test"),
                LastName.of("Test"),
                Address.of("address"),
                PostalCode.of("12-12"),
                City.of("Test"),
                Pesel.of(user.getPesel().value()),
                Email.of("test1@example.com"),
                PhoneNumber.of("123000000"),
                secondSchool.getSchoolId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateDirectorCommand.PESEL);
        assertEquals(errors.get(0).message(), CreateDirectorCommandValidator.MESSAGE_KEY_PESEL_NOT_UNIQUE);
    }
}
