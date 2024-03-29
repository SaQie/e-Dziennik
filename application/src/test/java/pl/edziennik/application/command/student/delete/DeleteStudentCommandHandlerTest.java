package pl.edziennik.application.command.student.delete;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class DeleteStudentCommandHandlerTest extends BaseUnitTest {

    private final DeleteStudentCommandHandler handler;

    public DeleteStudentCommandHandlerTest() {
        this.handler = new DeleteStudentCommandHandler(studentCommandRepository, resourceCreator);
    }

    @Test
    public void shouldThrowExceptionWhenStudentNotExists() {
        // given
        StudentId studentId = StudentId.create();

        DeleteStudentCommand command = new DeleteStudentCommand(studentId);
        // when
        // then
        Assertions.assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertNotFoundMessage());

    }

    @Test
    public void shouldDeleteStudent() {
        // given
        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());

        School school = createSchool("Testowa", "123123", "123123", address);
        school = schoolCommandRepository.save(school);

        SchoolClass schoolClass = createSchoolClass("Test", school, null);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        Student studentAfterSave = studentCommandRepository.getReferenceById(student.studentId());
        assertNotNull(studentAfterSave);

        DeleteStudentCommand command = new DeleteStudentCommand(student.studentId());

        // when
        handler.handle(command);

        // then
        Student studentAfterDelete = studentCommandRepository.getReferenceById(student.studentId());
        assertNull(studentAfterDelete);
    }
}