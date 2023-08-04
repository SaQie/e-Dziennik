package pl.edziennik.application.command.schoolclass.create;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateSchoolClassCommandValidatorTest extends BaseUnitTest {

    private final CreateSchoolClassCommandValidator validator;

    public CreateSchoolClassCommandValidatorTest() {
        this.validator = new CreateSchoolClassCommandValidator(schoolClassCommandRepository, schoolCommandRepository, teacherCommandRepository);
    }

    @Test
    public void shouldThrowErrorWhenSchoolNotExists() {
        // given
        CreateSchoolClassCommand command = new CreateSchoolClassCommand(
                Name.of("Test"),
                null,
                null
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(
                                CreateSchoolClassCommand.SCHOOL_ID));

    }

    @Test
    public void shouldThrowExceptionWhenTeacherIsProvidedButWasNotFound() {
        // given
        School school = createSchool("Test", "123123132", "123123123", address);
        school = schoolCommandRepository.save(school);

        CreateSchoolClassCommand command = new CreateSchoolClassCommand(
                Name.of("Test"),
                TeacherId.create(),
                school.schoolId()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(
                                CreateSchoolClassCommand.TEACHER_ID));

    }

    @Test
    public void shouldThrowExceptionWhenTeacherIsNotAssignedToGivenSchool() {
        // given
        School school = createSchool("first", "123123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        School secondSchool = createSchool("second", "123111", "2322323", address);
        secondSchool = schoolCommandRepository.save(secondSchool);

        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        user.activate();
        Teacher teacher = createTeacher(user, secondSchool, null, address);
        teacher = teacherCommandRepository.save(teacher);

        CreateSchoolClassCommand command = new CreateSchoolClassCommand(
                Name.of("Test"),
                teacher.teacherId(),
                school.schoolId()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(
                                CreateSchoolClassCommand.TEACHER_ID,
                                CreateSchoolClassCommandValidator.MESSAGE_KEY_TEACHER_NOT_BELONGS_TO_SCHOOL,
                                ErrorCode.NOT_ASSIGNED_TO_SCHOOL));

    }

    @Test
    public void shouldAddErrorWhenTeacherIsAlreadySupervisingTeacher() {
        // given
        School school = createSchool("first", "123123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        user.activate();
        Teacher teacher = createTeacher(user, school, null, address);
        teacher = teacherCommandRepository.save(teacher);

        SchoolClass schoolClass = createSchoolClass("Test", school, teacher);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        CreateSchoolClassCommand command = new CreateSchoolClassCommand(
                Name.of("Test"),
                teacher.teacherId(),
                school.schoolId()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(
                                CreateSchoolClassCommand.TEACHER_ID,
                                CreateSchoolClassCommandValidator.MESSAGE_KEY_TEACHER_IS_ALREADY_SUPERVISING_TEACHER,
                                ErrorCode.SELECTED_TEACHER_IS_ALREADY_SUPERVISING_TEACHER));
    }

    @Test
    public void shouldAddErrorWhenSchoolClassWithGivenNameAlreadyExists() {
        // given
        School school = createSchool("first", "123123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        user.activate();
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);

        SchoolClass schoolClass = createSchoolClass("Test", school, teacher);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        CreateSchoolClassCommand command = new CreateSchoolClassCommand(
                Name.of("Test"),
                null,
                school.schoolId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateSchoolClassCommand.CLASS_NAME);
        assertEquals(errors.get(0).message(), CreateSchoolClassCommandValidator.MESSAGE_KEY_SCHOOL_CLASS_ALREADY_EXISTS);
    }

    @Test
    public void shouldAddErrorWhenTeacherIsInactive() {
        // given
        School school = createSchool("first", "123123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);

        CreateSchoolClassCommand command = new CreateSchoolClassCommand(
                Name.of("Test"),
                teacher.teacherId(),
                school.schoolId()
        );

        // when
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(
                                CreateSchoolClassCommand.TEACHER_ID,
                                CreateSchoolClassCommandValidator.MESSAGE_KEY_ACCOUNT_INACTIVE,
                                ErrorCode.ACCOUNT_INACTIVE));
    }

}