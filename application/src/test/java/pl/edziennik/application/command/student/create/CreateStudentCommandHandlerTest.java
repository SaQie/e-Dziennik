package pl.edziennik.application.command.student.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.domain.student.Student;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateStudentCommandHandlerTest extends BaseUnitTest {

    private final CreateStudentCommandHandler handler;

    public CreateStudentCommandHandlerTest() {
        this.handler = new CreateStudentCommandHandler(schoolClassCommandRepository,
                schoolCommandRepository,
                studentCommandRepository,
                roleCommandRepository,
                passwordEncoder);
    }



    @Test
    public void shouldCreateStudent(){
        // given
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
                SchoolId.create(),
                SchoolClassId.create()
        );

        // when
        OperationResult operationResult = handler.handle(command);

        // then
        Student student = studentCommandRepository.getReferenceById(StudentId.of(operationResult.identifier().id()));
        assertNotNull(student);
    }
}