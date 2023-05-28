package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.dto.file.studentallsubjectsgrades.DetailedGradeForFileDto;
import pl.edziennik.common.dto.file.studentallsubjectsgrades.StudentAllSubjectsGradesHeaderForFileDto;
import pl.edziennik.common.dto.file.studentallsubjectsgrades.StudentAllSubjectsSummaryForFileDto;
import pl.edziennik.common.dto.grade.DetailedGradeDto;
import pl.edziennik.common.dto.grade.allsubjects.StudentAllSubjectsGradesHeaderDto;
import pl.edziennik.common.dto.grade.allsubjects.StudentAssignedSubjectsDto;
import pl.edziennik.common.dto.grade.bysubject.StudentGradesBySubjectDto;
import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.id.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DirtiesContext
public class GradeQueryProjectionTest extends BaseIntegrationTest {

    @Test
    @Transactional
    public void shouldReturnStudentGradesBySubjectDtoForSpecificStudent() {
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
        StudentGradesBySubjectDto studentGradesBySubjectDto = gradeQueryRepository.getStudentGradesBySubjectDto(subjectId, studentId);
        List<DetailedGradeDto> detailedGradeDtos = gradeQueryRepository.getDetailedGradeDto(subjectId, studentId);

        // then
        assertNotNull(studentGradesBySubjectDto);
        assertEquals(2, detailedGradeDtos.size());

        assertEquals(studentGradesBySubjectDto.studentSubjectId(), studentSubjectId);
        assertEquals(studentGradesBySubjectDto.studentId(), studentId);
        assertEquals(studentGradesBySubjectDto.fullName().value(), "Test Test");

        assertEquals(detailedGradeDtos.get(0).grade(), Grade.ONE);
        assertEquals(detailedGradeDtos.get(0).studentSubjectId(), studentSubjectId);
        assertEquals(detailedGradeDtos.get(0).teacherId(), teacherId);
        assertEquals(detailedGradeDtos.get(0).teacherFullName().value(), "Test Test");

        assertEquals(detailedGradeDtos.get(1).grade(), Grade.FIVE);
        assertEquals(detailedGradeDtos.get(1).studentSubjectId(), studentSubjectId);
        assertEquals(detailedGradeDtos.get(1).teacherId(), teacherId);
        assertEquals(detailedGradeDtos.get(1).teacherFullName().value(), "Test Test");
    }


    @Test
    @Transactional
    public void shouldReturnStudentGradesBySubjectDtoForAllStudentsAssignedToSubject() {
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
        List<StudentGradesBySubjectDto> studentsGradesBySubjectDto = gradeQueryRepository.getStudentsGradesBySubjectDto(subjectId);
        List<DetailedGradeDto> detailedGradeDtos = gradeQueryRepository.getDetailedGradeDto(subjectId);

        // then
        assertEquals(studentsGradesBySubjectDto.size(), 3);
        assertEquals(detailedGradeDtos.size(), 3);

        assertEquals(studentsGradesBySubjectDto.get(0).studentId(), studentId);
        assertEquals(studentsGradesBySubjectDto.get(0).studentSubjectId(), studentSubjectId);
        assertEquals(studentsGradesBySubjectDto.get(0).fullName().value(), "Test Test");

        assertEquals(studentsGradesBySubjectDto.get(1).studentId(), studentIdSecond);
        assertEquals(studentsGradesBySubjectDto.get(1).studentSubjectId(), studentSubjectIdSecond);
        assertEquals(studentsGradesBySubjectDto.get(1).fullName().value(), "Test Test");

        assertEquals(studentsGradesBySubjectDto.get(2).studentId(), studentIdThird);
        assertEquals(studentsGradesBySubjectDto.get(2).studentSubjectId(), studentSubjectIdThird);
        assertEquals(studentsGradesBySubjectDto.get(2).fullName().value(), "Test Test");

        assertEquals(detailedGradeDtos.get(0).teacherId(), teacherId);
        assertEquals(detailedGradeDtos.get(0).studentSubjectId(), studentSubjectId);
        assertEquals(detailedGradeDtos.get(0).grade(), Grade.ONE);

        assertEquals(detailedGradeDtos.get(1).teacherId(), teacherId);
        assertEquals(detailedGradeDtos.get(1).studentSubjectId(), studentSubjectIdSecond);
        assertEquals(detailedGradeDtos.get(1).grade(), Grade.TWO);

        assertEquals(detailedGradeDtos.get(2).teacherId(), teacherId);
        assertEquals(detailedGradeDtos.get(2).studentSubjectId(), studentSubjectIdThird);
        assertEquals(detailedGradeDtos.get(2).grade(), Grade.THREE);
    }


