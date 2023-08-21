package pl.edziennik.application.events.event;

import pl.edziennik.common.valueobject.id.LessonPlanId;

public record LessonPlanCreatedEvent(
        LessonPlanId lessonPlanId
) {

    public static final String LESSON_PLAN_ID = "lessonPlanId";

}
