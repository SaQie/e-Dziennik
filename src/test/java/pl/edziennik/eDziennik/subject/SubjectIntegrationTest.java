package pl.edziennik.eDziennik.subject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.server.subject.services.SubjectService;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.services.TeacherService;
import pl.edziennik.eDziennik.teacher.TeacherIntegrationTestUtil;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class SubjectIntegrationTest extends BaseTest {

    @Autowired
    private SubjectService service;

    @Autowired
    private TeacherService teacherService;

    @BeforeEach
    public void prepareDb(){
        clearDb();
        fillDbWithData();
    }

    private final SubjectIntegrationTestUtil util;
    private final TeacherIntegrationTestUtil teacherUtil;

    public SubjectIntegrationTest() {
        this.util = new SubjectIntegrationTestUtil();
        this.teacherUtil = new TeacherIntegrationTestUtil();
    }

    @Test
    public void shouldSaveNewSubjectWithTeacher(){
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(dto).getId();
        assertNotNull(teacherId);

        SubjectRequestApiDto expected = util.prepareSubjectRequestDto(teacherId);

        // when
        Long subjectId = service.createNewSubject(expected).getId();

        // then
        assertNotNull(subjectId);
        Subject actual = find(Subject.class, subjectId);
        assertNotNull(actual);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getTeacher(), actual.getTeacher().getId());
    }

    @Test
    public void shouldSaveNewSubjectWithoutTeacher(){
        // given
        SubjectRequestApiDto expected = util.prepareSubjectRequestDto(null);

        // when
        Long subjectId = service.createNewSubject(expected).getId();

        // then
        assertNotNull(subjectId);
        Subject actual = find(Subject.class, subjectId);
        assertNotNull(actual);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertNull(actual.getTeacher());
    }

    @Test
    public void shouldThrowsExceptionWhenSaveNewSubjectAndTeacherNotExist(){
        // given
        Long idTeacher = 1L;
        SubjectRequestApiDto expected = util.prepareSubjectRequestDto(1L);

        // when
        Throwable throwable = catchThrowable(() -> service.createNewSubject(expected));

        // then
        assertThat(throwable).hasMessageContaining("Teacher with id "+ idTeacher +" not found" );
    }

    @Test
    public void shouldFindSubjectWithGivenId(){
        // given
        SubjectRequestApiDto expected = util.prepareSubjectRequestDto(null);
        Long idSubject = service.createNewSubject(expected).getId();
        assertNotNull(idSubject);

        // when
        SubjectResponseApiDto actual = service.findSubjectById(idSubject);

        // then
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertNull(actual.getIdTeacher());
    }

    @Test
    public void shouldFindListOfSubjects(){
        // given
        SubjectRequestApiDto firstSubject = util.prepareSubjectRequestDto(null);
        SubjectRequestApiDto secondSubject = util.prepareSubjectRequestDto("Chemia", null);

        Long firstSubjectId = service.createNewSubject(firstSubject).getId();
        assertNotNull(firstSubjectId);
        Long secondSubjectId = service.createNewSubject(secondSubject).getId();
        assertNotNull(secondSubjectId);

        // when
        List<SubjectResponseApiDto> actual = service.findAllSubjects();

        // then
        assertEquals(2,actual.size());

    }
}
