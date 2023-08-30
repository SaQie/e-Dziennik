package pl.edziennik.application.integration.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.schoolclass.DetailedSchoolClassView;
import pl.edziennik.common.view.schoolclass.SchoolClassSummaryForSchoolView;
import pl.edziennik.common.view.schoolclass.config.SchoolClassConfigSummaryView;
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

        // when
        DetailedSchoolClassView view = schoolClassQueryDao.getDetailedSchoolClassView(schoolClassId);

        // then
        assertNotNull(view);
        assertEquals(view.schoolClassId(), schoolClassId);
    }

    @Test
    public void shouldReturnSchoolClassSummaryUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("Test", "123123@o2.pl", "123123", schoolId);
        TeacherId teacherIdSecond = createTeacher("Test2", "123@o2.pl", "111", schoolId);
        createSchoolClass(schoolId, teacherId, "1A");
        createSchoolClass(schoolId, teacherIdSecond, "2A");

        // when
        PageView<SchoolClassSummaryForSchoolView> view = schoolClassQueryDao.getSchoolClassSummaryForSchoolView(schoolId, Pageable.unpaged());

        // then
        assertNotNull(view);
        assertEquals(view.getContent().size(), 2);
    }

    @Test
    public void shouldGetSchoolClassConfigurationDataUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("Test", "123123@o2.pl", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");

        // when
        SchoolClassConfigSummaryView view = schoolClassQueryDao.getSchoolClassConfigSummaryView(schoolClassId);

        // then
        assertNotNull(view);
        assertEquals(view.schoolClassId(), schoolClassId);
    }

    @Test
    public void shouldThrowExceptionWhileGettingDetailedSchoolClassDataAndSchoolClassNotExists() {
        try {
            // given
            // when
            schoolClassQueryDao.getDetailedSchoolClassView(SchoolClassId.create());
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
        try {
            // given
            // when
            schoolClassQueryDao.getSchoolClassConfigSummaryView(SchoolClassId.create());
            Assertions.fail("Should throw exception when getting school class configuration data and school class not exists");
            // then
        } catch (BusinessException e) {
            List<ValidationError> errors = e.getErrors();
            assertEquals(errors.size(), 1);
            assertEquals(errors.get(0).errorCode(), ErrorCode.OBJECT_NOT_EXISTS.errorCode());
        }
    }

}
