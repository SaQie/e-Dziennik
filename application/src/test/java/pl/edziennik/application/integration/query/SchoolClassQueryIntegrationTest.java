package pl.edziennik.application.integration.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.query.schoolclass.config.GetSchoolClassConfigSummaryQuery;
import pl.edziennik.application.query.schoolclass.detailed.GetDetailedSchoolClassQuery;
import pl.edziennik.application.query.schoolclass.summary.GetSchoolClassSummaryForSchoolQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.schoolclass.DetailedSchoolClassDto;
import pl.edziennik.common.dto.schoolclass.SchoolClassSummaryForSchoolDto;
import pl.edziennik.common.dto.schoolclass.config.SchoolClassConfigSummaryDto;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class SchoolClassQueryIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldReturnDetailedSchoolClassUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("Test", "123123@o2.pl", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");

        GetDetailedSchoolClassQuery query = new GetDetailedSchoolClassQuery(schoolClassId);
        // when
        DetailedSchoolClassDto dto = dispatcher.dispatch(query);

        // then
        assertNotNull(dto);
        assertEquals(dto.schoolClassId(), schoolClassId);
    }

    @Test
    public void shouldReturnSchoolClassSummaryUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("Test", "123123@o2.pl", "123123", schoolId);
        TeacherId teacherIdSecond = createTeacher("Test2", "123@o2.pl", "111", schoolId);
        createSchoolClass(schoolId, teacherId, "1A");
        createSchoolClass(schoolId, teacherIdSecond, "2A");

        GetSchoolClassSummaryForSchoolQuery query = new GetSchoolClassSummaryForSchoolQuery(schoolId, Pageable.unpaged());
        // when
        PageDto<SchoolClassSummaryForSchoolDto> dto = dispatcher.dispatch(query);

        // then
        assertNotNull(dto);
        assertEquals(dto.getContent().size(), 2);
    }

    @Test
    public void shouldGetSchoolClassConfigurationDataUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("Test", "123123@o2.pl", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");

        GetSchoolClassConfigSummaryQuery query = new GetSchoolClassConfigSummaryQuery(schoolClassId);
        // when
        SchoolClassConfigSummaryDto dto = dispatcher.dispatch(query);

        // then
        assertNotNull(dto);
        assertEquals(dto.schoolClassId(), schoolClassId);
    }

    @Test
    public void shouldThrowExceptionWhileGettingDetailedSchoolClassDataAndSchoolClassNotExists() {
        // given
        GetDetailedSchoolClassQuery query = new GetDetailedSchoolClassQuery(SchoolClassId.create());

        try {
            // when
            dispatcher.dispatch(query);
            Assertions.fail("Should throw exception when getting detailed school class data and school class not exists");
            // then
        } catch (BusinessException e) {
            List<ValidationError> errors = e.getErrors();
            assertEquals(errors.size(), 1);
            assertEquals(errors.get(0).errorCode(), ErrorCode.OBJECT_NOT_EXISTS.errorCode());
        }
    }

    @Test
    public void shouldThrowExceptionWhileGettingSchoolClassConfigurationDataAndSchoolClassNotExists() {
        // given
        GetDetailedSchoolClassQuery query = new GetDetailedSchoolClassQuery(SchoolClassId.create());

        try {
            // when
            dispatcher.dispatch(query);
            Assertions.fail("Should throw exception when getting school class configuration data and school class not exists");
            // then
        } catch (BusinessException e) {
            List<ValidationError> errors = e.getErrors();
            assertEquals(errors.size(), 1);
            assertEquals(errors.get(0).errorCode(), ErrorCode.OBJECT_NOT_EXISTS.errorCode());
        }
    }

}