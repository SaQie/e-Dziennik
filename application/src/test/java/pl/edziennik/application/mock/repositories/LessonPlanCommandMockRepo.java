package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.id.LessonPlanId;
import pl.edziennik.common.valueobject.vo.LessonOrder;
import pl.edziennik.domain.lessonplan.LessonPlan;
import pl.edziennik.infrastructure.repository.lessonplan.LessonPlanCommandRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LessonPlanCommandMockRepo implements LessonPlanCommandRepository {

    private final Map<LessonPlanId, LessonPlan> database;

    public LessonPlanCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public LessonPlan save(LessonPlan lessonPlan) {
        database.put(lessonPlan.lessonPlanId(), lessonPlan);
        return database.get(lessonPlan.lessonPlanId());
    }

    @Override
    public Optional<LessonOrder> getLastLessonOrderInDay(LocalDateTime startDay, LocalDateTime endDay) {
        int i = database.values().stream().mapToInt(x -> x.lessonOrder().value()).min().orElse(1);
        return Optional.of(LessonOrder.of(i));
    }

    @Override
    public LessonPlan getById(LessonPlanId lessonPlanId) {
        return database.get(lessonPlanId);
    }
}
