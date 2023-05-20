package pl.edziennik.application.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.student.create.CreateStudentCommand;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.student.Student;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class StudentIntegrationTest extends BaseIntegrationTest {


    @Test
    public void shouldCreateStudent(){
        // given
        SchoolId schoolId = createSchool("Testowa", "123123", "123123");
        TeacherId teacherId = createTeacher("Teacher", "test@example.com", "123123123", schoolId);
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
                Email.of("test@example.com"),
                PhoneNumber.of("123123"),
                schoolId,
                schoolClassId
        );

        // when
        OperationResult operationResult = dispatcher.dispatch(command);

        // then
        Student student = studentCommandRepository.getByStudentId(StudentId.of(operationResult.identifier().id()));
        assertNotNull(student);
    }

    @Test
    public void shouldThrowExceptionIfStudentWithUsernameOrEmailOrPeselAlreadyExists() {
        // given
        SchoolId schoolId = createSchool("Test", "123123123", "123123123");
        TeacherId teacherId = createTeacher("Teacher", "test1@example.com", "129292", schoolId);

        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        createStudent("Test", "test@example.com", "123123123", schoolId, schoolClassId);

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("123123123"),
                Email.of("test@example.com"),
                PhoneNumber.of("123123"),
                schoolId,
                schoolClassId
        );

        try {
            // when
            dispatcher.dispatch(command);
            Assertions.fail("Should throw exception when student with given name or email or pesel already exists");
        }catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(3, errors.size());
            assertThat(errors)
                    .extracting(ValidationError::field)
                    .containsExactlyInAnyOrder(CreateStudentCommand.USERNAME, CreateStudentCommand.PESEL, CreateStudentCommand.EMAIL);

        }

    }

}
