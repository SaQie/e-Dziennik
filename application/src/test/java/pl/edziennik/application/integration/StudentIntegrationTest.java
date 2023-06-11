package pl.edziennik.application.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.student.create.CreateStudentCommand;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class StudentIntegrationTest extends BaseIntegrationTest {


    @Test
    public void shouldCreateStudent() {
        // given
        SchoolId schoolId = createSchool("Testowa", "123123", "123123");
        TeacherId teacherId = createTeacher("Teacher", "test@example.com", "124123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("123123123"),
                Email.of("test1@example.com"),
                PhoneNumber.of("123123"),
                schoolId,
                schoolClassId
        );

        // when
        OperationResult operationResult = dispatcher.dispatch(command);

        // then
        Student student = studentCommandRepository.getByStudentId(StudentId.of(operationResult.identifier().id()));
        assertNotNull(student);
        assertNotNull(student.getJournalNumber());
    }

    @Test
    @Transactional
    public void shouldThrowExceptionIfStudentWithUsernameOrEmailOrPeselAlreadyExists() {
        // given
        SchoolId schoolId = createSchool("Test", "123123123", "123123123");
        TeacherId teacherId = createTeacher("Teacher", "test1@example.com", "129292", schoolId);

        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        createStudent("Test", "test@example.com", "123123123", schoolId, schoolClassId);

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("123123123"),
                Email.of("test@example.com"),
                PhoneNumber.of("123123"),
                schoolId,
                schoolClassId
        );

        try {
            // when
            dispatcher.dispatch(command);
            Assertions.fail("Should throw exception when student with given name or email or pesel already exists");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(3, errors.size());
            assertThat(errors)
                    .extracting(ValidationError::field)
                    .containsExactlyInAnyOrder(CreateStudentCommand.USERNAME, CreateStudentCommand.PESEL, CreateStudentCommand.EMAIL);

        }

    }


    @Test
    @Transactional
    public void shouldThrowExceptionIfSchoolClassStudentLimitReached() {
        // given
        SchoolId schoolId = createSchool("Test", "123123123", "123123123");
        TeacherId teacherId = createTeacher("Teacher", "test1@example.com", "129292", schoolId);

        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SchoolClass schoolClass = schoolClassCommandRepository.getBySchoolClassId(schoolClassId);
        schoolClass.getSchoolClassConfiguration().changeMaxStudentsSize(0);

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("123123123"),
                Email.of("test@example.com"),
                PhoneNumber.of("123123"),
                schoolId,
                schoolClassId
        );

        try {
            // when
            dispatcher.dispatch(command);
            Assertions.fail("Should throw exception if school class student limit reached");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertThat(errors)
                    .extracting(ValidationError::field)
                    .containsExactlyInAnyOrder(CreateStudentCommand.SCHOOL_CLASS_ID);
        }
    }


    @Test
    public void shouldAutomaticallyAssignJournalNumberAfterSave() {
        // given
        SchoolId schoolId = createSchool("Test", "123123123", "123123123");
        TeacherId teacherId = createTeacher("Teacher", "test1@example.com", "129292", schoolId);
        TeacherId teacherIdSecond = createTeacher("Teacher2", "test2@example.com", "123123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SchoolClassId schoolClassIdSecond = createSchoolClass(schoolId, teacherIdSecond, "2A");

        // when

        // create students for first school class
        StudentId studentIdFirst = transactionTemplate.execute(result -> createStudent("Test", "test@o2.pl", "123123122", schoolId, schoolClassId));
        StudentId studentIdSecond = transactionTemplate.execute(result -> createStudent("Test2", "test2@o2.pl", "123123123", schoolId, schoolClassId));

        // create students for second school class
        StudentId studentIdThird = transactionTemplate.execute(result -> createStudent("Test3", "test3@o2.pl", "123123124", schoolId, schoolClassIdSecond));
        StudentId studentIdFourth = transactionTemplate.execute(result -> createStudent("Test4", "test4@o2.pl", "123123125", schoolId, schoolClassIdSecond));


        // then
        Student student = studentCommandRepository.getByStudentId(studentIdFirst);
        assertNotNull(student);
        assertEquals(student.getJournalNumber().value(), 1);

        student = studentCommandRepository.getByStudentId(studentIdSecond);
        assertNotNull(student);
        assertEquals(student.getJournalNumber().value(), 2);


        // Again journal number is one because every school class has their own sequence
        student = studentCommandRepository.getByStudentId(studentIdThird);
        assertNotNull(student);
        assertEquals(student.getJournalNumber().value(), 1);


        student = studentCommandRepository.getByStudentId(studentIdFourth);
        assertNotNull(student);
        assertEquals(student.getJournalNumber().value(), 2);
    }

}
