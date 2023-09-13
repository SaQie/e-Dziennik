package pl.edziennik.application.command.subjectmanagment.assigntostudent;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.user.User;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssignSubjectToStudentCommandHandlerTest extends BaseUnitTest {

    private final AssignSubjectToStudentCommandHandler handler;

    public AssignSubjectToStudentCommandHandlerTest() {
        this.handler = new AssignSubjectToStudentCommandHandler(studentSubjectCommandRepository, studentCommandRepository, subjectCommandRepository);
    }

    @Test
    public void shouldAssignSubjectToStudent() {
        // given
        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudentWithSchoolAndClass(user, null);

        School school = createSchool("Testowa1", "12344242", "24242323", address);
        school = schoolCommandRepository.save(school);

        SchoolClass schoolClass = createSchoolClass("Testowa4", school, null);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        Subject subject = createSubject("Testowy", null, schoolClass);
        subject = subjectCommandRepository.save(subject);

        boolean isStudentAssignedToSubject = studentSubjectCommandRepository.isStudentAlreadyAssignedToSubject(student.studentId(), subject.subjectId());
        assertFalse(isStudentAssignedToSubject);

        AssignSubjectToStudentCommand command = new AssignSubjectToStudentCommand(
                student.studentId(),
                subject.subjectId()
        );

        // when
        handler.handle(command);

        // then
        isStudentAssignedToSubject = studentSubjectCommandRepository.isStudentAlreadyAssignedToSubject(student.studentId(), subject.subjectId());
        assertTrue(isStudentAssignedToSubject);
    }
}
