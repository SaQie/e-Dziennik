package pl.edziennik.infrastructure.repository.lessonplan;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.LessonPlanId;
import pl.edziennik.common.valueobject.vo.LessonOrder;
import pl.edziennik.domain.lessonplan.LessonPlan;

import java.time.LocalDateTime;
import java.util.Optional;

@RepositoryDefinition(domainClass = LessonPlan.class, idClass = LessonPlanId.class)
public interface LessonPlanCommandRepository {

    LessonPlan save(LessonPlan lessonPlan);

    @Query("SELECT lp.lessonOrder FROM LessonPlan lp " +
            "WHERE lp.timeFrame.startDate >= :startDay " +
            "AND lp.timeFrame.endDate < :endDay " +
            "ORDER BY lp.lessonOrder DESC LIMIT 1")
    Optional<LessonOrder> getLastLessonOrderInDay(LocalDateTime startDay, LocalDateTime endDay);

    LessonPlan getById(LessonPlanId lessonPlanId);
}
