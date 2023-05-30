package pl.edziennik.application.integration;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.student.create.CreateStudentCommand;
import pl.edziennik.application.command.user.activate.ActivateUserCommand;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.*;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.user.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext
public class UserIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldCreateUserAccountAndSetIsActiveToFalse() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "23123");
        TeacherId teacherId = createTeacher("test", "test@example.com", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("123123123"),
                Email.of("test1@example.com"),
                PhoneNumber.of("123123"),
                schoolId,
                schoolClassId
        );

        // when
        OperationResult operationResult = dispatcher.dispatch(command);

        // then
        Student student = studentCommandRepository.getByStudentId(StudentId.of(operationResult.identifier().id()));
        UserId userId = student.getUser().getUserId();
        User user = userQueryRepository.getUserByUserId(userId);
        assertNotNull(user);
        assertFalse(user.getIsActive());
    }

    @Test
    public void shouldActivateUserAccount() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "23123");
        TeacherId teacherId = createTeacher("test", "test@example.com", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("123123123"),
                Email.of("test1@example.com"),
                PhoneNumber.of("123123"),
                schoolId,
                schoolClassId
        );
        OperationResult result = dispatcher.dispatch(command);
        StudentId studentId = StudentId.of(result.identifier().id());

        Student student = studentCommandRepository.getByStudentId(studentId);
        UserId userId = student.getUser().getUserId();

        String response = jdbcTemplate.queryForObject(
                "SELECT eat.token FROM email_activation_tokens eat where eat.user_id = :userId",
                new MapSqlParameterSource("userId", userId.id()), String.class);

        Token token = Token.of(response);

        ActivateUserCommand activateUserCommand = new ActivateUserCommand(token);
        // when
        dispatcher.dispatch(activateUserCommand);

        // then
        User user = userQueryRepository.getUserByUserId(userId);
        assertTrue(user.getIsActive());

        List<String> list = jdbcTemplate.queryForList(
                "SELECT eat.token FROM email_activation_tokens eat",
                new MapSqlParameterSource(), String.class);

        assertEquals(list.size(), 0);


    }


}
