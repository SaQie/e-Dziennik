package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.subject.DetailedSubjectView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class SubjectQueryProjectionTest extends BaseIntegrationTest {

    public final String expectedSchoolClassName = "1A";
    public final String expectedSubjectName = "Przyroda";

    @Test
    public void shouldReturnDetailedSubjectView(){
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId,teacherId, expectedSchoolClassName);
        SubjectId subjectId = transactionTemplate.execute((x) -> createSubject(expectedSubjectName, schoolClassId, teacherId));

        // when
        DetailedSubjectView view = subjectQueryRepository.getSubjectView(subjectId);

        // then
        assertNotNull(view);
        assertEquals(view.subjectId(), subjectId);
        assertEquals(view.teacherId(), teacherId);
        assertEquals(view.name().value(), expectedSubjectName);
        assertEquals(view.teacherFullName(), FullName.of("Test" + " Test"));
        assertEquals(view.schoolClassName().value(), expectedSchoolClassName);
    }

}
