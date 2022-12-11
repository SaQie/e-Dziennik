package pl.edziennik.eDziennik.studentsubject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.exceptions.BusinessException;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.grade.GradeIntegrationTestUtil;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.grade.domain.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.server.grade.services.managment.GradeManagmentService;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentGradesInSubjectDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.SubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.services.StudentSubjectService;
import pl.edziennik.eDziennik.server.studensubject.services.validator.StudentSubjectValidators;
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
import pl.edziennik.eDziennik.subject.SubjectIntegrationTestUtil;
import pl.edziennik.eDziennik.teacher.TeacherIntegrationTestUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class StudentSubjectIntegrationTest extends BaseTest {

    private final StudentSubjectIntegrationTestUtil util;
    private final TeacherIntegrationTestUtil teacherUtil;
    private final StudentIntegrationTestUtil studentUtil;
    private final GradeIntegrationTestUtil gradeUtil;
    private final SubjectIntegrationTestUtil subjectTestUtil;

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
        this.subjectTestUtil = new SubjectIntegrationTestUtil();
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

        StudentSubjectRequestDto requestDto = util.prepareStudentSubjectRequestDto(subjectId,studentId);

        // when
        service.assignStudentToSubject(requestDto);

        // then
        StudentSubjectsResponseDto studentSubject = service.getStudentSubjects(studentId);

        Subject expectedSubject = find(Subject.class, subjectId);
        Student expectedStudent = find(Student.class, studentId);

        assertEquals(1, studentSubject.getSubjects().size());
        assertEquals(expectedStudent.getPersonInformation().getFirstName(), studentSubject.getFirstName());
        assertEquals(expectedStudent.getPersonInformation().getLastName(), studentSubject.getLastName());
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

        StudentSubjectRequestDto requestDto = util.prepareStudentSubjectRequestDto(subjectId, studentId);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.assignStudentToSubject(requestDto));

        // then
        assertEquals(exception.getMessage(), BaseDao.BaseDaoExceptionMessage.createNotFoundExceptionMessage(Student.class.getSimpleName(), studentId));
    }

    @Test
    public void shouldThrowsExceptionWhenTryingAssignStudentToSubjectAndSubjectNotExist(){
        // given
        StudentRequestApiDto studentDto = studentUtil.prepareStudentRequestDto();
        Long studentId = studentService.register(studentDto).getId();

        StudentSubjectRequestDto requestDto = util.prepareStudentSubjectRequestDto(999L, studentId);
        Long subjectId = requestDto.getIdSubject();

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.assignStudentToSubject(requestDto));

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

        StudentSubjectRequestDto studentSubjectRequestDto = util.prepareStudentSubjectRequestDto(subjectId,studentId);
        service.assignStudentToSubject(studentSubjectRequestDto);

        // when
        gradeManagmentService.assignGradeToStudentSubject(studentId,subjectId,expectedGrade);

        // then
        StudentGradesInSubjectDto studentSubjectGrades = service.getStudentSubjectGrades(studentId, subjectId);

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

    @Test
    public void shouldThrowsExceptionWhenTryingAssignStudentToSubjectAndStudentIsAlreadyAssignedToSubject(){
        // given
        String expectedValidatorName = "StudentSubjectAlreadyExistValidator";
        StudentRequestApiDto studentDto = studentUtil.prepareStudentRequestDto();
        SubjectRequestApiDto subjectDto = subjectTestUtil.prepareSubjectRequestDto("Przyroda", null);
        Long studentId = studentService.register(studentDto).getId();
        Long subjectId = subjectService.createNewSubject(subjectDto).getId();

        StudentSubjectRequestDto requestDto = util.prepareStudentSubjectRequestDto(subjectId, studentId);

        service.assignStudentToSubject(requestDto);

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.assignStudentToSubject(requestDto));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(expectedValidatorName, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(List.of(StudentSubjectRequestDto.ID_SUBJECT ,StudentSubjectRequestDto.ID_STUDENT), exception.getErrors().get(0).getFields());
        String expectedExceptionMessage = resourceCreator.of(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST, find(Student.class, requestDto.getIdStudent()).getPersonInformation().getFirstName() + " " + find(Student.class, requestDto.getIdStudent()).getPersonInformation().getLastName() ,find(Subject.class, requestDto.getIdSubject()).getName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldThrowsExceptionWhenTryingAssignStudentToSubjectWhenSubjectComesFromDifferentSchoolClass(){
        // given
        String expectedValidatorName = "StudentCannotBeAssignedToSubjectFromDifferentClassValidator";
        StudentRequestApiDto studentRequestApiDto = studentUtil.prepareStudentRequestDto();
        // school class - 100L
        Long idStudent = studentService.register(studentRequestApiDto).getId();
        assertNotNull(idStudent);
        // school class - 101L
        SubjectRequestApiDto subjectRequestApiDto = subjectTestUtil.prepareSubjectRequestDto("Chemia", null, 101L);
        Long idSubject = subjectService.createNewSubject(subjectRequestApiDto).getId();

        StudentSubjectRequestDto studentSubjectRequestDto = util.prepareStudentSubjectRequestDto(idStudent, idSubject);

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.assignStudentToSubject(studentSubjectRequestDto));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(expectedValidatorName, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(List.of(StudentSubjectRequestDto.ID_SUBJECT), exception.getErrors().get(0).getFields());
        String expectedExceptionMessage = resourceCreator.of(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS, find(Student.class, studentSubjectRequestDto.getIdStudent()).getPersonInformation().getFirstName() + " " + find(Student.class, studentSubjectRequestDto.getIdStudent()).getPersonInformation().getLastName() ,find(Subject.class, studentSubjectRequestDto.getIdSubject()).getName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }


}
