package pl.edziennik.application.integration;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.student.create.CreateStudentCommand;
import pl.edziennik.application.command.subjectmanagment.assigntostudent.AssignSubjectToStudentCommand;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.*;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.schoolclass.SchoolClassConfiguration;
import pl.edziennik.domain.studentsubject.StudentSubject;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
public class StudentSubjectIntegrationTest extends BaseIntegrationTest {

    @Test
    @Transactional
    public void shouldAssignSubjectToStudent() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "1231233");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");

        SubjectId subjectId = createSubject("Test", schoolClassId, teacherId);
        StudentId studentId = createStudent("Test1", "Test1@example.com", "123123", schoolId, schoolClassId);

        AssignSubjectToStudentCommand command = new AssignSubjectToStudentCommand(
                studentId,
                subjectId
        );

        // when
        dispatcher.dispatch(command);

        // then
        StudentSubject studentSubject = studentSubjectCommandRepository.getByStudentStudentIdAndSubjectSubjectId(studentId, subjectId);
        assertNotNull(studentSubject);
        assertEquals(studentSubject.getSubject().getSubjectId(), subjectId);
        assertEquals(studentSubject.getStudent().getStudentId(), studentId);
    }

    @Test
    public void shouldAutomaticallyAssignSubjectsFromSchoolClassToStudentWhenInsertingNewStudent() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "1231233");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123", schoolId);

        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");

        createSubject("Test", schoolClassId, teacherId);

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

        // when
        OperationResult operationResult = dispatcher.dispatch(command);

        // then
        assertOneRowExists("student_subject");
    }

    @Test
    public void shouldNotAutomaticallyAssignSubjectFromSchoolClassToStudentIfSchoolClassConfigurationParameterIsFalse() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "1231233");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123", schoolId);

        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SchoolClass schoolClass = schoolClassCommandRepository.getBySchoolClassId(schoolClassId);

        SchoolClassConfiguration schoolClassConfiguration = schoolClassConfigurationCommandRepository.getSchoolClassConfigurationBySchoolClassConfigurationId(schoolClass.getSchoolClassConfiguration().getSchoolClassConfigurationId());
        schoolClassConfiguration.changeAutoAssignSubject(Boolean.FALSE);
        schoolClassConfigurationCommandRepository.save(schoolClassConfiguration);

        createSubject("Test", schoolClassId, teacherId);

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

        // when
        dispatcher.dispatch(command);

        // then
        assertNoOneRowExists("student_subject");
    }

}
