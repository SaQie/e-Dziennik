package pl.edziennik.application.command.grademanagment.assign;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.Weight;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AssignGradeToStudentSubjectCommandHandlerTest extends BaseUnitTest {

    private final AssignGradeToStudentSubjectCommandHandler handler;

    public AssignGradeToStudentSubjectCommandHandlerTest() {
        this.handler = new AssignGradeToStudentSubjectCommandHandler(studentSubjectCommandRepository,
                teacherCommandRepository,
                gradeCommandRepository,
                publisher);
    }


    @Test
    public void shouldAssignGradeToStudentSubject() {
        // given
        SchoolClass schoolClass = createSchoolWithSchoolClass();
        School school = schoolClass.school();
        Teacher teacher = schoolClass.teacher();

        User user = createUser("Kamcio", "Test@exampleee.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        Subject subject = createSubject("Przyroda", teacher, schoolClass);

        StudentSubject studentSubject = createStudentSubject(student, subject);

        AssignGradeToStudentSubjectCommand command = new AssignGradeToStudentSubjectCommand(
                student.studentId(),
                subject.subjectId(),
                Grade.FIVE,
                Weight.of(5),
                Description.of("Test"),
                teacher.teacherId()
        );
        // when
        handler.handle(command);

        // then
        pl.edziennik.domain.grade.Grade grade = gradeCommandRepository.getReferenceById(command.gradeId());
        assertNotNull(grade);
        assertEquals(grade.studentSubject().studentSubjectId(), studentSubject.studentSubjectId());

    }

}