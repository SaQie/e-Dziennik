package pl.edziennik.application.command.grademanagment.unassign;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.id.GradeId;
import pl.edziennik.common.valueobject.vo.Weight;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import org.junit.jupiter.api.Test;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.util.List;

class UnassignGradeFromStudentSubjectHandlerTest extends BaseUnitTest {

    private final UnassignGradeFromStudentSubjectCommandHandler handler;

    public UnassignGradeFromStudentSubjectHandlerTest() {
        this.handler = new UnassignGradeFromStudentSubjectCommandHandler(gradeCommandRepository, resourceCreator);
    }

    @Test
    public void shouldThrowExceptionIfGradeNotExists() {
        // given
        UnassignGradeFromStudentSubjectCommand command = new UnassignGradeFromStudentSubjectCommand(GradeId.create());

        // when
        // then
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertNotFoundMessage());
    }

    @Test
    public void shouldUnassignGradeFromStudentSubject() {
        // given
        SchoolClass schoolClass = createSchoolWithSchoolClass();
        School school = schoolClass.school();
        Teacher teacher = schoolClass.teacher();
        User user = createUser("TESTOWY", "test@examplee.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);
        Subject subject = createSubject("Przyroda", teacher, schoolClass);
        StudentSubject studentSubject = createStudentSubject(student, subject);
        pl.edziennik.domain.grade.Grade grade = assignGradeToStudentSubject(studentSubject, teacher, Grade.FIVE, Weight.of(5));

        UnassignGradeFromStudentSubjectCommand command = new UnassignGradeFromStudentSubjectCommand(grade.gradeId());

        List<pl.edziennik.domain.grade.Grade> gradesBySubjectId =
                gradeCommandRepository.getGradesBySubjectId(subject.subjectId());

        Assertions.assertEquals(gradesBySubjectId.size(), 1);

        // when
        handler.handle(command);

        // then
        gradesBySubjectId =
                gradeCommandRepository.getGradesBySubjectId(subject.subjectId());
        Assertions.assertEquals(gradesBySubjectId.size(), 0);
    }
}
