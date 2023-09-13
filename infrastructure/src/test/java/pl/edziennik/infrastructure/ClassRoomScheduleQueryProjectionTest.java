package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.ClassRoomScheduleId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.common.view.classroomschedule.summary.ClassRoomScheduleSummaryView;
import pl.edziennik.common.view.classroomschedule.summaryforschool.ClassRoomScheduleSummaryForSchoolView;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class ClassRoomScheduleQueryProjectionTest extends BaseIntegrationTest {

    // 10:00 -> 10:30
    private static final LocalDateTime DATE_2022_01_01_10_00 = LocalDateTime.of(2022, 1, 1, 10, 0);
    private static final LocalDateTime DATE_2022_01_01_10_30 = LocalDateTime.of(2022, 1, 1, 10, 30);

    // 11:00 -> 11:30
    private static final LocalDateTime DATE_2022_01_01_11_00 = LocalDateTime.of(2022, 1, 1, 11, 0);
    private static final LocalDateTime DATE_2022_01_01_11_30 = LocalDateTime.of(2022, 1, 1, 11, 30);

    // 12:00 -> 12:30
    private static final LocalDateTime DATE_2022_01_01_12_00 = LocalDateTime.of(2022, 1, 1, 12, 0);
    private static final LocalDateTime DATE_2022_01_01_12_30 = LocalDateTime.of(2022, 1, 1, 12, 30);


    @Test
    public void shouldReturnClassRoomScheduleSummaryView() {
        // given
        SchoolId schoolId = createSchool("Test", "123123123", "123123123");
        ClassRoomId classRoomIdFirst = createClassRoom(schoolId, "122A");
        // 10:30 -> 12:30
        createClassRoomSchedule(classRoomIdFirst, TimeFrame.of(DATE_2022_01_01_10_30, DATE_2022_01_01_12_30));

        // when
        Page<ClassRoomScheduleSummaryView> view = classRoomScheduleQueryRepository.getClassRoomScheduleView(Pageable.unpaged(), classRoomIdFirst);

        // then
        assertNotNull(view);
        List<ClassRoomScheduleSummaryView> content = view.getContent();
        assertEquals(1, content.size());
        ClassRoomScheduleSummaryView classRoomScheduleSummaryView = content.get(0);
        checkTimeFrame(DATE_2022_01_01_10_30, DATE_2022_01_01_12_30, classRoomScheduleSummaryView.timeFrame());
    }

    @Test
    public void shouldReturnClassRoomSchedulesSummaryViewForSchool() {
        // given

        SchoolId schoolId = createSchool("Test", "123123123", "123123123");
        ClassRoomId classRoomIdFirst = createClassRoom(schoolId, "122A");
        ClassRoomId classRoomIdSecond = createClassRoom(schoolId, "123A");
        ClassRoomId classRoomIdThird = createClassRoom(schoolId, "124A");

        // Schedules for first class room (10:00 -> 10:30 | 11:00 -> 11:30 | 12:00 -> 12:30)
        ClassRoomScheduleId classRoomFirstScheduleIdFirst = createClassRoomSchedule(classRoomIdFirst,
                TimeFrame.of(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30));
        ClassRoomScheduleId classRoomFirstScheduleIdSecond = createClassRoomSchedule(classRoomIdFirst,
                TimeFrame.of(DATE_2022_01_01_11_00, DATE_2022_01_01_11_30));
        ClassRoomScheduleId classRoomFirstScheduleIdThird = createClassRoomSchedule(classRoomIdFirst,
                TimeFrame.of(DATE_2022_01_01_12_00, DATE_2022_01_01_12_30));

        // Schedules for second class room (10:00 -> 10:30) | 12:00 -> 12:30)
        ClassRoomScheduleId classRoomSecondScheduleIdFirst = createClassRoomSchedule(classRoomIdSecond,
                TimeFrame.of(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30));
        ClassRoomScheduleId classRoomSecondScheduleIdSecond = createClassRoomSchedule(classRoomIdSecond,
                TimeFrame.of(DATE_2022_01_01_12_00, DATE_2022_01_01_12_30));

        // Schedules for third class room (12:00 -> 12:30)
        ClassRoomScheduleId classRoomThirdScheduleIdFirst = createClassRoomSchedule(classRoomIdThird,
                TimeFrame.of(DATE_2022_01_01_12_00, DATE_2022_01_01_12_30));

        // when
        Page<ClassRoomScheduleSummaryForSchoolView> classRoomScheduleForSchoolView =
                classRoomScheduleQueryRepository.getClassRoomScheduleForSchoolView(Pageable.unpaged(), schoolId);

        List<ClassRoomScheduleSummaryView> firstClassRoomSchedules = classRoomScheduleQueryRepository.getClassRoomScheduleView(classRoomIdFirst);
        List<ClassRoomScheduleSummaryView> secondClassRoomSchedules = classRoomScheduleQueryRepository.getClassRoomScheduleView(classRoomIdSecond);
        List<ClassRoomScheduleSummaryView> thirdClassRoomSchedules = classRoomScheduleQueryRepository.getClassRoomScheduleView(classRoomIdThird);

        // then
        assertNotNull(classRoomScheduleForSchoolView);
        assertEquals(3, firstClassRoomSchedules.size());
        assertEquals(2, secondClassRoomSchedules.size());
        assertEquals(1, thirdClassRoomSchedules.size());

        // Check header view
        List<ClassRoomScheduleSummaryForSchoolView> header = classRoomScheduleForSchoolView.getContent();
        assertEquals(3, header.size());
        assertEquals(classRoomIdFirst, header.get(0).classRoomId());
        assertEquals(classRoomIdSecond, header.get(1).classRoomId());
        assertEquals(classRoomIdThird, header.get(2).classRoomId());

        // Check first class room schedules
        assertEquals(3, firstClassRoomSchedules.size());

        ClassRoomScheduleSummaryView classRoomScheduleSummaryView = firstClassRoomSchedules.get(0);
        checkTimeFrame(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, classRoomScheduleSummaryView.timeFrame());
        classRoomScheduleSummaryView = firstClassRoomSchedules.get(1);
        checkTimeFrame(DATE_2022_01_01_11_00, DATE_2022_01_01_11_30, classRoomScheduleSummaryView.timeFrame());
        classRoomScheduleSummaryView = firstClassRoomSchedules.get(2);
        checkTimeFrame(DATE_2022_01_01_12_00, DATE_2022_01_01_12_30, classRoomScheduleSummaryView.timeFrame());

        // Check second class room schedules
        assertEquals(2, secondClassRoomSchedules.size());
        classRoomScheduleSummaryView = secondClassRoomSchedules.get(0);
        checkTimeFrame(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, classRoomScheduleSummaryView.timeFrame());
        classRoomScheduleSummaryView = secondClassRoomSchedules.get(1);
        checkTimeFrame(DATE_2022_01_01_12_00, DATE_2022_01_01_12_30, classRoomScheduleSummaryView.timeFrame());

        // Check third class room schedules
        assertEquals(1, thirdClassRoomSchedules.size());
        classRoomScheduleSummaryView = thirdClassRoomSchedules.get(0);
        checkTimeFrame(DATE_2022_01_01_12_00, DATE_2022_01_01_12_30, classRoomScheduleSummaryView.timeFrame());

    }

}
