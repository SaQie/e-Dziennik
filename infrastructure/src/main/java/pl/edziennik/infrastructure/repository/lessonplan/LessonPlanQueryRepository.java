package pl.edziennik.infrastructure.repository.lessonplan;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.LessonPlanId;
import pl.edziennik.domain.lessonplan.LessonPlan;

@RepositoryDefinition(domainClass = LessonPlan.class, idClass = LessonPlanId.class)
public interface LessonPlanQueryRepository {
}
