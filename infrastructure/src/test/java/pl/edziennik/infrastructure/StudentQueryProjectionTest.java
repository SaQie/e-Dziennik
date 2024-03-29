package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.valueobject.vo.FullName;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.student.DetailedStudentView;
import pl.edziennik.common.view.student.StudentSummaryView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class StudentQueryProjectionTest extends BaseIntegrationTest {

    private final String expectedSchoolClassName = "1A";
    private final String expectedStudentName = "Test";
    private final String expectedStudentEmail = "test@examplee.com";
    private final String expectedStudentPesel = "123123123123";
    private final String expectedSecondStudentName = "Test1";
    private final String expectedSecondStudentEmail = "test1@examplee.com";
    private final String expectedSecondStudentPesel = "129123123123";
    private final String expectedSchoolName = "Test";

    @Test
    @Transactional
    public void shouldReturnDetailedStudentView() {
        // given
        SchoolId schoolId = createSchool(expectedSchoolName, "12312313", "123123123");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, expectedSchoolClassName);

        StudentId studentId = createStudent(expectedStudentName, expectedStudentEmail, expectedStudentPesel, schoolId, schoolClassId);

        // when
        DetailedStudentView view = studentQueryRepository.getStudentView(studentId);

        // then
        assertNotNull(view);
        assertEquals(view.username().value(), expectedStudentName);
        assertEquals(view.email().value(), expectedStudentEmail);
        assertEquals(view.schoolClassId(), schoolClassId);
        assertEquals(view.schoolId(), schoolId);
        assertEquals(view.fullName(), FullName.of(expectedStudentName + " " + expectedStudentName));
        assertEquals(view.schoolName().value(), expectedSchoolName);
        assertEquals(view.journalNumber().value(), 1);
    }

    @Test
    @Transactional
    public void shouldReturnStudentSummaryView() {
        // given
        SchoolId schoolId = createSchool(expectedSchoolName, "12312313", "123123123");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, expectedSchoolClassName);

        StudentId studentId = createStudent(expectedStudentName, expectedStudentEmail, expectedStudentPesel, schoolId, schoolClassId);
        StudentId studentIdSecond = createStudent(expectedSecondStudentName, expectedSecondStudentEmail, expectedSecondStudentPesel, schoolId, schoolClassId);

        // when
        Page<StudentSummaryView> dtos = studentQueryRepository.findAllWithPagination(Pageable.unpaged());

        // then
        assertNotNull(dtos);
        List<StudentSummaryView> list = dtos.get().toList();
        assertEquals(2, list.size());

        assertEquals(list.get(0).studentId(), studentId);
        assertEquals(list.get(0).username().value(), expectedStudentName);
        assertEquals(list.get(0).schoolId(), schoolId);
        assertEquals(list.get(0).fullName(), FullName.of(expectedStudentName + " " + expectedStudentName));
        assertEquals(list.get(0).schoolName().value(), expectedSchoolName);
        assertEquals(list.get(0).journalNumber().value(), 1);

        assertEquals(list.get(1).studentId(), studentIdSecond);
        assertEquals(list.get(1).username().value(), expectedSecondStudentName);
        assertEquals(list.get(1).schoolId(), schoolId);
        assertEquals(list.get(1).fullName(), FullName.of(expectedStudentName + " " + expectedStudentName));
        assertEquals(list.get(1).schoolName().value(), expectedSchoolName);
        assertEquals(list.get(1).journalNumber().value(), 2);


    }

}
