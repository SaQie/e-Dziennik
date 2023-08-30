package pl.edziennik.application.integration.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacher.DetailedTeacherView;
import pl.edziennik.common.view.teacher.TeacherSubjectsSummaryView;
import pl.edziennik.common.view.teacher.TeacherSummaryView;
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
        // when
        PageView<TeacherSummaryView> view = teacherQueryDao.getTeacherSummaryView(Pageable.unpaged());

        // then
        assertNotNull(view);
        assertEquals(1, view.getContent().size());
    }

    @Test
    @Transactional
    public void shouldReturnTeacherSubjectsSummaryUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123132", "123123");
        TeacherId teacherId = createTeacher("Test", "111@o2.pl", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        createSubject("Przyroda", schoolClassId, teacherId);

        // when
        PageView<TeacherSubjectsSummaryView> pageView = teacherQueryDao.getTeacherSubjectsSummaryView(Pageable.unpaged(), teacherId);

        // then
        assertNotNull(pageView);
        assertEquals(1, pageView.getContent().size());
    }

    @Test
    public void shouldReturnDetailedTeacherUsingQuery() {
        // given
        SchoolId schoolId = createSchool("Test", "123123132", "123123");
        TeacherId teacherId = createTeacher("Test", "111@o2.pl", "123123", schoolId);

        // when
        DetailedTeacherView view = teacherQueryDao.getDetailedTeacherView(teacherId);

        // then
        assertNotNull(view);
    }

    @Test
    public void shouldThrowExceptionWhileGettingDetailedTeacherAndTeacherNotExists() {
        try {
            // given
            // when
            teacherQueryDao.getDetailedTeacherView(TeacherId.create());
            Assertions.fail("Should throw exception when trying to get detailed teacher data and teacher not exists");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertEquals(ErrorCode.OBJECT_NOT_EXISTS.errorCode(), errors.get(0).errorCode());
        }
    }

}
