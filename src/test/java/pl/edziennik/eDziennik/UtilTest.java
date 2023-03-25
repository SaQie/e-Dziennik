package pl.edziennik.eDziennik;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.services.StudentService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

}