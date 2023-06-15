package pl.edziennik.application.integration.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.query.student.detailed.GetDetailedStudentQuery;
import pl.edziennik.application.query.student.summary.GetStudentSummaryQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.student.DetailedStudentDto;
import pl.edziennik.common.dto.student.StudentSummaryDto;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class StudentQueryIntegrationTest extends BaseIntegrationTest {

    @Test
    @Transactional
    public void shouldReturnDetailedStudentUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("test", "1321@o2.pl", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        StudentId studentId = createStudent("Test", "123123", "123123", schoolId, schoolClassId);

        GetDetailedStudentQuery query = new GetDetailedStudentQuery(studentId);

        // when
        DetailedStudentDto dto = dispatcher.dispatch(query);

        // then
        assertNotNull(dto);
        assertEquals(dto.studentId(), studentId);
    }

    @Test
    @Transactional
    public void shouldThrowExceptionWhileGettingDetailedStudentDataAndStudentNotExists() {
        // given
        GetDetailedStudentQuery query = new GetDetailedStudentQuery(StudentId.create());

        try {
            // when
            dispatcher.dispatch(query);
            Assertions.fail("Should throw exception while getting detailed student data and student not exists");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertEquals(errors.get(0).errorCode(), ErrorCode.OBJECT_NOT_EXISTS.errorCode());
        }
    }

    @Test
    @Transactional
    public void shouldReturnStudentSummaryUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123", "123123");
        TeacherId teacherId = createTeacher("test", "1321@o2.pl", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        createStudent("Test", "123123", "123123", schoolId, schoolClassId);

        GetStudentSummaryQuery query = new GetStudentSummaryQuery(Pageable.unpaged());

        // when
        PageDto<StudentSummaryDto> pageDto = dispatcher.dispatch(query);

        // then
        assertNotNull(pageDto);
        assertEquals(pageDto.getContent().size(), 1);
    }

}
