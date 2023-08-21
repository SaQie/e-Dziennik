package pl.edziennik.infrastructure.repository.classroomschedule;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.ClassRoomScheduleId;
import pl.edziennik.domain.classroom.ClassRoomSchedule;

@RepositoryDefinition(idClass = ClassRoomScheduleId.class, domainClass = ClassRoomSchedule.class)
public interface ClassRoomScheduleQueryRepository {
}
