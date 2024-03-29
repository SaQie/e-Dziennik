package pl.edziennik.application.command.school.delete;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.PersonInformation;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteSchoolCommandValidatorTest extends BaseUnitTest {

    private final DeleteSchoolCommandValidator validator;

    public DeleteSchoolCommandValidatorTest() {
        this.validator = new DeleteSchoolCommandValidator(schoolCommandRepository);
    }

    @Test
    public void shouldThrowErrorWhenSchoolNotExists() {
        // given
        DeleteSchoolCommand command = new DeleteSchoolCommand(SchoolId.create());

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(
                                DeleteSchoolCommand.SCHOOL_ID));
    }

    @Test
    public void shouldAddErrorWhenTeachersStillExistsInSchool() {
        // given
        School school = createSchool("School", "123123", "123123", address);

        schoolCommandRepository.save(school);

        User user = createUser("Kamil", "qweqwe@o2.pl", "TEACHER");
        PersonInformation personInformation = createPersonInformation();
        createTeacher(user, school, personInformation, address);

        DeleteSchoolCommand deleteSchoolCommand = new DeleteSchoolCommand(school.schoolId());

        // when
        validator.validate(deleteSchoolCommand, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(errors.get(0).field(), DeleteSchoolCommand.SCHOOL_ID);
        assertEquals(errors.get(0).message(), DeleteSchoolCommandValidator.MESSAGE_KEY_CANNOT_DELETE_SCHOOL_BECAUSE_OF_TEACHER_EXISTS);
    }

    @Test
    public void shouldAddErrorWhenStudentsStillExistsInSchool() {
        // given
        School school = createSchool("School", "123123", "123123", address);
        schoolCommandRepository.save(school);

        User user = createUser("Kamil", "qweqwe@o2.pl", "TEACHER");
        PersonInformation personInformation = createPersonInformation();
        createStudent(user, school, SchoolClass.of(null, null, null,null, schoolClassConfigurationProperties), personInformation, address);

        DeleteSchoolCommand deleteSchoolCommand = new DeleteSchoolCommand(school.schoolId());

        // when
        validator.validate(deleteSchoolCommand, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(errors.get(0).field(), DeleteSchoolCommand.SCHOOL_ID);
        assertEquals(errors.get(0).message(), DeleteSchoolCommandValidator.MESSAGE_KEY_CANNOT_DELETE_SCHOOL_BECAUSE_OF_STUDENT_EXISTS);
    }

    @Test
    public void shouldAddErrorWhenSchoolClassesStillExistsInSchool() {
        // given
        School school = createSchool("School", "123123", "123123", address);

        schoolCommandRepository.save(school);

        User user = createUser("Kamil", "qweqwe@o2.pl", "TEACHER");
        createSchoolClass("test", school, null);

        DeleteSchoolCommand deleteSchoolCommand = new DeleteSchoolCommand(school.schoolId());

        // when
        validator.validate(deleteSchoolCommand, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(errors.get(0).field(), DeleteSchoolCommand.SCHOOL_ID);
        assertEquals(errors.get(0).message(), DeleteSchoolCommandValidator.MESSAGE_KEY_CANNOT_DELETE_SCHOOL_BECAUSE_OF_SCHOOL_CLASS_EXISTS);
    }

}
