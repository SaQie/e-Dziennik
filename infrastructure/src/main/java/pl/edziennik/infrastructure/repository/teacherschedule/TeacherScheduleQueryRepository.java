package pl.edziennik.infrastructure.repository.teacherschedule;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
import pl.edziennik.domain.teacher.TeacherSchedule;

@RepositoryDefinition(domainClass = TeacherSchedule.class, idClass = TeacherScheduleId.class)
public interface TeacherScheduleQueryRepository {
}