    @Test
    @Transactional
    public void shouldReturnDetailedGradeDto(){
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
        List<DetailedGradeDto> detailedGradeDtos = gradeQueryRepository.getDetailedGradeDto(studentId);

        // then
        assertEquals(detailedGradeDtos.size(), 3);

        assertEquals(detailedGradeDtos.get(0).teacherId(), teacherId);
        assertEquals(detailedGradeDtos.get(0).studentSubjectId(), studentSubjectId);
        assertEquals(detailedGradeDtos.get(0).grade(), Grade.ONE);

        assertEquals(detailedGradeDtos.get(1).teacherId(), teacherId);
        assertEquals(detailedGradeDtos.get(1).studentSubjectId(), studentSubjectId);
        assertEquals(detailedGradeDtos.get(1).grade(), Grade.TWO);

        assertEquals(detailedGradeDtos.get(2).teacherId(), teacherId);
        assertEquals(detailedGradeDtos.get(2).studentSubjectId(), studentSubjectId);
        assertEquals(detailedGradeDtos.get(2).grade(), Grade.THREE);
    }

    @Test
    @Transactional
    public void shouldReturnStudentAllSubjectsGradesHeaderDto(){
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
        StudentAllSubjectsGradesHeaderDto dto = gradeQueryRepository.getStudentAllSubjectsGradesHeaderDto(studentId);
        List<StudentAssignedSubjectsDto> assignedSubjectsDto = gradeQueryRepository.getStudentAssignedSubjectsDto(studentId);
        List<DetailedGradeDto> gradeDto = gradeQueryRepository.getDetailedGradeDto(studentId);

        // then
        assertEquals(dto.studentId(), studentId);
        assertEquals(assignedSubjectsDto.size(), 2);
        assertEquals(gradeDto.size(), 3);

        assertEquals(dto.fullName().value(), "Test Test");

        assertEquals(assignedSubjectsDto.get(0).studentSubjectId(), studentSubjectId);
        assertEquals(assignedSubjectsDto.get(0).teacherFullName().value(), "Test Test");
        assertEquals(assignedSubjectsDto.get(0).subjectName().value(), "Przyroda");

        assertEquals(assignedSubjectsDto.get(1).studentSubjectId(), studentSubjectIdSecond);
        assertEquals(assignedSubjectsDto.get(1).subjectName().value(), "Przyroda2");

        assertEquals(gradeDto.get(0).studentSubjectId(), studentSubjectId);
        assertEquals(gradeDto.get(0).grade(), Grade.ONE);
        assertEquals(gradeDto.get(0).teacherId(), teacherId);


        assertEquals(gradeDto.get(1).studentSubjectId(), studentSubjectId);
        assertEquals(gradeDto.get(1).grade(), Grade.TWO);
        assertEquals(gradeDto.get(1).teacherId(), teacherId);

        assertEquals(gradeDto.get(2).studentSubjectId(), studentSubjectIdSecond);
        assertEquals(gradeDto.get(2).grade(), Grade.THREE);
        assertEquals(gradeDto.get(2).teacherId(), teacherId);


    }

    @Test
    @Transactional
    public void shouldReturnStudentAllSubjectGradesHeaderForFileDto(){
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
        StudentAllSubjectsGradesHeaderForFileDto header = gradeQueryRepository.getStudentAllSubjectGradesHeaderForFileDto(studentId);
        List<StudentAllSubjectsSummaryForFileDto> subjectsSummary = gradeQueryRepository.getStudentAllSubjectsSummaryForFileDto(studentId);
        List<DetailedGradeForFileDto> gradeDto = gradeQueryRepository.getDetailedGradeForFileDto(studentId);

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
