package pl.edziennik.application.command.grademanagment.assigngrade;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.Weight;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
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
        School school = schoolClass.school();
        Teacher teacher = schoolClass.teacher();

        Subject subject = createSubject("Przyroda", teacher, schoolClass);
        subject = subjectCommandRepository.save(subject);

        AssignGradeToStudentSubjectCommand command = new AssignGradeToStudentSubjectCommand(
                StudentId.create(),
                subject.subjectId(),
                Grade.ONE,
                Weight.of(5),
                Description.of("Test"),
                teacher.teacherId()
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
        School school = schoolClass.school();
        Teacher teacher = schoolClass.teacher();

        User user = createUser("Testowy", "Test1@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        AssignGradeToStudentSubjectCommand command = new AssignGradeToStudentSubjectCommand(
                student.studentId(),
                SubjectId.create(),
                Grade.ONE,
                Weight.of(5),
                Description.of("Test"),
                teacher.teacherId()
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
        School school = schoolClass.school();
        Teacher teacher = schoolClass.teacher();

        User user = createUser("Testowy", "Test1@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        Subject subject = createSubject("Przyroda", teacher, schoolClass);
        subject = subjectCommandRepository.save(subject);

        AssignGradeToStudentSubjectCommand command = new AssignGradeToStudentSubjectCommand(
                student.studentId(),
                subject.subjectId(),
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
        School school = schoolClass.school();
        Teacher teacher = schoolClass.teacher();

        User user = createUser("Testowy", "Test1@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        user.activate();
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        Subject subject = createSubject("Przyroda", teacher, schoolClass);
        subject = subjectCommandRepository.save(subject);

        AssignGradeToStudentSubjectCommand command = new AssignGradeToStudentSubjectCommand(
                student.studentId(),
                subject.subjectId(),
                Grade.ONE,
                Weight.of(5),
                Description.of("Test"),
                teacher.teacherId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(AssignGradeToStudentSubjectCommand.SUBJECT_ID, errors.get(0).field());
        assertEquals(AssignGradeToStudentSubjectCommandValidator.MESSAGE_KEY_STUDENT_SUBJECT_NOT_EXISTS, errors.get(0).message());
    }

    @Test
    public void shouldAddErrorWhenTeacherAccountIsInactive() {
        // given
        SchoolClass schoolClass = createSchoolWithSchoolClass();
        School school = schoolClass.school();
        Teacher teacher = schoolClass.teacher();
        User user = teacher.user();
        user.unactivate();

        user = createUser("Testowy", "Test1@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        user.activate();
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        Subject subject = createSubject("Przyroda", teacher, schoolClass);
        subject = subjectCommandRepository.save(subject);

        StudentSubject studentSubject = createStudentSubject(student, subject);
        studentSubject = studentSubjectCommandRepository.save(studentSubject);

        AssignGradeToStudentSubjectCommand command = new AssignGradeToStudentSubjectCommand(
                student.studentId(),
                subject.subjectId(),
                Grade.ONE,
                Weight.of(5),
                Description.of("Test"),
                teacher.teacherId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(AssignGradeToStudentSubjectCommand.TEACHER_ID, errors.get(0).field());
        assertEquals(AssignGradeToStudentSubjectCommandValidator.MESSAGE_KEY_ACCOUNT_INACTIVE, errors.get(0).message());


    }


    @Test
    public void shouldAddErrorWhenStudentAccountIsInactive() {
        // given
        SchoolClass schoolClass = createSchoolWithSchoolClass();
        School school = schoolClass.school();
        Teacher teacher = schoolClass.teacher();

        User user = createUser("Testowy", "Test1@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        Subject subject = createSubject("Przyroda", teacher, schoolClass);
        subject = subjectCommandRepository.save(subject);

        StudentSubject studentSubject = createStudentSubject(student, subject);
        studentSubject = studentSubjectCommandRepository.save(studentSubject);

        AssignGradeToStudentSubjectCommand command = new AssignGradeToStudentSubjectCommand(
                student.studentId(),
                subject.subjectId(),
                Grade.ONE,
                Weight.of(5),
                Description.of("Test"),
                teacher.teacherId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(AssignGradeToStudentSubjectCommand.STUDENT_ID, errors.get(0).field());
        assertEquals(AssignGradeToStudentSubjectCommandValidator.MESSAGE_KEY_ACCOUNT_INACTIVE, errors.get(0).message());


    }

}