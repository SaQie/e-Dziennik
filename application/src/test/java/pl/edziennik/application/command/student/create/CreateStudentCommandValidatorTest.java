package pl.edziennik.application.command.student.create;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateStudentCommandValidatorTest extends BaseUnitTest {

    private final CreateStudentCommandValidator validator;

    public CreateStudentCommandValidatorTest() {
        this.validator = new CreateStudentCommandValidator(studentCommandRepository,
                schoolClassCommandRepository,
                schoolCommandRepository);
    }

    @Test
    public void shouldThrowErrorWhenSchoolNotExists() {
        // given
        School school = createSchool("Test", "123123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        SchoolClass schoolClass = createSchoolClass("Testowa", school, null);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test112"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("99999999999"),
                Email.of("Test@example.com"),
                PhoneNumber.of("123123"),
                SchoolId.create(),
                schoolClass.getSchoolClassId()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertBusinessExceptionMessage(CreateStudentCommand.SCHOOL_ID));
    }

    @Test
    public void shouldThrowErrorWhenSchoolClassNotExists() {
        // given
        School school = createSchool("Test", "123123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test112"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("99999999999"),
                Email.of("Test@example.com"),
                PhoneNumber.of("123123"),
                school.getSchoolId(),
                SchoolClassId.create()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertBusinessExceptionMessage(CreateStudentCommand.SCHOOL_CLASS_ID));

    }

    @Test
    public void shouldAddErrorWhenStudentExistsByUsername() {
        // given
        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());

        School school = createSchool("Testowa", "123123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        SchoolClass schoolClass = createSchoolClass("Test", school, null);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("99999999999"),
                Email.of("Test@example.com"),
                PhoneNumber.of("123123"),
                school.getSchoolId(),
                schoolClass.getSchoolClassId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateStudentCommand.USERNAME);
        assertEquals(errors.get(0).errorMessage(), CreateStudentCommandValidator.MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME);
    }

    @Test
    public void shouldAddErrorWhenStudentExistsByEmail() {
        // given
        User user = createUser("Test1", "Test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());

        School school = createSchool("Testowa", "123123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        SchoolClass schoolClass = createSchoolClass("Test", school, null);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("99999999999"),
                Email.of("Test@example.com"),
                PhoneNumber.of("123123"),
                school.getSchoolId(),
                schoolClass.getSchoolClassId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateStudentCommand.EMAIL);
        assertEquals(errors.get(0).errorMessage(), CreateStudentCommandValidator.MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL);
    }

    @Test
    public void shouldAddErrorWhenStudentExistsByPesel() {
        // given
        User user = createUser("Test1", "Test1@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());

        School school = createSchool("Testowa", "123123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        SchoolClass schoolClass = createSchoolClass("Test", school, null);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("12345678912"),
                Email.of("Test@example.com"),
                PhoneNumber.of("123123"),
                school.getSchoolId(),
                schoolClass.getSchoolClassId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateStudentCommand.PESEL);
        assertEquals(errors.get(0).errorMessage(), CreateStudentCommandValidator.MESSAGE_KEY_STUDENT_PESEL_NOT_UNIQUE);
    }

    @Test
    public void shouldAddErrorWhenSchoolClassNotBelongsToSchool() {
        // given
        School school = createSchool("FirstSchool", "123123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        SchoolClass schoolClass = createSchoolClass("Test", school, null);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        School secondSchool = createSchool("SecondSchool", "123123123", "123123123", address);
        secondSchool = schoolCommandRepository.save(secondSchool);

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("12345678912"),
                Email.of("Test@example.com"),
                PhoneNumber.of("123123"),
                secondSchool.getSchoolId(),
                schoolClass.getSchoolClassId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateStudentCommand.SCHOOL_ID);
        assertEquals(errors.get(0).errorMessage(), CreateStudentCommandValidator.MESSAGE_KEY_SCHOOL_CLASS_NOT_BELONGS_TO_SCHOOL);
    }

    @Test
    public void shouldAddErrorWhenSchoolClassStudentLimitReached() {
        // given
        User user = createUser("Test1", "Test1@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());

        School school = createSchool("Testowa", "123123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        SchoolClass schoolClass = createSchoolClass("Test", school, null);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        schoolClass.getSchoolClassConfiguration().changeMaxStudentsSize(0);

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("12345678912"),
                Email.of("Test@example.com"),
                PhoneNumber.of("123123"),
                school.getSchoolId(),
                schoolClass.getSchoolClassId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateStudentCommand.SCHOOL_CLASS_ID);
        assertEquals(errors.get(0).errorMessage(), CreateStudentCommandValidator.MESSAGE_KEY_SCHOOL_CLASS_STUDENT_LIMIT_REACHED);
    }


}

