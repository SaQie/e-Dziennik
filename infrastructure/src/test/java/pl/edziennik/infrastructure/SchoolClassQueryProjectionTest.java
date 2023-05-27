package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.dto.schoolclass.DetailedSchoolClassDto;
import pl.edziennik.common.dto.schoolclass.SchoolClassSummaryForSchoolDto;
import pl.edziennik.common.dto.schoolclass.StudentSummaryForSchoolClassDto;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.TeacherId;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
public class SchoolClassQueryProjectionTest extends BaseIntegrationTest {

    private final String expectedSchoolName = "Test";
    private final String expectedSchoolClassName = "1A";
    private final String expectedSchoolClassNameSecond = "2A";

    @Test
    @Transactional
    public void shouldReturnDetailedSchoolClassDto() {
        // given
        SchoolId schoolId = createSchool(expectedSchoolName, "123123123123", "123123123");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "12312312312", schoolId);

        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, expectedSchoolClassName);

        StudentId studentId = createStudent("Test", "Test@Example.com", "123123123", schoolId, schoolClassId);

        // when
        DetailedSchoolClassDto dto = schoolClassQueryRepository.getSchoolClass(schoolClassId);
        List<StudentSummaryForSchoolClassDto> studentsSummary = schoolClassQueryRepository.getSchoolClassStudentsSummary(schoolClassId);

        // then
        assertNotNull(dto);
        assertEquals(dto.className().value(), expectedSchoolClassName);
        assertEquals(dto.schoolClassId(), schoolClassId);
        assertEquals(dto.supervisingTeacherId(), teacherId);
        assertEquals(dto.supervisingTeacherName().value(), "Test Test");

        assertEquals(1,studentsSummary.size());
        assertEquals(studentsSummary.get(0).studentId(), studentId);
        assertEquals(studentsSummary.get(0).fullName().value(), "Test Test");
        assertEquals(studentsSummary.get(0).journalNumber().value(), 1);
    }

    @Test
    public void shouldReturnSchoolClassSummaryForSchoolDto(){
        // given
        SchoolId schoolId = createSchool(expectedSchoolName, "123123123123", "123123123");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "12312312312", schoolId);
        TeacherId teacherIdSecond = createTeacher("Test2", "test2@example.com", "1231242424", schoolId);

        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, expectedSchoolClassName);
        SchoolClassId schoolClassIdSecond = createSchoolClass(schoolId, teacherIdSecond, expectedSchoolClassNameSecond);

        // when
        Page<SchoolClassSummaryForSchoolDto> dto = schoolClassQueryRepository.findAllWithPaginationForSchool(Pageable.unpaged(), schoolId);

        // then
        assertNotNull(dto);
        List<SchoolClassSummaryForSchoolDto> list = dto.get().toList();

        assertEquals(list.get(0).schoolClassId(), schoolClassId);
        assertEquals(list.get(0).schoolClassName().value(), expectedSchoolClassName);

        assertEquals(list.get(1).schoolClassId(), schoolClassIdSecond);
        assertEquals(list.get(1).schoolClassName().value(), expectedSchoolClassNameSecond);
    }


}
