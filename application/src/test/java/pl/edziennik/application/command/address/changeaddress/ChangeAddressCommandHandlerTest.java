package pl.edziennik.application.command.address.changeaddress;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.Address;
import pl.edziennik.common.valueobject.City;
import pl.edziennik.common.valueobject.PostalCode;
import pl.edziennik.domain.director.Director;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeAddressCommandHandlerTest extends BaseUnitTest {

    private final ChangeAddressCommandHandler handler;

    public ChangeAddressCommandHandlerTest() {
        this.handler = new ChangeAddressCommandHandler(schoolCommandRepository,
                studentCommandRepository,
                teacherCommandRepository,
                parentCommandRepository,
                directorCommandRepository,
                resourceCreator);
    }

    @Test
    public void shouldThrowExceptionWhenStudentNotExists() {
        // given
        ChangeAddressCommand command = new ChangeAddressCommand(UUID.randomUUID(), null, null, null, ChangeAddressCommand.CommandFor.STUDENT);

        // when
        // then
        Assertions.assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertNotFoundMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTeacherNotExists() {
        // given
        ChangeAddressCommand command = new ChangeAddressCommand(UUID.randomUUID(), null, null, null, ChangeAddressCommand.CommandFor.TEACHER);

        // when
        // then
        Assertions.assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertNotFoundMessage());
    }

    @Test
    public void shouldThrowExceptionWhenParentNotExists() {
        // given
        ChangeAddressCommand command = new ChangeAddressCommand(UUID.randomUUID(), null, null, null, ChangeAddressCommand.CommandFor.PARENT);

        // when
        // then
        Assertions.assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertNotFoundMessage());
    }

    @Test
    public void shouldThrowExceptionWhenSchoolNotExists() {
        // given
        ChangeAddressCommand command = new ChangeAddressCommand(UUID.randomUUID(), null, null, null, ChangeAddressCommand.CommandFor.SCHOOL);

        // when
        // then
        Assertions.assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertNotFoundMessage());
    }

    @Test
    public void shouldChangeDirectorAddressData() {
        // given
        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        School school = createSchool("test", "123123", "123123", address);
        Director director = createDirector(user, school, personInformation, address);

        ChangeAddressCommand command = new ChangeAddressCommand(director.getDirectorId().id(),
                Address.of("Test"),
                City.of("Test2"),
                PostalCode.of("11-111"),
                ChangeAddressCommand.CommandFor.DIRECTOR);

        // when
        handler.handle(command);

        // then
        director = directorCommandRepository.getByDirectorId(director.getDirectorId());
        assertEquals(director.getAddress().getAddress().value(), "Test");
        assertEquals(director.getAddress().getCity().value(), "Test2");
        assertEquals(director.getAddress().getPostalCode().value(), "11-111");
    }

    @Test
    public void shouldChangeStudentAddressData() {
        // given
        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudentWithSchoolAndClass(user, null);

        ChangeAddressCommand command = new ChangeAddressCommand(student.getStudentId().id(),
                Address.of("Test"),
                City.of("Test2"),
                PostalCode.of("11-111"),
                ChangeAddressCommand.CommandFor.STUDENT);

        // when
        handler.handle(command);

        // then
        student = studentCommandRepository.getByStudentId(student.getStudentId());
        assertEquals(student.getAddress().getAddress().value(), "Test");
        assertEquals(student.getAddress().getCity().value(), "Test2");
        assertEquals(student.getAddress().getPostalCode().value(), "11-111");
    }

    @Test
    public void shouldChangeTeacherAddressData() {
        // given
        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        School school = createSchool("Test", "123123", "123123", address);
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacherCommandRepository.save(teacher);

        ChangeAddressCommand command = new ChangeAddressCommand(teacher.getTeacherId().id(),
                Address.of("Test"),
                City.of("Test2"),
                PostalCode.of("11-111"),
                ChangeAddressCommand.CommandFor.TEACHER);

        // when
        handler.handle(command);

        // then
        teacher = teacherCommandRepository.getByTeacherId(teacher.getTeacherId());
        assertEquals(teacher.getAddress().getAddress().value(), "Test");
        assertEquals(teacher.getAddress().getCity().value(), "Test2");
        assertEquals(teacher.getAddress().getPostalCode().value(), "11-111");
    }

    @Test
    public void shouldChangeParentAddressData() {
        // given
        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Parent parent = createParent(user, personInformation, address);
        parentCommandRepository.save(parent);

        ChangeAddressCommand command = new ChangeAddressCommand(parent.getParentId().id(),
                Address.of("Test"),
                City.of("Test2"),
                PostalCode.of("11-111"),
                ChangeAddressCommand.CommandFor.PARENT);

        // when
        handler.handle(command);

        // then
        parent = parentCommandRepository.getReferenceById(parent.getParentId());
        assertEquals(parent.getAddress().getAddress().value(), "Test");
        assertEquals(parent.getAddress().getCity().value(), "Test2");
        assertEquals(parent.getAddress().getPostalCode().value(), "11-111");
    }

    @Test
    public void shouldChangeSchoolAddressData() {
        // given
        School school = createSchool("Test", "123123", "123123", address);
        schoolCommandRepository.save(school);

        ChangeAddressCommand command = new ChangeAddressCommand(school.getSchoolId().id(),
                Address.of("Test"),
                City.of("Test2"),
                PostalCode.of("11-111"),
                ChangeAddressCommand.CommandFor.SCHOOL);

        // when
        handler.handle(command);

        // then
        school = schoolCommandRepository.getBySchoolId(school.getSchoolId());
        assertEquals(school.getAddress().getAddress().value(), "Test");
        assertEquals(school.getAddress().getCity().value(), "Test2");
        assertEquals(school.getAddress().getPostalCode().value(), "11-111");
    }


}
