package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.id.*;
import pl.edziennik.common.view.file.studentallsubjectsgrades.DetailedGradeForFileView;
import pl.edziennik.common.view.file.studentallsubjectsgrades.StudentAllSubjectsGradesHeaderForFileView;
import pl.edziennik.common.view.file.studentallsubjectsgrades.StudentAllSubjectsSummaryForFileView;
import pl.edziennik.common.view.grade.DetailedGradeView;
import pl.edziennik.common.view.grade.allsubjects.StudentAllSubjectsGradesHeaderView;
import pl.edziennik.common.view.grade.allsubjects.StudentAssignedSubjectsView;
import pl.edziennik.common.view.grade.bysubject.StudentGradesBySubjectView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DirtiesContext
public class GradeQueryProjectionTest extends BaseIntegrationTest {

    @Test
    @Transactional
    public void shouldReturnStudentGradesBySubjectViewForSpecificStudent() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("Kamil", "test@example.com", "123123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SubjectId subjectId = createSubject("Przyroda", schoolClassId, teacherId);
        StudentId studentId = createStudent("Kamilo", "test1@example.com", "123123", schoolId, schoolClassId);

        StudentSubjectId studentSubjectId = assignStudentToSubject(studentId, subjectId);

        assignGradeToStudentSubject(Grade.ONE, studentId, subjectId, teacherId);
        assignGradeToStudentSubject(Grade.FIVE, studentId, subjectId, teacherId);

        // when
        StudentGradesBySubjectView studentGradesBySubjectDto = gradeQueryRepository.getStudentGradesBySubjectView(subjectId, studentId);
        List<DetailedGradeView> detailedGradeViews = gradeQueryRepository.getDetailedGradeView(subjectId, studentId);

        // then
        assertNotNull(studentGradesBySubjectDto);
        assertEquals(2, detailedGradeViews.size());

        assertEquals(studentGradesBySubjectDto.studentSubjectId(), studentSubjectId);
        assertEquals(studentGradesBySubjectDto.studentId(), studentId);
        assertEquals(studentGradesBySubjectDto.fullName().value(), "Test Test");

        assertEquals(detailedGradeViews.get(0).grade(), Grade.ONE);
        assertEquals(detailedGradeViews.get(0).studentSubjectId(), studentSubjectId);
        assertEquals(detailedGradeViews.get(0).teacherId(), teacherId);
        assertEquals(detailedGradeViews.get(0).teacherFullName().value(), "Test Test");

        assertEquals(detailedGradeViews.get(1).grade(), Grade.FIVE);
        assertEquals(detailedGradeViews.get(1).studentSubjectId(), studentSubjectId);
        assertEquals(detailedGradeViews.get(1).teacherId(), teacherId);
        assertEquals(detailedGradeViews.get(1).teacherFullName().value(), "Test Test");
    }


