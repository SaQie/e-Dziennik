package pl.edziennik.infrastructure.repository.classroom;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.domain.classroom.ClassRoom;

@RepositoryDefinition(domainClass = ClassRoom.class, idClass = ClassRoomId.class)
public interface ClassRoomQueryRepository {
}
