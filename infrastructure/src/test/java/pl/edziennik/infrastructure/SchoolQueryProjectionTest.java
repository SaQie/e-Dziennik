package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.dto.school.DetailedSchoolDto;
import pl.edziennik.common.dto.school.SchoolSummaryDto;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SchoolLevelId;

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
    public void shouldReturnDetailedSchoolDto() {
        // given
        SchoolId schoolId = createSchool(expectedSchoolName, expectedNip, expectedRegon);

        // when
        DetailedSchoolDto dto = schoolQueryRepository.getSchool(schoolId);

        // then
        assertNotNull(dto);
        assertEquals(dto.name().value(), expectedSchoolName);
        assertEquals(dto.address().value(), expectedAddress);
        assertEquals(dto.schoolId(), schoolId);
        assertEquals(dto.nip().value(), expectedNip);
        assertEquals(dto.city().value(), expectedCity);
        assertEquals(dto.regon().value(), expectedRegon);
    }

    @Test
    public void shouldReturnSchoolSummaryDto() {
        // given
        SchoolId schoolId = createSchool(expectedSchoolName, expectedNip, expectedRegon);
        SchoolId schoolIdSecond = createSchool(expectedSchoolNameSecond, expectedNipSecond, expectedRegonSecond);

        // when
        Page<SchoolSummaryDto> dto = schoolQueryRepository.findAllWithPagination(Pageable.unpaged());

        // then
        assertNotNull(dto);
        List<SchoolSummaryDto> list = dto.get().toList();
        assertEquals(list.size(), 2);

        assertEquals(list.get(0).schoolId().id(), schoolId.id());
        assertEquals(list.get(0).schoolLevelId(), SchoolLevelId.PredefinedRow.PRIMARY_SCHOOL);

        assertEquals(list.get(1).schoolId().id(), schoolIdSecond.id());
        assertEquals(list.get(1).schoolLevelId(), SchoolLevelId.PredefinedRow.PRIMARY_SCHOOL);
    }

}
