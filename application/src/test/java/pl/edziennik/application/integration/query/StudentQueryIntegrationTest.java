package pl.edziennik.application.integration.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.student.DetailedStudentView;
import pl.edziennik.common.view.student.StudentSummaryView;
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

        // when
        DetailedStudentView view = studentQueryDao.getDetailedStudentView(studentId);

        // then
        assertNotNull(view);
        assertEquals(view.studentId(), studentId);
    }

    @Test
    @Transactional
    public void shouldThrowExceptionWhileGettingDetailedStudentDataAndStudentNotExists() {
        try {
            // given
            // when
            studentQueryDao.getDetailedStudentView(StudentId.create());
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

        // when
        PageView<StudentSummaryView> pageView = studentQueryDao.getStudentSummaryView(Pageable.unpaged());

        // then
        assertNotNull(pageView);
        assertEquals(pageView.getContent().size(), 1);
    }

}
