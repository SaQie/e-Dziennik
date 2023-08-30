package pl.edziennik.application.integration.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.school.DetailedSchoolView;
import pl.edziennik.common.view.school.SchoolSummaryView;
import pl.edziennik.common.view.school.config.SchoolConfigSummaryView;
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

        // when
        DetailedSchoolView view = schoolQueryDao.getDetailedSchoolView(schoolId);

        // then
        assertNotNull(view);
        assertEquals(view.schoolId(), schoolId);
    }

    @Test
    public void shouldReturnSchoolSummaryUsingQuery() {
        // given
        createSchool("Test", "123123", "123123");

        // when
        PageView<SchoolSummaryView> pageView = schoolQueryDao.getSchoolSummaryView(Pageable.unpaged());

        // then
        assertNotNull(pageView);
        assertEquals(pageView.getContent().size(), 1);
    }

    @Test
    public void shouldThrowExceptionWhileGettingDetailedSchoolDataAndSchoolNotExists() {
        try {
            // given
            // when
            schoolQueryDao.getDetailedSchoolView(SchoolId.create());
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

        // when
        SchoolConfigSummaryView view = schoolQueryDao.getSchoolConfigSummaryView(schoolId);

        // then
        assertNotNull(view);
        assertEquals(view.schoolId(), schoolId);
        assertEquals(view.averageType(), AverageType.ARITHMETIC);
    }

    @Test
    public void shouldThrowExceptionWhileGettingSchoolConfigurationDataAndSchoolNotExists() {


        try {
            // given
            // when
            schoolQueryDao.getSchoolConfigSummaryView(SchoolId.create());
            Assertions.fail("Should throw exception while getting school configuration data and school not exists");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertEquals(ErrorCode.OBJECT_NOT_EXISTS.errorCode(), errors.get(0).errorCode());
        }
    }
}
