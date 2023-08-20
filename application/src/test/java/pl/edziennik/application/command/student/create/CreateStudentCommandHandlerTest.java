package pl.edziennik.application.command.student.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.vo.*;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateStudentCommandHandlerTest extends BaseUnitTest {

    private final CreateStudentCommandHandler handler;

    public CreateStudentCommandHandlerTest() {
        this.handler = new CreateStudentCommandHandler(schoolClassCommandRepository,
                schoolCommandRepository,
                studentCommandRepository,
                roleCommandRepository,
                passwordEncoder,
                publisher);
    }



    @Test
    public void shouldCreateStudent(){
        // given
        School school = createSchool("Test", "1231231", "123123", address);
        school = schoolCommandRepository.save(school);

        User user = createUser("Test", "test", "test");
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);

        SchoolClass schoolClass = createSchoolClass("test", school, teacher);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

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
                schoolClass.schoolClassId()
        );

        // when
        OperationResult operationResult = handler.handle(command);

        // then
        Student student = studentCommandRepository.getReferenceById(StudentId.of(operationResult.identifier().id()));
        assertNotNull(student);
    }
}