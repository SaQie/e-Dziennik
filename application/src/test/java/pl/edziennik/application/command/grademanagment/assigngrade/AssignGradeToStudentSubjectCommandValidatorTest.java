package pl.edziennik.application.command.grademanagment.assigngrade;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.Description;
import pl.edziennik.common.valueobject.Weight;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssignGradeToStudentSubjectCommandValidatorTest extends BaseUnitTest {

    private final AssignGradeToStudentSubjectCommandValidator validator;

    public AssignGradeToStudentSubjectCommandValidatorTest() {
        this.validator = new AssignGradeToStudentSubjectCommandValidator(studentSubjectCommandRepository,
                studentCommandRepository,
                subjectCommandRepository,
                teacherCommandRepository);
    }

    @Test
    public void shouldThrowExceptionWhenStudentNotExists() {
        // given
        SchoolClass schoolClass = createSchoolWithSchoolClass();
        School school = schoolClass.getSchool();
        Teacher teacher = schoolClass.getTeacher();

        Subject subject = createSubject("Przyroda", teacher, schoolClass);
        subject = subjectCommandRepository.save(subject);

        AssignGradeToStudentSubjectCommand command = new AssignGradeToStudentSubjectCommand(
                StudentId.create(),
                subject.getSubjectId(),
                Grade.ONE,
                Weight.of(5),
                Description.of("Test"),
                teacher.getTeacherId()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(AssignGradeToStudentSubjectCommand.STUDENT_ID));
    }

    @Test
    public void shouldThrowExceptionWhenSubjectNotExists() {
        // given
        SchoolClass schoolClass = createSchoolWithSchoolClass();
        School school = schoolClass.getSchool();
        Teacher teacher = schoolClass.getTeacher();

        User user = createUser("Testowy", "Test1@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        AssignGradeToStudentSubjectCommand command = new AssignGradeToStudentSubjectCommand(
                student.getStudentId(),
                SubjectId.create(),
                Grade.ONE,
                Weight.of(5),
                Description.of("Test"),
                teacher.getTeacherId()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(AssignGradeToStudentSubjectCommand.SUBJECT_ID));
    }

    @Test
    public void shouldThrowExceptionWhenTeacherNotExists() {
        // given
        SchoolClass schoolClass = createSchoolWithSchoolClass();
        School school = schoolClass.getSchool();
        Teacher teacher = schoolClass.getTeacher();

        User user = createUser("Testowy", "Test1@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        Subject subject = createSubject("Przyroda", teacher, schoolClass);
        subject = subjectCommandRepository.save(subject);

        AssignGradeToStudentSubjectCommand command = new AssignGradeToStudentSubjectCommand(
                student.getStudentId(),
                subject.getSubjectId(),
                Grade.ONE,
                Weight.of(5),
                Description.of("Test"),
                TeacherId.create()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(AssignGradeToStudentSubjectCommand.TEACHER_ID));
    }

    @Test
    public void shouldAddErrorWhenStudentIsNotAssignedToSubject() {
        // given
        SchoolClass schoolClass = createSchoolWithSchoolClass();
        School school = schoolClass.getSchool();
        Teacher teacher = schoolClass.getTeacher();

        User user = createUser("Testowy", "Test1@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        Subject subject = createSubject("Przyroda", teacher, schoolClass);
        subject = subjectCommandRepository.save(subject);

        AssignGradeToStudentSubjectCommand command = new AssignGradeToStudentSubjectCommand(
                student.getStudentId(),
                subject.getSubjectId(),
                Grade.ONE,
                Weight.of(5),
                Description.of("Test"),
                teacher.getTeacherId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), AssignGradeToStudentSubjectCommand.SUBJECT_ID);
        assertEquals(errors.get(0).errorMessage(), AssignGradeToStudentSubjectCommandValidator.MESSAGE_KEY_STUDENT_SUBJECT_NOT_EXISTS);
    }

}