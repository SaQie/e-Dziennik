package pl.edziennik.application.command.student.assignparent;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssignParentCommandValidatorTest extends BaseUnitTest {

    private final AssignParentCommandValidator validator;

    public AssignParentCommandValidatorTest() {
        this.validator = new AssignParentCommandValidator(parentCommandRepository, studentCommandRepository);
    }

    @Test
    public void shouldThrowExceptionWhenStudentNotExists() {
        // given
        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.PARENT_ROLE_NAME.value());
        Parent parent = createParent(user, personInformation, address);

        parent = parentCommandRepository.save(parent);

        AssignParentCommand command = new AssignParentCommand(
                StudentId.create(),
                parent.getParentId()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertBusinessExceptionMessage(AssignParentCommand.STUDENT_ID));
    }

    @Test
    public void shouldThrowExceptionWhenParentNotExists() {
        // given
        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.PARENT_ROLE_NAME.value());
        School school = createSchool("test", "1231231", "123123", address);
        SchoolClass schoolClass = createSchoolClass("Test", school, null);

        school = schoolCommandRepository.save(school);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        Student student = createStudent(user, school, schoolClass, personInformation, address);

        student = studentCommandRepository.save(student);

        AssignParentCommand command = new AssignParentCommand(
                student.getStudentId(),
                ParentId.create()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertBusinessExceptionMessage(AssignParentCommand.PARENT_ID));
    }

    @Test
    public void shouldAddErrorWhenParentAlreadyHasAssignedStudent() {
        // given
        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudentWithSchoolAndClass(user, null);

        User parentUser = createUser("Test2", "Test2@example.com", RoleCommandMockRepo.PARENT_ROLE_NAME.value());
        Parent parent = createParent(parentUser, personInformation, address, student);
        parent = parentCommandRepository.save(parent);

        AssignParentCommand command = new AssignParentCommand(
                student.getStudentId(),
                parent.getParentId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), AssignParentCommand.PARENT_ID);
        assertEquals(errors.get(0).errorMessage(), AssignParentCommandValidator.MESSAGE_KEY_PARENT_ALREADY_HAS_STUDENT);
    }

    @Test
    public void shouldAddErrorWhenStudentAlreadyHasAssignedParent() {
        // given
        User parentUser = createUser("Test2", "Test2@example.com", RoleCommandMockRepo.PARENT_ROLE_NAME.value());
        Parent parent = createParent(parentUser, personInformation, address);
        parentCommandRepository.save(parent);

        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudentWithSchoolAndClass(user, parent);


        AssignParentCommand command = new AssignParentCommand(
                student.getStudentId(),
                parent.getParentId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), AssignParentCommand.STUDENT_ID);
        assertEquals(errors.get(0).errorMessage(), AssignParentCommandValidator.MESSAGE_KEY_STUDENT_ALREADY_HAS_PARENT);
    }
}
