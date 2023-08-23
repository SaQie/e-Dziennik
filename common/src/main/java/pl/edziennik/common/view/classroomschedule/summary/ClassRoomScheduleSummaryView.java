package pl.edziennik.common.view.classroomschedule.summary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.edziennik.common.valueobject.id.ClassRoomScheduleId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.TimeFrame;

public record ClassRoomScheduleSummaryView(
        ClassRoomScheduleId classRoomScheduleId,
        Description description,
        @JsonDeserialize(as = TimeFrame.class) TimeFrame timeFrame
) {
}
