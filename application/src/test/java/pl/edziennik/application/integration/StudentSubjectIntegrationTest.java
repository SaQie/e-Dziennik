package pl.edziennik.application.integration;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.student.create.CreateStudentCommand;
import pl.edziennik.application.command.subjectmanagment.assign.AssignSubjectToStudentCommand;
import pl.edziennik.application.command.subjectmanagment.unassign.UnassignStudentFromSubjectCommand;
import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.id.*;
import pl.edziennik.common.valueobject.vo.*;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.schoolclass.SchoolClassConfiguration;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

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
        dispatcher.run(command);

        // then
        StudentSubject studentSubject = studentSubjectCommandRepository.getByStudentStudentIdAndSubjectSubjectId(studentId, subjectId);
        assertNotNull(studentSubject);
        assertEquals(studentSubject.subject().subjectId(), subjectId);
        assertEquals(studentSubject.student().studentId(), studentId);
    }

    @Test
    public void shouldAutomaticallyAssignSubjectsFromSchoolClassToStudentWhenInsertingNewStudent() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "1231233");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123", schoolId);

        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");

        transactionTemplate.execute((x) -> createSubject("Test", schoolClassId, teacherId));

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test1"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("123123123"),
                Email.of("test1@example.com"),
                PhoneNumber.of("123123"),
                schoolClassId
        );

        // when
        dispatcher.run(command);

        // then
        assertOneRowExists("student_subject");
    }

    @Test
    @Transactional
    public void shouldNotAutomaticallyAssignSubjectFromSchoolClassToStudentIfSchoolClassConfigurationParameterIsFalse() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "1231233");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123", schoolId);

        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SchoolClass schoolClass = schoolClassCommandRepository.getBySchoolClassId(schoolClassId);

        SchoolClassConfiguration schoolClassConfiguration = schoolClassConfigurationCommandRepository.getSchoolClassConfigurationBySchoolClassConfigurationId(schoolClass.schoolClassConfiguration().schoolClassConfigurationId());
        schoolClassConfiguration.changeAutoAssignSubject(Boolean.FALSE);
        schoolClassConfigurationCommandRepository.save(schoolClassConfiguration);

        createSubject("Test", schoolClassId, teacherId);

        CreateStudentCommand command = new CreateStudentCommand(
                Password.of("Test"),
                Username.of("Test1"),
                FirstName.of("Kamil"),
                LastName.of("Nowak"),
                Address.of("Test"),
                PostalCode.of("123123"),
                City.of("Nowakowo"),
                Pesel.of("123123123"),
                Email.of("test1@example.com"),
                PhoneNumber.of("123123"),
                schoolClassId
        );

        // when
        dispatcher.run(command);

        // then
        assertNoOneRowExists("student_subject");
    }

    @Test
    public void shouldThrowExceptionWhenTryingToUnassignSubjectFromStudentWhenGradesExists() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "1231233");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123", schoolId);

        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SchoolClass schoolClass = schoolClassCommandRepository.getBySchoolClassId(schoolClassId);

        StudentId studentId = transactionTemplate.execute((x) -> createStudent("Test2", "test@examplee.com", "123123123", schoolId, schoolClassId));

        SubjectId subjectId = transactionTemplate.execute((x) -> createSubject("Przyroda", schoolClassId, teacherId));
        StudentSubjectId studentSubjectId = assignSubjectToStudent(subjectId, studentId);
        GradeId gradeId = assignGradeToStudentSubject(studentSubjectId, Grade.FIVE, teacherId);

        UnassignStudentFromSubjectCommand command = new UnassignStudentFromSubjectCommand(studentId, subjectId);


        try {
            // when
            dispatcher.run(command);
            Assertions.fail("Should throw exception if trying to unassign student from subject and grades exists");
        } catch (BusinessException e) {
            // then
            Assertions.assertEquals(e.getErrors().size(), 1);
            Assertions.assertEquals(e.getErrors().get(0).field(), UnassignStudentFromSubjectCommand.STUDENT_ID);
        }
    }

    @Test
    public void shouldThrowExceptionIfStudentIsNotAssignedToSubjectAndTryingToUnassign(){
        // given
        SchoolId schoolId = createSchool("Test", "123123", "1231233");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123", schoolId);

        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SchoolClass schoolClass = schoolClassCommandRepository.getBySchoolClassId(schoolClassId);

        StudentId studentId = transactionTemplate.execute((x) -> createStudent("Test2", "test@examplee.com", "123123123", schoolId, schoolClassId));

        SubjectId subjectId = transactionTemplate.execute((x) -> createSubject("Przyroda", schoolClassId, teacherId));
        UnassignStudentFromSubjectCommand command = new UnassignStudentFromSubjectCommand(studentId, subjectId);


        try {
            // when
            dispatcher.run(command);
            Assertions.fail("Should throw exception if trying to unassign student from subject and student is not assigned to this subject");
        } catch (BusinessException e) {
            // then
            Assertions.assertEquals(e.getErrors().size(), 1);
            Assertions.assertEquals(e.getErrors().get(0).field(), UnassignStudentFromSubjectCommand.STUDENT_ID);
        }
    }

    @Test
    public void shouldUnassignStudentFromSubjectIfGradesNotExists(){
        // given
        SchoolId schoolId = createSchool("Test", "123123", "1231233");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123", schoolId);

        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SchoolClass schoolClass = schoolClassCommandRepository.getBySchoolClassId(schoolClassId);

        StudentId studentId = transactionTemplate.execute((x) -> createStudent("Test2", "test@examplee.com", "123123123", schoolId, schoolClassId));

        SubjectId subjectId = transactionTemplate.execute((x) -> createSubject("Przyroda", schoolClassId, teacherId));
        StudentSubjectId studentSubjectId = assignSubjectToStudent(subjectId, studentId);

        UnassignStudentFromSubjectCommand command = new UnassignStudentFromSubjectCommand(studentId, subjectId);

        // when
        dispatcher.run(command);

        // then
        Assertions.assertFalse(studentSubjectCommandRepository.findById(studentSubjectId).isPresent());
    }

}
