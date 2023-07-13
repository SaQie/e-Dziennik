package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.dto.subject.DetailedSubjectDto;
import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class SubjectQueryProjectionTest extends BaseIntegrationTest {

    public final String expectedSchoolClassName = "1A";
    public final String expectedSubjectName = "Przyroda";

    @Test
    public void shouldReturnDetailedSubjectDto(){
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId,teacherId, expectedSchoolClassName);
        SubjectId subjectId = transactionTemplate.execute((x) -> createSubject(expectedSubjectName, schoolClassId, teacherId));

        // when
        DetailedSubjectDto dto = subjectQueryRepository.getSubject(subjectId);

        // then
        assertNotNull(dto);
        assertEquals(dto.subjectId(), subjectId);
        assertEquals(dto.teacherId(), teacherId);
        assertEquals(dto.name().value(), expectedSubjectName);
        assertEquals(dto.teacherFullName(), FullName.of("Test" + " Test"));
        assertEquals(dto.schoolClassName().value(), expectedSchoolClassName);
    }

}
