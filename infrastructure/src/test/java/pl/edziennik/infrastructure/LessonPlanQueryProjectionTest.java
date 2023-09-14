package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.valueobject.id.*;
import pl.edziennik.common.valueobject.vo.*;
import pl.edziennik.common.view.lessonplan.DetailedLessonPlanView;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
public class LessonPlanQueryProjectionTest extends BaseIntegrationTest {

    // 10:00 -> 10:30
    private static final LocalDateTime DATE_2022_01_01_10_00 = LocalDateTime.of(2022, 1, 1, 10, 0);
    private static final LocalDateTime DATE_2022_01_01_10_30 = LocalDateTime.of(2022, 1, 1, 10, 30);

    @Test
    public void shouldReturnDetailedLessonPlanView() {
        // given
        SchoolId schoolId = createSchool("TEST", "123123123", "123123");
        TeacherId teacherId = createTeacher("TEST", "test@example.com", "123123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1A");
        SubjectId subjectId = transactionTemplate.execute((x) ->createSubject("Przyroda", schoolClassId, teacherId));
        ClassRoomId classRoomId = createClassRoom(schoolId, "122A");

        LessonPlanId lessonPlanId = createLessonPlan(schoolClassId, teacherId, subjectId,
                classRoomId, TimeFrame.of(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30));

        // when
        DetailedLessonPlanView view = lessonPlanQueryRepository.getDetailedLessonPlanView(lessonPlanId);

        // then
        assertEquals(LessonOrder.of(1), view.lessonOrder());
        assertEquals(ClassRoomName.of("122A"), view.classRoomName());
        assertEquals(Name.of("Przyroda"), view.subjectName());
        assertEquals(FullName.of("Test Test"), view.teacherName());
        checkTimeFrame(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, view.timeFrame());
    }

}
