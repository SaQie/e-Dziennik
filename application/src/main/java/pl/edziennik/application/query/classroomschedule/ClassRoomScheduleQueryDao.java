package pl.edziennik.application.query.classroomschedule;

import org.springframework.data.domain.Pageable;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.classroomschedule.summary.ClassRoomScheduleSummaryView;
import pl.edziennik.common.view.classroomschedule.summaryforschool.ClassRoomScheduleSummaryForSchoolView;

public interface ClassRoomScheduleQueryDao {

    PageView<ClassRoomScheduleSummaryView> getClassRoomScheduleSummaryView(Pageable pageable, ClassRoomId classRoomId);

    PageView<ClassRoomScheduleSummaryForSchoolView> getClassRoomsSchedulesSummaryForSchoolView(Pageable pageable, SchoolId schoolId);

}
