package pl.edziennik.infrastructure.repository.classroom;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;
import pl.edziennik.domain.classroom.ClassRoom;

import java.util.Optional;

@RepositoryDefinition(domainClass = ClassRoom.class, idClass = ClassRoomId.class)
public interface ClassRoomCommandRepository {

    @Query("SELECT CASE WHEN COUNT(cr) > 0 THEN TRUE ELSE FALSE END FROM ClassRoom cr " +
            "WHERE cr.school.schoolId = :schoolId " +
            "AND cr.classRoomName = :classRoomName")
    boolean isClassRoomAlreadyExists(ClassRoomName classRoomName, SchoolId schoolId);


    ClassRoom save(ClassRoom classRoom);

    Optional<ClassRoom> findById(ClassRoomId classRoomId);

    ClassRoom getById(ClassRoomId classRoomId);

}
