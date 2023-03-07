package pl.edziennik.eDziennik;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.student.StudentIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.student.controller.StudentController;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.domain.student.services.StudentService;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.server.basics.dao.BaseDao;
import pl.edziennik.eDziennik.server.basics.dto.Page;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
public class UtilTest extends BaseTest {

    @Autowired
    private StudentService studentService;


    @BeforeEach
    public void prepareDb() {
        clearDb();
        fillDbWithData();
    }

    @Test
    public void shouldAutmaticallyInsertPersonFullNameWhenSave() {
        // given
        // first name - Kamil
        // last name - Nowak
        StudentRequestApiDto studentRequestApiDto = studentUtil.prepareStudentRequestDto();
        String expectedFullNameAfterSave = studentRequestApiDto.getFirstName() + " " + studentRequestApiDto.getLastName();

        // when
        Long id = studentService.register(studentRequestApiDto).getId();

        // then
        assertNotNull(id);
        Student student = find(Student.class, id);
        PersonInformation personInformation = student.getPersonInformation();
        assertEquals(expectedFullNameAfterSave, personInformation.getFullName());
    }

    @Test
    public void shouldThrowsExceptionWhenPageRequestValuesIsNegativeOrZero() {
        // given
        PageRequest pageRequest = new PageRequest(0, 0);
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> studentService.findAllStudents(pageRequest));

        // then
        assertNotNull(exception);
    }

    @Test
    public void shouldShowOnlySpecificPageRequestValuesWhenPageable() {
        // given
        int page = 1;
        int size = 5;
        int expectedPagesCount = 3;
        int expectedItemsTotalCount = 15;
        List<StudentRequestApiDto> studentRequestApiDtos = studentUtil.prepareTestsStudents(expectedItemsTotalCount);
        studentRequestApiDtos.forEach(dto -> studentService.register(dto));

        // when
        Page<List<StudentResponseApiDto>> firstPage = studentService.findAllStudents(new PageRequest(1, size));

        Page<List<StudentResponseApiDto>> secondPage = studentService.findAllStudents(new PageRequest(2, size));

        Page<List<StudentResponseApiDto>> thirdPage = studentService.findAllStudents(new PageRequest(3, size));

        Page<List<StudentResponseApiDto>> fourthPage = studentService.findAllStudents(new PageRequest(4, size));

        // then

        // first page
        assertEquals(page, firstPage.getActualPage());
        assertEquals(size, firstPage.getItemsOnPage());
        assertEquals(expectedPagesCount, firstPage.getPagesCount());
        assertEquals(expectedItemsTotalCount, firstPage.getItemsTotalCount());
        assertEquals(size, firstPage.getEntities().size());

        // second page

        assertEquals(2, secondPage.getActualPage());
        assertEquals(size, secondPage.getItemsOnPage());
        assertEquals(expectedPagesCount, secondPage.getPagesCount());
        assertEquals(expectedItemsTotalCount, secondPage.getItemsTotalCount());
        assertEquals(size, secondPage.getEntities().size());

        // third page

        assertEquals(3, thirdPage.getActualPage());
        assertEquals(size, thirdPage.getItemsOnPage());
        assertEquals(expectedPagesCount, thirdPage.getPagesCount());
        assertEquals(expectedItemsTotalCount, thirdPage.getItemsTotalCount());
        assertEquals(size, thirdPage.getEntities().size());

        // fourth page

        assertEquals(4, fourthPage.getActualPage());
        assertEquals(size, fourthPage.getItemsOnPage());
        assertEquals(expectedPagesCount, fourthPage.getPagesCount());
        assertEquals(expectedItemsTotalCount, fourthPage.getItemsTotalCount());
        assertEquals(0, fourthPage.getEntities().size());

    }

}
