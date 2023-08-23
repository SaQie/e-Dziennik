package pl.edziennik.common.view.classroomschedule.summaryforschool;

import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;
import pl.edziennik.common.view.classroomschedule.summary.ClassRoomScheduleSummaryView;

import java.util.Collections;
import java.util.List;

public record ClassRoomScheduleSummaryForSchoolView(
        ClassRoomId classRoomId,
        ClassRoomName classRoomName,
        List<ClassRoomScheduleSummaryView> schedules
) {

    public ClassRoomScheduleSummaryForSchoolView(ClassRoomId classRoomId, ClassRoomName classRoomName) {
        this(classRoomId, classRoomName, Collections.emptyList());
    }

    public ClassRoomScheduleSummaryForSchoolView(ClassRoomScheduleSummaryForSchoolView view, List<ClassRoomScheduleSummaryView> schedules) {
        this(view.classRoomId, view.classRoomName, schedules);
    }
}
