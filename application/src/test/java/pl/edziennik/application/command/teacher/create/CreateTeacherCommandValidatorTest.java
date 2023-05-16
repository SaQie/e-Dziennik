package pl.edziennik.application.command.teacher.create;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateTeacherCommandValidatorTest extends BaseUnitTest {

    private final CreateTeacherCommandValidator validator;

    public CreateTeacherCommandValidatorTest() {
        this.validator = new CreateTeacherCommandValidator(teacherCommandRepository, schoolCommandRepository);
    }

    @Test
    public void shouldThrowExceptionWhenSchoolNotExists() {
        // given
        CreateTeacherCommand command = new CreateTeacherCommand(
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
    public void shouldAddErrorWhenTeacherExistsByEmail() {
        // given
        User user = createUser("Test1", "test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        School school = createSchool("Test", "123123123", "123132123", address);
        school = schoolCommandRepository.save(school);

        PersonInformation personInformation = createPersonInformation("123123123123");
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);

        CreateTeacherCommand command = new CreateTeacherCommand(
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
                school.getSchoolId()
        );
        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateTeacherCommand.EMAIL);
        assertEquals(errors.get(0).errorMessage(), CreateTeacherCommandValidator.MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL);
    }

    @Test
    public void shouldAddErrorWhenTeacherExistsByUsername() {
        // given
        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        School school = createSchool("Test", "123123123", "123132123", address);
        school = schoolCommandRepository.save(school);

        PersonInformation personInformation = createPersonInformation("123123123123");
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);

        CreateTeacherCommand command = new CreateTeacherCommand(
                Password.of("password"),
                Username.of("Test"),
                FirstName.of("Test"),
                LastName.of("Test"),
                Address.of("address"),
                PostalCode.of("12-12"),
                City.of("Test"),
                Pesel.of("123123123"),
                Email.of("xxx@example.com"),
                PhoneNumber.of("123000000"),
                school.getSchoolId()
        );
        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateTeacherCommand.USERNAME);
        assertEquals(errors.get(0).errorMessage(), CreateTeacherCommandValidator.MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME);
    }

    @Test
    public void shouldAddErrorWhenTeacherExistsByPesel() {
        // given
        User user = createUser("Test1", "test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        School school = createSchool("Test", "123123123", "123132123", address);
        school = schoolCommandRepository.save(school);

        PersonInformation personInformation = createPersonInformation("123123123");
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);

        CreateTeacherCommand command = new CreateTeacherCommand(
                Password.of("password"),
                Username.of("Test11"),
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
        assertEquals(errors.get(0).field(), CreateTeacherCommand.PESEL);
        assertEquals(errors.get(0).errorMessage(), CreateTeacherCommandValidator.MESSAGE_KEY_TEACHER_PESEL_NOT_UNIQUE);
    }

}