package pl.edziennik.application.command.subjectmanagment.assigntostudent;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;
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

class AssignSubjectToStudentCommandValidatorTest extends BaseUnitTest {

    private final AssignSubjectToStudentCommandValidator validator;

    public AssignSubjectToStudentCommandValidatorTest() {
        this.validator = new AssignSubjectToStudentCommandValidator(subjectCommandRepository, studentCommandRepository, studentSubjectCommandRepository);
    }

    @Test
    public void shouldThrowErrorWhenStudentNotExists() {
        // given
        School school = createSchool("Test", "123123", "12312313", address);
        school = schoolCommandRepository.save(school);

        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);

        SchoolClass schoolClass = createSchoolClass("Testowy", school, teacher);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        Subject subject = createSubject("Test", teacher, schoolClass);
        subject = subjectCommandRepository.save(subject);

        AssignSubjectToStudentCommand command = new AssignSubjectToStudentCommand(
                StudentId.create(),
                subject.getSubjectId()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertBusinessExceptionMessage(AssignSubjectToStudentCommand.STUDENT_ID));

    }

    @Test
    public void shouldThrowErrorWhenSubjectNotExists() {
        // given
        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudentWithSchoolAndClass(user, null);

        AssignSubjectToStudentCommand command = new AssignSubjectToStudentCommand(
                student.getStudentId(),
                SubjectId.create()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertBusinessExceptionMessage(AssignSubjectToStudentCommand.SUBJECT_ID));
    }

    @Test
    public void shouldAddErrorWhenStudentIsFromDifferentSchoolClassThanSubject() {
        // given
        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        user.activate();
        Student student = createStudentWithSchoolAndClass(user, null);

        School school = createSchool("Testowa1", "12344242", "24242323", address);
        school = schoolCommandRepository.save(school);

        SchoolClass schoolClass = createSchoolClass("Testowa4", school, null);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        Subject subject = createSubject("Testowy", null, schoolClass);
        subject = subjectCommandRepository.save(subject);

        AssignSubjectToStudentCommand command = new AssignSubjectToStudentCommand(
                student.getStudentId(),
                subject.getSubjectId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), AssignSubjectToStudentCommand.STUDENT_ID);
        assertEquals(errors.get(0).errorMessage(), AssignSubjectToStudentCommandValidator.MESSAGE_KEY_SUBJECT_IS_FROM_ANOTHER_SCHOOL_CLASS);
    }

    @Test
    public void shouldAddErrorWhenStudentIsAlreadyAssignedToSubject() {
        // given
        School school = createSchool("Testowa1", "12344242", "24242323", address);
        school = schoolCommandRepository.save(school);

        SchoolClass schoolClass = createSchoolClass("Testowa4", school, null);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        user.activate();
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        Subject subject = createSubject("Testowy", null, schoolClass);
        subject = subjectCommandRepository.save(subject);

        StudentSubject studentSubject = createStudentSubject(student, subject);
        studentSubject = studentSubjectCommandRepository.save(studentSubject);


        AssignSubjectToStudentCommand command = new AssignSubjectToStudentCommand(
                student.getStudentId(),
                subject.getSubjectId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), AssignSubjectToStudentCommand.SUBJECT_ID);
        assertEquals(errors.get(0).errorMessage(), AssignSubjectToStudentCommandValidator.MESSAGE_KEY_STUDENT_STUDENT_ALREADY_ASSIGNED_TO_SUBJECT);
    }

    @Test
    public void shouldAddErrorWhenStudentAccountIsInactive() {
// given
        School school = createSchool("Testowa1", "12344242", "24242323", address);
        school = schoolCommandRepository.save(school);

        SchoolClass schoolClass = createSchoolClass("Testowa4", school, null);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value());
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student = studentCommandRepository.save(student);

        Subject subject = createSubject("Testowy", null, schoolClass);
        subject = subjectCommandRepository.save(subject);

        AssignSubjectToStudentCommand command = new AssignSubjectToStudentCommand(
                student.getStudentId(),
                subject.getSubjectId()
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), AssignSubjectToStudentCommand.STUDENT_ID);
        assertEquals(errors.get(0).errorMessage(), AssignSubjectToStudentCommandValidator.MESSAGE_KEY_ACCOUNT_INACTIVE);
    }
}