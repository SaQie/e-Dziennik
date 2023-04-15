package pl.edziennik.eDziennik;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
public class UtilTest extends BaseTesting {


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
        String expectedFullNameAfterSave = studentRequestApiDto.firstName() + " " + studentRequestApiDto.lastName();

        // when
        StudentId id = studentService.register(studentRequestApiDto).studentId();

        // then
        assertNotNull(id);
        Student student = find(Student.class, id);
        PersonInformation personInformation = student.getPersonInformation();
        assertEquals(expectedFullNameAfterSave, personInformation.fullName());
    }

    @Test
    public void shouldAutomaticallyUpdatePersonFullNameWhenUpdate() {
        StudentRequestApiDto studentRequestApiDto = studentUtil.prepareStudentRequestDto();
        StudentId id = studentService.register(studentRequestApiDto).studentId();
        String expectedFullNameAfterUpdate = "Dawid" + " " + "Nowak";
        studentRequestApiDto = studentUtil.prepareStudentRequestDto("Test", "Dawid", "Nowak", "q", "qwe");

        // when
        studentService.updateStudent(id, studentRequestApiDto);

        // then
        assertNotNull(id);
        Student student = find(Student.class, id);
        PersonInformation personInformation = student.getPersonInformation();
        assertEquals(expectedFullNameAfterUpdate, personInformation.fullName());
    }

}
