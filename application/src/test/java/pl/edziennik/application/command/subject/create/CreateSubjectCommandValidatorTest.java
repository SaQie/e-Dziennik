package pl.edziennik.application.command.subject.create;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.Description;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateSubjectCommandValidatorTest extends BaseUnitTest {

    private final CreateSubjectCommandValidator validator;

    public CreateSubjectCommandValidatorTest() {
        this.validator = new CreateSubjectCommandValidator(subjectCommandRepository, teacherCommandRepository, schoolClassCommandRepository);
    }

    @Test
    public void shouldThrowExceptionWhenTeacherNotExists() {
        // given
        School school = createSchool("TEST", "123123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        SchoolClass schoolClass = createSchoolClass("1A", school, null);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        CreateSubjectCommand command = new CreateSubjectCommand(
                Name.of("Test"),
                Description.of("Test"),
                TeacherId.create(),
                schoolClass.getSchoolClassId()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertBusinessExceptionMessage(CreateSubjectCommand.TEACHER_ID));

    }

    @Test
    public void shouldThrowExceptionWhenSchoolClassNotExists() {
        // given
        School school = createSchool("TEST", "123123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);

        CreateSubjectCommand command = new CreateSubjectCommand(
                Name.of("Test"),
                Description.of("Test"),
                teacher.getTeacherId(),
                SchoolClassId.create()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertBusinessExceptionMessage(CreateSubjectCommand.SCHOOL_CLASS_ID));
    }

    @Test
    public void shouldAddErrorWhenSubjectAlreadyExistsByName() {
        // given
        School school = createSchool("Test", "123123132", "123123", address);
        school = schoolCommandRepository.save(school);

        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);

        SchoolClass schoolClass = createSchoolClass("1A", school, teacher);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        Subject subject = createSubject("Przyroda", teacher, schoolClass);
        subject = subjectCommandRepository.save(subject);

        CreateSubjectCommand command = new CreateSubjectCommand(
                Name.of("Przyroda"),
                Description.of("Test"),
                teacher.getTeacherId(),
                schoolClass.getSchoolClassId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateSubjectCommand.NAME);
        assertEquals(errors.get(0).errorMessage(), CreateSubjectCommandValidator.MESSAGE_KEY_SUBJECT_ALREADY_EXISTS);
    }

    @Test
    public void shouldAddErrorWhenTeacherIsFromAnotherSchool() {
        // given
        SchoolClass schoolClass = createSchoolWithSchoolClass();
        School school = schoolClass.getSchool();

        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);

        Subject subject = createSubject("Przyroda", teacher, schoolClass);
        subject = subjectCommandRepository.save(subject);

        CreateSubjectCommand command = new CreateSubjectCommand(
                Name.of("Test"),
                Description.of("Test"),
                teacher.getTeacherId(),
                schoolClass.getSchoolClassId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateSubjectCommand.TEACHER_ID);
        assertEquals(errors.get(0).errorMessage(), CreateSubjectCommandValidator.MESSAGE_KEY_TEACHER_IS_FROM_ANOTHER_SCHOOL);
    }


}
