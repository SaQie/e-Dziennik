package pl.edziennik.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.common.view.teacherschedule.summary.TeacherScheduleSummaryView;
import pl.edziennik.common.view.teacherschedule.summaryforschool.TeacherScheduleSummaryForSchoolView;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class TeacherScheduleQueryProjectionTest extends BaseIntegrationTest {

    // 10:00 -> 10:30
    private static final LocalDateTime DATE_2022_01_01_10_00 = LocalDateTime.of(2022, 1, 1, 10, 0);
    private static final LocalDateTime DATE_2022_01_01_10_30 = LocalDateTime.of(2022, 1, 1, 10, 30);

    // 11:00 -> 11:30
    private static final LocalDateTime DATE_2022_01_01_11_00 = LocalDateTime.of(2022, 1, 1, 11, 0);
    private static final LocalDateTime DATE_2022_01_01_11_30 = LocalDateTime.of(2022, 1, 1, 11, 30);

    // 12:00 -> 12:30
    private static final LocalDateTime DATE_2022_01_01_12_00 = LocalDateTime.of(2022, 1, 1, 12, 0);
    private static final LocalDateTime DATE_2022_01_01_12_30 = LocalDateTime.of(2022, 1, 1, 12, 30);

    private SchoolId schoolId;

    @BeforeEach
    public void prepareSchool() {
        schoolId = createSchool("TEST", "123123", "123123");
    }

    @Test
    public void shouldReturnTeacherScheduleSummaryView() {
        // given
        TeacherId teacherId = createTeacher("TEST", "asdasd@o2.pl", "123123123", schoolId);
        TeacherScheduleId teacherSchedule = createTeacherSchedule(teacherId, TimeFrame.of(DATE_2022_01_01_11_00, DATE_2022_01_01_12_00));

        // when
        Page<TeacherScheduleSummaryView> teacherScheduleView = teacherScheduleQueryRepository.getTeacherScheduleView(Pageable.unpaged(), teacherId);

        // then
        assertNotNull(teacherScheduleView);
        List<TeacherScheduleSummaryView> content = teacherScheduleView.getContent();
        assertEquals(1, content.size());
        TeacherScheduleSummaryView scheduleSummaryView = content.get(0);
        checkTimeFrame(DATE_2022_01_01_11_00, DATE_2022_01_01_12_00, scheduleSummaryView.timeFrame());

    }

    @Test
    public void shouldReturnTeacherScheduleSummaryViewForSchool() {
        // given
        TeacherId teacherIdFirst = createTeacher("TEST", "asdasd@o2.pl", "123123123", schoolId);
        TeacherId teacherIdSecond = createTeacher("TEST2", "asdasdsss@o2.pl", "1231234444", schoolId);

        // Create schedules for first teacher ( 10:00 -> 10:30 | 11:30 -> 12:30 )
        TeacherScheduleId firstTeacherScheduleFirst = createTeacherSchedule(teacherIdFirst,
                TimeFrame.of(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30));
        TeacherScheduleId firstTeacherScheduleSecond = createTeacherSchedule(teacherIdFirst,
                TimeFrame.of(DATE_2022_01_01_11_30, DATE_2022_01_01_12_30));

        // Create schedules for second teacher (10:00 -> 12:30)
        TeacherScheduleId secondTeacherScheduleFirst = createTeacherSchedule(teacherIdSecond,
                TimeFrame.of(DATE_2022_01_01_10_30, DATE_2022_01_01_12_30));

        // when
        Page<TeacherScheduleSummaryForSchoolView> view = teacherScheduleQueryRepository.getTeachersSchedulesForSchoolView(Pageable.unpaged(), schoolId);

        List<TeacherScheduleSummaryView> firstTeacherSchedulesView = teacherScheduleQueryRepository.getTeacherSchedulesView(teacherIdFirst);
        List<TeacherScheduleSummaryView> secondTeacherSchedulesView = teacherScheduleQueryRepository.getTeacherSchedulesView(teacherIdSecond);

        // then
        assertNotNull(view);
        assertEquals(2, firstTeacherSchedulesView.size());
        assertEquals(1, secondTeacherSchedulesView.size());

        // Check header view
        List<TeacherScheduleSummaryForSchoolView> content = view.getContent();
        assertEquals(2, content.size());
        assertEquals(teacherIdFirst, content.get(0).teacherId());
        assertEquals(teacherIdSecond, content.get(1).teacherId());

        // Check first teacher schedules
        assertEquals(2, firstTeacherSchedulesView.size());
        TeacherScheduleSummaryView scheduleSummaryView = firstTeacherSchedulesView.get(0);
        checkTimeFrame(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, scheduleSummaryView.timeFrame());
        scheduleSummaryView = firstTeacherSchedulesView.get(1);
        checkTimeFrame(DATE_2022_01_01_11_30, DATE_2022_01_01_12_30, scheduleSummaryView.timeFrame());

        // Check second teacher schedules
        assertEquals(1, secondTeacherSchedulesView.size());
        scheduleSummaryView = secondTeacherSchedulesView.get(0);
        checkTimeFrame(DATE_2022_01_01_10_30, DATE_2022_01_01_12_30, scheduleSummaryView.timeFrame());

    }

}
