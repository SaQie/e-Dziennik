package pl.edziennik.eDziennik.domain.studentsubject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTesting;
import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.studentsubject.domain.wrapper.StudentSubjectSeparateId;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentGradesInSubjectDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.SubjectGradesResponseDto;
import pl.edziennik.eDziennik.domain.studentsubject.services.validator.StudentSubjectValidators;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class StudentSubjectIntegrationTest extends BaseTesting {
    @Test
    public void shouldAssignStudentToSubject() {
        // given
        TeacherRequestApiDto teacherDto = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(teacherDto).id();

        StudentRequestApiDto studentDto = studentUtil.prepareStudentRequestDto("Test333", "test5@example.com");
        Long studentId = studentService.register(studentDto).id();

        SubjectRequestApiDto subjectDto = studentSubjectUtil.prepareSubject(teacherId);
        Long subjectId = subjectService.createNewSubject(subjectDto).id();

        StudentSubjectRequestDto requestDto = studentSubjectUtil.prepareStudentSubjectRequestDto(subjectId, studentId);

        // when
        studentSubjectService.assignStudentToSubject(requestDto);

        // then
        StudentSubjectsResponseDto studentSubject = studentSubjectService.getStudentSubjects(StudentId.wrap(studentId));

        Subject expectedSubject = find(Subject.class, subjectId);
        Student expectedStudent = find(Student.class, studentId);

        assertEquals(1, studentSubject.getSubjects().size());
        assertEquals(expectedStudent.getPersonInformation().firstName(), studentSubject.getFirstName());
        assertEquals(expectedStudent.getPersonInformation().lastName(), studentSubject.getLastName());
        assertEquals(expectedStudent.getStudentId().value(), studentSubject.getId());

        SubjectResponseApiDto actualSubject = studentSubject.getSubjects().get(0);
        assertEquals(expectedSubject.getName(), actualSubject.name());
        assertEquals(expectedSubject.getDescription(), actualSubject.description());
        assertEquals(expectedSubject.getTeacher().getTeacherId().value(), actualSubject.teacher().id());
    }

    @Test
    public void shouldThrowsExceptionWhenTryingAssignStudentToSubjectAndStudentNotExist() {
        // given
        Long studentId = 999L;

        TeacherRequestApiDto teacherDto = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(teacherDto).id();

        SubjectRequestApiDto subjectDto = studentSubjectUtil.prepareSubject(teacherId);
        Long subjectId = subjectService.createNewSubject(subjectDto).id();

        StudentSubjectRequestDto requestDto = studentSubjectUtil.prepareStudentSubjectRequestDto(subjectId, studentId);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentSubjectService.assignStudentToSubject(requestDto));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", studentId, Student.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsExceptionWhenTryingAssignStudentToSubjectAndSubjectNotExist() {
        // given
        StudentRequestApiDto studentDto = studentUtil.prepareStudentRequestDto();
        Long studentId = studentService.register(studentDto).id();

        StudentSubjectRequestDto requestDto = studentSubjectUtil.prepareStudentSubjectRequestDto(999L, studentId);
        Long subjectId = requestDto.idSubject();

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentSubjectService.assignStudentToSubject(requestDto));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", subjectId, Subject.class.getSimpleName()));
    }

    @Test
    public void shouldAssignGradeToStudentSubject() {
        // given
        TeacherRequestApiDto teacherDto = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(teacherDto).id();

        StudentRequestApiDto studentDto = studentUtil.prepareStudentRequestDto("Test22221", "Test5@example.com");
        Long studentId = studentService.register(studentDto).id();

        SubjectRequestApiDto expectedSubject = studentSubjectUtil.prepareSubject(teacherId);
        Long subjectId = subjectService.createNewSubject(expectedSubject).id();

        GradeRequestApiDto expectedGrade = gradeUtil.prepareRequestApi(5, 5);
        expectedGrade.setTeacherName(find(Teacher.class, teacherId).getUser().getUsername());

        StudentSubjectRequestDto studentSubjectRequestDto = studentSubjectUtil.prepareStudentSubjectRequestDto(subjectId, studentId);
        studentSubjectService.assignStudentToSubject(studentSubjectRequestDto);
        StudentSubjectSeparateId studentSubjectSeparateId = StudentSubjectSeparateId.wrap(StudentId.wrap(studentId), SubjectId.wrap(subjectId));

        // when
        gradeManagmentService.assignGradeToStudentSubject(studentSubjectSeparateId, expectedGrade);

        // then
        StudentGradesInSubjectDto studentSubjectGrades = studentSubjectService.getStudentSubjectGrades(StudentId.wrap(studentId), SubjectId.wrap(subjectId));

        SubjectGradesResponseDto actualSubject = studentSubjectGrades.getSubject();
        assertEquals(expectedSubject.name(), actualSubject.getName());
        assertEquals(subjectId, actualSubject.getId());

        List<SubjectGradesResponseDto.GradesDto> actualGrades = studentSubjectGrades.getSubject().getGrades();
        assertEquals(1, actualGrades.size());

        SubjectGradesResponseDto.GradesDto actualGrade = actualGrades.get(0);
        assertEquals(expectedGrade.getGrade(), actualGrade.getGrade());
        assertEquals(expectedGrade.getDescription(), actualGrade.getDescription());
        assertEquals(expectedGrade.getWeight(), actualGrade.getWeight());
        assertEquals(teacherId, actualGrade.getTeacher());


    }

    @Test
    public void shouldThrowsExceptionWhenTryingAssignStudentToSubjectAndStudentIsAlreadyAssignedToSubject() {
        // given
        StudentRequestApiDto studentDto = studentUtil.prepareStudentRequestDto();
        SubjectRequestApiDto subjectDto = subjectUtil.prepareSubjectRequestDto("Przyroda", null);
        Long studentId = studentService.register(studentDto).id();
        Long subjectId = subjectService.createNewSubject(subjectDto).id();

        StudentSubjectRequestDto requestDto = studentSubjectUtil.prepareStudentSubjectRequestDto(subjectId, studentId);

        studentSubjectService.assignStudentToSubject(requestDto);

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> studentSubjectService.assignStudentToSubject(requestDto));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(StudentSubjectValidators.STUDENT_SUBJECT_ALREADY_EXIST_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(StudentSubjectRequestDto.ID_SUBJECT, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST, find(Student.class,
                requestDto.idStudent()).getPersonInformation().firstName() + " " + find(Student.class,
                requestDto.idStudent()).getPersonInformation().lastName(), find(Subject.class, requestDto.idSubject()).getName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldThrowsExceptionWhenTryingAssignStudentToSubjectWhenSubjectComesFromDifferentSchoolClass() {
        // given
        StudentRequestApiDto studentRequestApiDto = studentUtil.prepareStudentRequestDto();
        // school class - 100L
        Long idStudent = studentService.register(studentRequestApiDto).id();
        assertNotNull(idStudent);
        // school class - 101L
        SubjectRequestApiDto subjectRequestApiDto = subjectUtil.prepareSubjectRequestDto("Chemia", null, 101L);
        Long idSubject = subjectService.createNewSubject(subjectRequestApiDto).id();

        StudentSubjectRequestDto studentSubjectRequestDto = studentSubjectUtil.prepareStudentSubjectRequestDto(idStudent, idSubject);

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> studentSubjectService.assignStudentToSubject(studentSubjectRequestDto));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(StudentSubjectValidators.STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FRON_DIFFERENT_CLASS_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(StudentSubjectRequestDto.ID_SUBJECT, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS, find(Student.class,
                studentSubjectRequestDto.idStudent()).getPersonInformation().firstName() + " " + find(Student.class,
                studentSubjectRequestDto.idStudent()).getPersonInformation().lastName(), find(Subject.class, studentSubjectRequestDto.idSubject()).getName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }


}
