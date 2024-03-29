package pl.edziennik.application.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.teacher.create.CreateTeacherCommand;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.*;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
public class TeacherIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldCreateTeacher() {
        // given
        SchoolId schoolId = createSchool("Test", "12312313", "123123132");

        CreateTeacherCommand command = new CreateTeacherCommand(
                Password.of("password"),
                Username.of("Test"),
                FirstName.of("Test"),
                LastName.of("Test"),
                Address.of("address"),
                PostalCode.of("12-12"),
                City.of("Test"),
                Pesel.of("123123123"),
                Email.of("Test@example.com"),
                PhoneNumber.of("123000000"),
                schoolId
        );

        // when
        dispatcher.run(command);

        // then
        Teacher teacher = teacherCommandRepository.getByTeacherId(command.teacherId());
        Assertions.assertNotNull(teacher);
    }

    @Test
    public void shouldThrowExceptionIfTeacherWithUsernameOrPeselOrEmailAlreadyExists() {
        // given
        SchoolId schoolId = createSchool("Test", "123123123", "12312312");
        createTeacher("Test", "Test@example.com", "123123123", schoolId);

        CreateTeacherCommand command = new CreateTeacherCommand(
                Password.of("password"),
                Username.of("Test"),
                FirstName.of("Test"),
                LastName.of("Test"),
                Address.of("address"),
                PostalCode.of("12-12"),
                City.of("Test"),
                Pesel.of("123123123"),
                Email.of("Test@example.com"),
                PhoneNumber.of("123000000"),
                schoolId
        );

        try {
            // when
            dispatcher.run(command);
            Assertions.fail("Should throw exception when Teacher with given username or email or pesel already exists");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(3, errors.size());
            assertThat(errors)
                    .extracting(ValidationError::field)
                    .containsExactlyInAnyOrder(CreateTeacherCommand.USERNAME, CreateTeacherCommand.PESEL, CreateTeacherCommand.EMAIL);
        }

    }

}
