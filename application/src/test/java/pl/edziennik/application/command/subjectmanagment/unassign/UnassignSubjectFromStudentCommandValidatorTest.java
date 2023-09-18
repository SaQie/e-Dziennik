package pl.edziennik.application.command.subjectmanagment.unassign;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

class UnassignSubjectFromStudentCommandValidatorTest extends BaseUnitTest {

    private final UnassignStudentFromSubjectCommandValidator validator;

    public UnassignSubjectFromStudentCommandValidatorTest() {
        this.validator = new UnassignStudentFromSubjectCommandValidator(studentSubjectCommandRepository,
                subjectCommandRepository, studentCommandRepository);
    }

    @Test
    public void shouldThrowExceptionIfStudentNotExists() {
        // given
        SchoolClass schoolClass = createSchoolWithSchoolClass();
        School school = schoolClass.school();

        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudent(user, school, schoolClass, personInformation, address);

        user = createUser("Test2", "test2@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        Teacher teacher = createTeacher(user, school, personInformation, address);
        Subject subject = createSubject("Przyroda", teacher, schoolClass);

        UnassignStudentFromSubjectCommand command = new UnassignStudentFromSubjectCommand(StudentId.create(), subject.subjectId());


        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertBusinessExceptionMessage(UnassignStudentFromSubjectCommand.STUDENT_ID));
    }

    @Test
    public void shouldThrowExceptionIfSubjectNotExists() {
        // given
        SchoolClass schoolClass = createSchoolWithSchoolClass();
        School school = schoolClass.school();

        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        UnassignStudentFromSubjectCommand command = new UnassignStudentFromSubjectCommand(student.studentId(), SubjectId.create());

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertBusinessExceptionMessage(UnassignStudentFromSubjectCommand.SUBJECT_ID));
    }
}