    @Test
    @Transactional
    public void shouldReturnStudentGradesBySubjectViewForAllStudentsAssignedToSubject() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("Kamil", "test@example.com", "123123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SubjectId subjectId = createSubject("Przyroda", schoolClassId, teacherId);
        StudentId studentId = createStudent("Kamilo", "test1@example.com", "123123", schoolId, schoolClassId);
        StudentId studentIdSecond = createStudent("Kamilo2", "test2@example.com", "1231234", schoolId, schoolClassId);
        StudentId studentIdThird = createStudent("Kamilo3", "test3@example.com", "1231235", schoolId, schoolClassId);

        StudentSubjectId studentSubjectId = assignStudentToSubject(studentId, subjectId);
        StudentSubjectId studentSubjectIdSecond = assignStudentToSubject(studentIdSecond, subjectId);
        StudentSubjectId studentSubjectIdThird = assignStudentToSubject(studentIdThird, subjectId);

        assignGradeToStudentSubject(Grade.ONE, studentId, subjectId, teacherId);
        assignGradeToStudentSubject(Grade.TWO, studentIdSecond, subjectId, teacherId);
        assignGradeToStudentSubject(Grade.THREE, studentIdThird, subjectId, teacherId);

        // when
        List<StudentGradesBySubjectView> studentsGradesBySubjectDto = gradeQueryRepository.getStudentsGradesBySubjectView(subjectId);
        List<DetailedGradeView> detailedGradeViews = gradeQueryRepository.getDetailedGradeView(subjectId);

        // then
        assertEquals(studentsGradesBySubjectDto.size(), 3);
        assertEquals(detailedGradeViews.size(), 3);

        assertEquals(studentsGradesBySubjectDto.get(0).studentId(), studentId);
        assertEquals(studentsGradesBySubjectDto.get(0).studentSubjectId(), studentSubjectId);
        assertEquals(studentsGradesBySubjectDto.get(0).fullName().value(), "Test Test");

        assertEquals(studentsGradesBySubjectDto.get(1).studentId(), studentIdSecond);
        assertEquals(studentsGradesBySubjectDto.get(1).studentSubjectId(), studentSubjectIdSecond);
        assertEquals(studentsGradesBySubjectDto.get(1).fullName().value(), "Test Test");

        assertEquals(studentsGradesBySubjectDto.get(2).studentId(), studentIdThird);
        assertEquals(studentsGradesBySubjectDto.get(2).studentSubjectId(), studentSubjectIdThird);
        assertEquals(studentsGradesBySubjectDto.get(2).fullName().value(), "Test Test");

        assertEquals(detailedGradeViews.get(0).teacherId(), teacherId);
        assertEquals(detailedGradeViews.get(0).studentSubjectId(), studentSubjectId);
        assertEquals(detailedGradeViews.get(0).grade(), Grade.ONE);

        assertEquals(detailedGradeViews.get(1).teacherId(), teacherId);
        assertEquals(detailedGradeViews.get(1).studentSubjectId(), studentSubjectIdSecond);
        assertEquals(detailedGradeViews.get(1).grade(), Grade.TWO);

        assertEquals(detailedGradeViews.get(2).teacherId(), teacherId);
        assertEquals(detailedGradeViews.get(2).studentSubjectId(), studentSubjectIdThird);
        assertEquals(detailedGradeViews.get(2).grade(), Grade.THREE);
    }


