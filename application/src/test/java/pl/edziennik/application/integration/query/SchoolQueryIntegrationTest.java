package pl.edziennik.application.integration.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.query.school.config.GetSchoolConfigSummaryQuery;
import pl.edziennik.application.query.school.detailed.GetDetailedSchoolQuery;
import pl.edziennik.application.query.school.summary.GetSchoolSummaryQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.school.DetailedSchoolDto;
import pl.edziennik.common.dto.school.SchoolSummaryDto;
import pl.edziennik.common.dto.school.config.SchoolConfigSummaryDto;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class SchoolQueryIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldReturnDetailedSchoolUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");

        GetDetailedSchoolQuery query = new GetDetailedSchoolQuery(schoolId);

        // when
        DetailedSchoolDto dto = dispatcher.dispatch(query);

        // then
        assertNotNull(dto);
        assertEquals(dto.schoolId(), schoolId);
    }

    @Test
    public void shouldReturnSchoolSummaryUsingQuery() {
        // given
        createSchool("Test", "123123", "123123");

        GetSchoolSummaryQuery query = new GetSchoolSummaryQuery(Pageable.unpaged());

        // when
        PageDto<SchoolSummaryDto> pageDto = dispatcher.dispatch(query);

        // then
        assertNotNull(pageDto);
        assertEquals(pageDto.getContent().size(), 1);
    }

    @Test
    public void shouldThrowExceptionWhileGettingDetailedSchoolDataAndSchoolNotExists() {
        // given
        GetDetailedSchoolQuery query = new GetDetailedSchoolQuery(SchoolId.create());

        try {
            // when
            dispatcher.dispatch(query);
            Assertions.fail("Should throw exception while getting detailed school data and school not exists");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertEquals(ErrorCode.OBJECT_NOT_EXISTS.errorCode(), errors.get(0).errorCode());
        }
    }

    @Test
    public void shouldReturnSchoolConfigurationData() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");

        GetSchoolConfigSummaryQuery query = new GetSchoolConfigSummaryQuery(schoolId);

        // when
        SchoolConfigSummaryDto dto = dispatcher.dispatch(query);

        // then
        assertNotNull(dto);
        assertEquals(dto.schoolId(), schoolId);
        assertEquals(dto.averageType(), AverageType.ARITHMETIC);
    }

    @Test
    public void shouldThrowExceptionWhileGettingSchoolConfigurationDataAndSchoolNotExists() {
        // given
        GetSchoolConfigSummaryQuery query = new GetSchoolConfigSummaryQuery(SchoolId.create());

        try {
            // when
            dispatcher.dispatch(query);
            Assertions.fail("Should throw exception while getting school configuration data and school not exists");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertEquals(ErrorCode.OBJECT_NOT_EXISTS.errorCode(), errors.get(0).errorCode());
        }
    }
}
