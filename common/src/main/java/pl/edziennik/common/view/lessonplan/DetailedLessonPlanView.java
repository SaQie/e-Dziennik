package pl.edziennik.common.view.lessonplan;

import pl.edziennik.common.valueobject.id.LessonPlanId;
import pl.edziennik.common.valueobject.vo.*;

public record DetailedLessonPlanView(

        LessonPlanId lessonPlanId,
        LessonOrder lessonOrder,
        ClassRoomName classRoomName,
        TimeFrame timeFrame,
        Name subjectName,
        FullName teacherName,
        Boolean isSubstitute

) {
}
