package pl.edziennik.eDziennik.studentsubject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.grade.GradeIntegrationTestUtil;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.grade.domain.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.server.grade.services.GradeService;
import pl.edziennik.eDziennik.server.grade.services.managment.GradeManagmentService;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.SubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.services.StudentSubjectService;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.student.services.StudentService;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.server.subject.services.SubjectService;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.services.TeacherService;
import pl.edziennik.eDziennik.student.StudentIntegrationTestUtil;
import pl.edziennik.eDziennik.teacher.TeacherIntegrationTestUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class StudentSubjectIntegrationTest extends BaseTest {

    private final StudentSubjectIntegrationTestUtil util;
    private final TeacherIntegrationTestUtil teacherUtil;
    private final StudentIntegrationTestUtil studentUtil;
    private final GradeIntegrationTestUtil gradeUtil;

    @Autowired
    private StudentSubjectService service;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GradeManagmentService gradeManagmentService;

    public StudentSubjectIntegrationTest() {
        this.util = new StudentSubjectIntegrationTestUtil();
        this.teacherUtil = new TeacherIntegrationTestUtil();
        this.studentUtil = new StudentIntegrationTestUtil();
        this.gradeUtil = new GradeIntegrationTestUtil();
    }

    @BeforeEach
    public void prepareDb(){
        clearDb();
        fillDbWithData();
    }


    @Test
    public void shouldAssignStudentToSubject(){
        // given
        TeacherRequestApiDto teacherDto = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(teacherDto).getId();

        StudentRequestApiDto studentDto = studentUtil.prepareStudentRequestDto();
        Long studentId = studentService.register(studentDto).getId();

        SubjectRequestApiDto subjectDto = util.prepareSubject(teacherId);
        Long subjectId = subjectService.createNewSubject(subjectDto).getId();

        StudentSubjectRequestDto requestDto = util.prepareStudentSubjectRequestDto(subjectId);

        // when
        service.assignStudentToSubject(requestDto, studentId);

        // then
        StudentSubjectsResponseDto studentSubject = service.getStudentSubjects(studentId);

        Subject expectedSubject = find(Subject.class, subjectId);
        Student expectedStudent = find(Student.class, studentId);

        assertEquals(1, studentSubject.getSubjects().size());
        assertEquals(expectedStudent.getFirstName(), studentSubject.getFirstName());
        assertEquals(expectedStudent.getLastName(), studentSubject.getLastName());
        assertEquals(expectedStudent.getId(), studentSubject.getId());

        SubjectResponseApiDto actualSubject = studentSubject.getSubjects().get(0);
        assertEquals(expectedSubject.getName(), actualSubject.getName());
        assertEquals(expectedSubject.getDescription(), actualSubject.getDescription());
        assertEquals(expectedSubject.getTeacher().getId(), actualSubject.getIdTeacher());
    }

    @Test
    public void shouldThrowsExceptionWhenTryingAssignStudentToSubjectAndStudentNotExist(){
        // given
        Long studentId = 999L;

        TeacherRequestApiDto teacherDto = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(teacherDto).getId();

        SubjectRequestApiDto subjectDto = util.prepareSubject(teacherId);
        Long subjectId = subjectService.createNewSubject(subjectDto).getId();

        StudentSubjectRequestDto requestDto = util.prepareStudentSubjectRequestDto(subjectId);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.assignStudentToSubject(requestDto, studentId));

        // then
        assertEquals(exception.getMessage(), BaseDao.BaseDaoExceptionMessage.createNotFoundExceptionMessage(Student.class.getSimpleName(), studentId));
    }

    @Test
    public void shouldThrowsExceptionWhenTryingAssignStudentToSubjectAndSubjectNotExist(){
        // given
        StudentRequestApiDto studentDto = studentUtil.prepareStudentRequestDto();
        Long studentId = studentService.register(studentDto).getId();

        StudentSubjectRequestDto requestDto = util.prepareStudentSubjectRequestDto(999L);
        Long subjectId = requestDto.getSubject();

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.assignStudentToSubject(requestDto, studentId));

        // then
        assertEquals(exception.getMessage(), BaseDao.BaseDaoExceptionMessage.createNotFoundExceptionMessage(Subject.class.getSimpleName(), subjectId));
    }

    @Test
    public void shouldAssignGradeToStudentSubject(){
        // given
        TeacherRequestApiDto teacherDto = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(teacherDto).getId();

        StudentRequestApiDto studentDto = studentUtil.prepareStudentRequestDto();
        Long studentId = studentService.register(studentDto).getId();

        SubjectRequestApiDto expectedSubject = util.prepareSubject(teacherId);
        Long subjectId = subjectService.createNewSubject(expectedSubject).getId();

        GradeRequestApiDto expectedGrade = gradeUtil.prepareRequestApi(5, 5);
        expectedGrade.setTeacherName(find(Teacher.class, teacherId).getUsername());

        StudentSubjectRequestDto studentSubjectRequestDto = util.prepareStudentSubjectRequestDto(subjectId);
        service.assignStudentToSubject(studentSubjectRequestDto, studentId);

        // when
        gradeManagmentService.assignGradeToStudentSubject(studentId,subjectId,expectedGrade);

        // then
        StudentSubjectGradesResponseDto studentSubjectGrades = service.getStudentSubjectGrades(studentId, subjectId);

        SubjectGradesResponseDto actualSubject = studentSubjectGrades.getSubject();
        assertEquals(expectedSubject.getName(), actualSubject.getName());
        assertEquals(subjectId, actualSubject.getId());

        List<SubjectGradesResponseDto.GradesDto> actualGrades = studentSubjectGrades.getSubject().getGrades();
        assertEquals(1, actualGrades.size());

        SubjectGradesResponseDto.GradesDto actualGrade = actualGrades.get(0);
        assertEquals(expectedGrade.getGrade(), actualGrade.getGrade());
        assertEquals(expectedGrade.getDescription(), actualGrade.getDescription());
        assertEquals(expectedGrade.getWeight(), actualGrade.getWeight());
        assertEquals(teacherId, actualGrade.getTeacher());


    }


}
