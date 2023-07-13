package pl.edziennik.application.integration.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.query.teacher.detailed.GetDetailedTeacherQuery;
import pl.edziennik.application.query.teacher.subjects.GetTeacherSubjectsSummaryQuery;
import pl.edziennik.application.query.teacher.summary.GetTeacherSummaryQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.teacher.DetailedTeacherDto;
import pl.edziennik.common.dto.teacher.TeacherSubjectsSummaryDto;
import pl.edziennik.common.dto.teacher.TeacherSummaryDto;
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
public class TeacherQueryIntegrationTest extends BaseIntegrationTest {


    @Test
    public void shouldReturnTeacherSummaryUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123132", "123123");
        createTeacher("Test", "111@o2.pl", "123123", schoolId);
        GetTeacherSummaryQuery query = new GetTeacherSummaryQuery(Pageable.unpaged());

        // when
        PageDto<TeacherSummaryDto> dto = dispatcher.dispatch(query);

        // then
        assertNotNull(dto);
        assertEquals(1, dto.getContent().size());
    }

    @Test
    @Transactional
    public void shouldReturnTeacherSubjectsSummaryUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123132", "123123");
        TeacherId teacherId = createTeacher("Test", "111@o2.pl", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        createSubject("Przyroda", schoolClassId, teacherId);

        GetTeacherSubjectsSummaryQuery query = new GetTeacherSubjectsSummaryQuery(Pageable.unpaged(), teacherId);

        // when
        PageDto<TeacherSubjectsSummaryDto> pageDto = dispatcher.dispatch(query);

        // then
        assertNotNull(pageDto);
        assertEquals(1, pageDto.getContent().size());
    }

    @Test
    public void shouldReturnDetailedTeacherUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123132", "123123");
        TeacherId teacherId = createTeacher("Test", "111@o2.pl", "123123", schoolId);

        GetDetailedTeacherQuery query = new GetDetailedTeacherQuery(teacherId);

        // when
        DetailedTeacherDto dto = dispatcher.dispatch(query);

        // then
        assertNotNull(dto);
    }

    @Test
    public void shouldThrowExceptionWhileGettingDetailedTeacherAndTeacherNotExists() {
        // given
        GetDetailedTeacherQuery query = new GetDetailedTeacherQuery(TeacherId.create());

        try {
            // when
            dispatcher.dispatch(query);
            Assertions.fail("Should throw exception when trying to get detailed teacher data and teacher not exists");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertEquals(ErrorCode.OBJECT_NOT_EXISTS.errorCode(), errors.get(0).errorCode());
        }
    }

}
