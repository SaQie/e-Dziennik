package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.dto.grade.DetailedGradeDto;
import pl.edziennik.common.dto.grade.StudentGradesBySubjectDto;
import pl.edziennik.common.enums.Grade;
import pl.edziennik.common.valueobject.id.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        List<DetailedGradeDto> detailedGradeDtos = gradeQueryRepository.getDetailedStudentSubjectDto(subjectId, studentId);

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
        List<DetailedGradeDto> detailedGradeDtos = gradeQueryRepository.getDetailedStudentSubjectDto(subjectId);

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


}
