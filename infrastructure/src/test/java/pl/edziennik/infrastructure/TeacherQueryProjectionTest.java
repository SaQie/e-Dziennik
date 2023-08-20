package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.*;
import pl.edziennik.common.view.teacher.DetailedTeacherView;
import pl.edziennik.common.view.teacher.TeacherSubjectsSummaryView;
import pl.edziennik.common.view.teacher.TeacherSummaryView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DirtiesContext
public class TeacherQueryProjectionTest extends BaseIntegrationTest {

    final String expectedSchoolClassName = "1A";
    final String expectedSchoolClassNameSecond = "1B";

    final String expectedUsername = "Kamil";
    final String expectedEmail = "Test@example.com";
    final String expectedPesel = "1234567890";
    final String expectedSchoolName = "Test";
    final String expectedFirstName = "Test";
    final String expectedLastName = "Test";
    final String expectedCity = "Test";
    final String expectedPostalCode = "12-123";
    final String expectedAddress = "Test";

    final String expectedUsernameSecond = "Kamil1";
    final String expectedEmailSecond = "Test1@example.com";
    final String expectedPeselSecond = "1234567899";

    final String expectedSubjectName = "Przyroda";
    final String expectedSubjectNameSecond = "Chemia";

    @Test
    public void shouldReturnDetailedTeacherView() {
        // given
        SchoolId schoolId = createSchool(expectedSchoolName, "123123", "123123");
        TeacherId teacherId = createTeacher(expectedUsername, expectedEmail, expectedPesel, schoolId);

        // when
        DetailedTeacherView view = teacherQueryRepository.getTeacherView(teacherId);

        // then
        assertNotNull(view);
        assertEquals(view.teacherId(), teacherId);
        assertEquals(view.email(), Email.of(expectedEmail));
        assertEquals(view.address(), Address.of(expectedAddress));
        assertEquals(view.username(), Username.of(expectedUsername));
        assertEquals(view.pesel(), Pesel.of(expectedPesel));
        assertEquals(view.city(), City.of(expectedCity));
        assertEquals(view.fullName(), FullName.of(expectedFirstName + " " + expectedLastName));
        assertEquals(view.postalCode(), PostalCode.of(expectedPostalCode));
        assertEquals(view.schoolName(), Name.of(expectedSchoolName));
        assertEquals(view.schoolId(), schoolId);

    }

    @Test
    public void shouldReturnTeacherSummaryView() {
        // given
        SchoolId schoolId = createSchool(expectedSchoolName, "123123", "123123");
        TeacherId teacherId = createTeacher(expectedUsername, expectedEmail, expectedPesel, schoolId);
        TeacherId secondTeacherId = createTeacher(expectedUsernameSecond, expectedEmailSecond, expectedPeselSecond, schoolId);

        // when
        Page<TeacherSummaryView> dtos = teacherQueryRepository.findAllWithPagination(Pageable.unpaged());

        // then
        assertNotNull(dtos);
        List<TeacherSummaryView> dtoList = dtos.get().toList();
        assertEquals(2, dtoList.size());

        assertEquals(dtoList.get(0).teacherId(), teacherId);
        assertEquals(dtoList.get(0).schoolName(), Name.of(expectedSchoolName));
        assertEquals(dtoList.get(0).username(), Username.of(expectedUsername));
        assertEquals(dtoList.get(0).fullName(), FullName.of(expectedFirstName + " " + expectedLastName));

        assertEquals(dtoList.get(1).teacherId(), secondTeacherId);
        assertEquals(dtoList.get(1).schoolName(), Name.of(expectedSchoolName));
        assertEquals(dtoList.get(1).username(), Username.of(expectedUsernameSecond));
        assertEquals(dtoList.get(1).fullName(), FullName.of(expectedFirstName + " " + expectedLastName));
    }


    @Test
    public void shouldReturnTeacherSubjectsSummaryView() {
        // given
        SchoolId schoolId = createSchool(expectedSchoolName, "123123", "123123");
        TeacherId teacherId = createTeacher(expectedUsername, expectedEmail, expectedPesel, schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, expectedSchoolClassName);
        SchoolClassId schoolClassIdSecond = createSchoolClass(schoolId, teacherId, expectedSchoolClassNameSecond);

        SubjectId subjectId = transactionTemplate.execute((x) -> createSubject(expectedSubjectName, schoolClassId, teacherId));
        SubjectId subjectIdSecond = transactionTemplate.execute((x) -> createSubject(expectedSubjectNameSecond, schoolClassIdSecond, teacherId));

        // when
        Page<TeacherSubjectsSummaryView> dtos = teacherQueryRepository.getTeacherSubjectsSummaryWithPagination(
                Pageable.unpaged(), teacherId);

        // then
        assertNotNull(dtos);
        List<TeacherSubjectsSummaryView> dtoList = dtos.get().toList();
        assertEquals(2, dtoList.size());

        assertEquals(dtoList.get(0).subjectId(), subjectId);
        assertEquals(dtoList.get(0).name(), Name.of(expectedSubjectName));
        assertEquals(dtoList.get(0).schoolClassName(), Name.of(expectedSchoolClassName));
        assertEquals(dtoList.get(0).schoolClassId(), schoolClassId);

        assertEquals(dtoList.get(1).subjectId(), subjectIdSecond);
        assertEquals(dtoList.get(1).name(), Name.of(expectedSubjectNameSecond));
        assertEquals(dtoList.get(1).schoolClassName(), Name.of(expectedSchoolClassNameSecond));
        assertEquals(dtoList.get(1).schoolClassId(), schoolClassIdSecond);

    }

}