    @Test
    @Transactional
    public void shouldReturnDetailedGradeView(){
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("Kamil", "test@example.com", "123123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SubjectId subjectId = createSubject("Przyroda", schoolClassId, teacherId);
        StudentId studentId = createStudent("Kamilo", "test1@example.com", "123123", schoolId, schoolClassId);

        StudentSubjectId studentSubjectId = assignStudentToSubject(studentId, subjectId);

        assignGradeToStudentSubject(Grade.ONE, studentId, subjectId, teacherId);
        assignGradeToStudentSubject(Grade.TWO, studentId, subjectId, teacherId);
        assignGradeToStudentSubject(Grade.THREE, studentId, subjectId, teacherId);

        // when
        List<DetailedGradeView> detailedGradeViews = gradeQueryRepository.getDetailedGradeView(studentId);

        // then
        assertEquals(detailedGradeViews.size(), 3);

        assertEquals(detailedGradeViews.get(0).teacherId(), teacherId);
        assertEquals(detailedGradeViews.get(0).studentSubjectId(), studentSubjectId);
        assertEquals(detailedGradeViews.get(0).grade(), Grade.ONE);

        assertEquals(detailedGradeViews.get(1).teacherId(), teacherId);
        assertEquals(detailedGradeViews.get(1).studentSubjectId(), studentSubjectId);
        assertEquals(detailedGradeViews.get(1).grade(), Grade.TWO);

        assertEquals(detailedGradeViews.get(2).teacherId(), teacherId);
        assertEquals(detailedGradeViews.get(2).studentSubjectId(), studentSubjectId);
        assertEquals(detailedGradeViews.get(2).grade(), Grade.THREE);
    }

    @Test
    @Transactional
    public void shouldReturnStudentAllSubjectsGradesHeaderView(){
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("Kamil", "test@example.com", "123123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SubjectId subjectId = createSubject("Przyroda", schoolClassId, teacherId);
        SubjectId subjectIdSecond = createSubject("Przyroda2", schoolClassId, teacherId);
        StudentId studentId = createStudent("Kamilo", "test1@example.com", "123123", schoolId, schoolClassId);

        StudentSubjectId studentSubjectId = assignStudentToSubject(studentId, subjectId);
        StudentSubjectId studentSubjectIdSecond = assignStudentToSubject(studentId, subjectIdSecond);

        assignGradeToStudentSubject(Grade.ONE, studentId, subjectId, teacherId);
        assignGradeToStudentSubject(Grade.TWO, studentId, subjectId, teacherId);
        assignGradeToStudentSubject(Grade.THREE, studentId, subjectIdSecond, teacherId);

        // when
        StudentAllSubjectsGradesHeaderView view = gradeQueryRepository.getStudentAllSubjectsGradesHeaderView(studentId);
        List<StudentAssignedSubjectsView> assignedSubjectsDto = gradeQueryRepository.getStudentAssignedSubjectsView(studentId);
        List<DetailedGradeView> gradeView = gradeQueryRepository.getDetailedGradeView(studentId);

        // then
        assertEquals(view.studentId(), studentId);
        assertEquals(assignedSubjectsDto.size(), 2);
        assertEquals(gradeView.size(), 3);

        assertEquals(view.fullName().value(), "Test Test");

        assertEquals(assignedSubjectsDto.get(0).studentSubjectId(), studentSubjectId);
        assertEquals(assignedSubjectsDto.get(0).teacherFullName().value(), "Test Test");
        assertEquals(assignedSubjectsDto.get(0).subjectName().value(), "Przyroda");

        assertEquals(assignedSubjectsDto.get(1).studentSubjectId(), studentSubjectIdSecond);
        assertEquals(assignedSubjectsDto.get(1).subjectName().value(), "Przyroda2");

        assertEquals(gradeView.get(0).studentSubjectId(), studentSubjectId);
        assertEquals(gradeView.get(0).grade(), Grade.ONE);
        assertEquals(gradeView.get(0).teacherId(), teacherId);


        assertEquals(gradeView.get(1).studentSubjectId(), studentSubjectId);
        assertEquals(gradeView.get(1).grade(), Grade.TWO);
        assertEquals(gradeView.get(1).teacherId(), teacherId);

        assertEquals(gradeView.get(2).studentSubjectId(), studentSubjectIdSecond);
        assertEquals(gradeView.get(2).grade(), Grade.THREE);
        assertEquals(gradeView.get(2).teacherId(), teacherId);


    }

    @Test
    @Transactional
    public void shouldReturnStudentAllSubjectGradesHeaderForFileView(){
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("Kamil", "test@example.com", "123123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SubjectId subjectId = createSubject("Przyroda", schoolClassId, teacherId);
        SubjectId subjectIdSecond = createSubject("Przyroda2", schoolClassId, teacherId);
        StudentId studentId = createStudent("Kamilo", "test1@example.com", "123123", schoolId, schoolClassId);

        StudentSubjectId studentSubjectId = assignStudentToSubject(studentId, subjectId);
        StudentSubjectId studentSubjectIdSecond = assignStudentToSubject(studentId, subjectIdSecond);

        assignGradeToStudentSubject(Grade.ONE, studentId, subjectId, teacherId);
        assignGradeToStudentSubject(Grade.TWO, studentId, subjectId, teacherId);
        assignGradeToStudentSubject(Grade.THREE, studentId, subjectIdSecond, teacherId);

        // when
        StudentAllSubjectsGradesHeaderForFileView header = gradeQueryRepository.getStudentAllSubjectGradesHeaderForFileView(studentId);
        List<StudentAllSubjectsSummaryForFileView> subjectsSummary = gradeQueryRepository.getStudentAllSubjectsSummaryForFileView(studentId);
        List<DetailedGradeForFileView> gradeDto = gradeQueryRepository.getDetailedGradeForFileView(studentId);

        // then
        assertEquals(header.schoolClassName().value(),"1A");
        assertEquals(header.studentFullName().value(), "Test Test");
        assertEquals(header.teacherFullName().value(), "Test Test");

        assertEquals(subjectsSummary.size(), 2);
        assertEquals(subjectsSummary.get(0).subjectName().value(), "Przyroda");
        assertEquals(subjectsSummary.get(1).subjectName().value(), "Przyroda2");

        assertEquals(gradeDto.size(), 3);

        assertEquals(gradeDto.get(0).studentSubjectId(), studentSubjectId);
        assertEquals(gradeDto.get(0).grade(), Grade.ONE);


        assertEquals(gradeDto.get(1).studentSubjectId(), studentSubjectId);
        assertEquals(gradeDto.get(1).grade(), Grade.TWO);

        assertEquals(gradeDto.get(2).studentSubjectId(), studentSubjectIdSecond);
        assertEquals(gradeDto.get(2).grade(), Grade.THREE);
    }

}
