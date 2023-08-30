package pl.edziennik.application.integration.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.common.valueobject.id.*;
import pl.edziennik.common.view.grade.allsubjects.StudentAllSubjectsGradesHeaderView;
import pl.edziennik.common.view.grade.bysubject.StudentGradesBySubjectView;
import pl.edziennik.common.view.subject.DetailedSubjectView;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class SubjectQueryIntegrationTest extends BaseIntegrationTest {


    @Test
    @Transactional
    public void shouldReturnDetailedSubjectUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("test", "1321@o2.pl", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SubjectId subjectId = createSubject("Przyroda", schoolClassId, teacherId);

        // when
        DetailedSubjectView view = subjectQueryDao.getDetailedSubjectView(subjectId);

        // then
        assertNotNull(view);
    }

    @Test
    public void shouldThrowExceptionWhileGettingDetailedSubjectIfSubjectNotExists() {
        try {
            // given
            // when
            subjectQueryDao.getDetailedSubjectView(SubjectId.create());
            Assertions.fail("Should throw exception when getting detailed subject data and subject not exists");
            // then
        } catch (BusinessException e) {
            List<ValidationError> errors = e.getErrors();
            assertEquals(errors.size(), 1);
            assertEquals(errors.get(0).errorCode(), ErrorCode.OBJECT_NOT_EXISTS.errorCode());
        }
    }

    @Test
    @Transactional
    public void shouldReturnSpecificStudentGradesUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("test", "1321@o2.pl", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SubjectId subjectId = createSubject("Przyroda", schoolClassId, teacherId);
        StudentId studentId = createStudent("Testowy", "111@o2.pl", "123123", schoolId, schoolClassId);
        assignStudentToSubject(studentId, subjectId);
        assignGradeToStudentSubject(studentId, subjectId, teacherId);

        // when
        StudentAllSubjectsGradesHeaderView view = subjectQueryDao.getAllSubjectsGradesOfSpecificStudentView(studentId);

        // then
        assertNotNull(view);
        assertEquals(view.studentId(), studentId);
    }

    @Test
    @Transactional
    public void shouldReturnAllSubjectsGradesOfSpecificStudentUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("test", "1321@o2.pl", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SubjectId subjectId = createSubject("Przyroda", schoolClassId, teacherId);
        StudentId studentId = createStudent("Testowy", "111@o2.pl", "123123", schoolId, schoolClassId);
        StudentSubjectId studentSubjectId = assignStudentToSubject(studentId, subjectId);
        assignGradeToStudentSubject(studentId, subjectId, teacherId);

        // when
        StudentGradesBySubjectView view = subjectQueryDao.getStudentGradesBySubjectView(subjectId, studentId);

        // then
        assertNotNull(view);
        assertEquals(studentId, view.studentId());
        assertEquals(studentSubjectId, view.studentSubjectId());
    }

    @Test
    @Transactional
    public void shouldReturnStudentsGradesBySubjectUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("test", "1321@o2.pl", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SubjectId subjectId = createSubject("Przyroda", schoolClassId, teacherId);
        StudentId studentId = createStudent("Testowy", "111@o2.pl", "123123", schoolId, schoolClassId);
        assignStudentToSubject(studentId, subjectId);
        assignGradeToStudentSubject(studentId, subjectId, teacherId);

        // when
        List<StudentGradesBySubjectView> view = subjectQueryDao.getAllStudentGradesBySubjectView(subjectId);

        // then
        assertNotNull(view);
        assertEquals(1, view.size());
        assertEquals(view.get(0).studentId(), studentId);
    }


}
