package pl.edziennik.common.view.teacherschedule.summary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.TimeFrame;

public record TeacherScheduleSummaryView(
        TeacherScheduleId teacherScheduleId,
        Description description,
        @JsonDeserialize(as = TimeFrame.class) TimeFrame timeFrame
) {
}
