package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.common.view.school.DetailedSchoolView;
import pl.edziennik.common.view.school.SchoolSummaryView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class SchoolQueryProjectionTest extends BaseIntegrationTest {

    private final String expectedSchoolName = "Test";
    private final String expectedSchoolNameSecond = "Test2";
    private final String expectedNip = "123123123";
    private final String expectedNipSecond = "923123123";
    private final String expectedRegon = "123123123";
    private final String expectedRegonSecond = "9123123123";
    private final String expectedCity = "Test";
    private final String expectedAddress = "Test";

    @Test
    public void shouldReturnDetailedSchoolView() {
        // given
        SchoolId schoolId = createSchool(expectedSchoolName, expectedNip, expectedRegon);

        // when
        DetailedSchoolView view = schoolQueryRepository.getSchoolView(schoolId);

        // then
        assertNotNull(view);
        assertEquals(view.name().value(), expectedSchoolName);
        assertEquals(view.address().value(), expectedAddress);
        assertEquals(view.schoolId(), schoolId);
        assertEquals(view.nip().value(), expectedNip);
        assertEquals(view.city().value(), expectedCity);
        assertEquals(view.regon().value(), expectedRegon);
    }

    @Test
    public void shouldReturnSchoolSummaryView() {
        // given
        SchoolId schoolId = createSchool(expectedSchoolName, expectedNip, expectedRegon);
        SchoolId schoolIdSecond = createSchool(expectedSchoolNameSecond, expectedNipSecond, expectedRegonSecond);

        // when
        Page<SchoolSummaryView> view = schoolQueryRepository.findAllWithPagination(Pageable.unpaged());

        // then
        assertNotNull(view);
        List<SchoolSummaryView> list = view.get().toList();
        assertEquals(list.size(), 2);

        assertEquals(list.get(0).schoolId().id(), schoolId.id());
        assertEquals(list.get(0).schoolLevelId(), SchoolLevelId.PredefinedRow.PRIMARY_SCHOOL);

        assertEquals(list.get(1).schoolId().id(), schoolIdSecond.id());
        assertEquals(list.get(1).schoolLevelId(), SchoolLevelId.PredefinedRow.PRIMARY_SCHOOL);
    }

}
