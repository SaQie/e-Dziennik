package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.dto.teacher.DetailedTeacherDto;
import pl.edziennik.common.dto.teacher.TeacherSubjectsSummaryDto;
import pl.edziennik.common.dto.teacher.TeacherSummaryDto;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


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
    public void shouldReturnDetailedTeacherDto() {
        // given
        SchoolId schoolId = createSchool(expectedSchoolName, "123123", "123123");
        TeacherId teacherId = createTeacher(expectedUsername, expectedEmail, expectedPesel, schoolId);

        // when
        DetailedTeacherDto dto = teacherQueryRepository.getTeacher(teacherId);

        // then
        assertNotNull(dto);
        assertEquals(dto.teacherId(), teacherId);
        assertEquals(dto.email(), Email.of(expectedEmail));
        assertEquals(dto.address(), Address.of(expectedAddress));
        assertEquals(dto.username(), Username.of(expectedUsername));
        assertEquals(dto.pesel(), Pesel.of(expectedPesel));
        assertEquals(dto.city(), City.of(expectedCity));
        assertEquals(dto.fullName(), FullName.of(expectedFirstName + " " + expectedLastName));
        assertEquals(dto.postalCode(), PostalCode.of(expectedPostalCode));
        assertEquals(dto.schoolName(), Name.of(expectedSchoolName));
        assertEquals(dto.schoolId(), schoolId);

    }

    @Test
    public void shouldReturnTeacherSummaryDto() {
        // given
        SchoolId schoolId = createSchool(expectedSchoolName, "123123", "123123");
        TeacherId teacherId = createTeacher(expectedUsername, expectedEmail, expectedPesel, schoolId);
        TeacherId secondTeacherId = createTeacher(expectedUsernameSecond, expectedEmailSecond, expectedPeselSecond, schoolId);

        // when
        Page<TeacherSummaryDto> dtos = teacherQueryRepository.findAllWithPagination(Pageable.unpaged());

        // then
        assertNotNull(dtos);
        List<TeacherSummaryDto> dtoList = dtos.get().toList();
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
    public void shouldReturnTeacherSubjectsSummaryDto() {
        // given
        SchoolId schoolId = createSchool(expectedSchoolName, "123123", "123123");
        TeacherId teacherId = createTeacher(expectedUsername, expectedEmail, expectedPesel, schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, expectedSchoolClassName);
        SchoolClassId schoolClassIdSecond = createSchoolClass(schoolId, teacherId, expectedSchoolClassNameSecond);

        SubjectId subjectId = createSubject(expectedSubjectName, schoolClassId, teacherId);
        SubjectId subjectIdSecond = createSubject(expectedSubjectNameSecond, schoolClassIdSecond, teacherId);

        // when
        Page<TeacherSubjectsSummaryDto> dtos = teacherQueryRepository.getTeacherSubjectsSummaryWithPagination(
                Pageable.unpaged(), teacherId);

        // then
        assertNotNull(dtos);
        List<TeacherSubjectsSummaryDto> dtoList = dtos.get().toList();
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
