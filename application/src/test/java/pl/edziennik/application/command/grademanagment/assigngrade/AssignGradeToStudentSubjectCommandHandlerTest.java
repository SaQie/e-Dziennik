package pl.edziennik.application.command.grademanagment.assigngrade;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.Description;
import pl.edziennik.common.valueobject.Weight;
import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;

import static org.junit.jupiter.api.Assertions.*;

class AssignGradeToStudentSubjectCommandHandlerTest extends BaseUnitTest {

    private final AssignGradeToStudentSubjectCommandHandler handler;

    public AssignGradeToStudentSubjectCommandHandlerTest() {
        this.handler = new AssignGradeToStudentSubjectCommandHandler(studentSubjectCommandRepository,
                teacherCommandRepository,
                gradeCommandRepository);
    }


    @Test
    public void shouldAssignGradeToStudentSubject() {
        // given
        SchoolClass schoolClass = createSchoolWithSchoolClass();
        School school = schoolClass.getSchool();
        Teacher teacher = schoolClass.getTeacher();

        User user = createUser("Kamcio", "Test@exampleee.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        Subject subject = createSubject("Przyroda", teacher, schoolClass);
        subject = subjectCommandRepository.save(subject);

        StudentSubject studentSubject = createStudentSubject(student, subject);
        studentSubject = studentSubjectCommandRepository.save(studentSubject);

        AssignGradeToStudentSubjectCommand command = new AssignGradeToStudentSubjectCommand(
                student.getStudentId(),
                subject.getSubjectId(),
                Grade.FIVE,
                Weight.of(5),
                Description.of("Test"),
                teacher.getTeacherId()
        );
        // when
        OperationResult operationResult = handler.handle(command);

        // then
        assertTrue(operationResult.isSuccess());
        pl.edziennik.domain.grade.Grade grade = gradeCommandRepository.getReferenceById(GradeId.of(operationResult.identifier().id()));
        assertNotNull(grade);
        assertEquals(grade.getStudentSubject().getStudentSubjectId(), studentSubject.getStudentSubjectId());

    }

}