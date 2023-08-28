package pl.edziennik.infrastructure.repository.classroom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.classroom.classroomsforschool.ClassRoomForSchoolView;
import pl.edziennik.domain.classroom.ClassRoom;

@RepositoryDefinition(domainClass = ClassRoom.class, idClass = ClassRoomId.class)
public interface ClassRoomQueryRepository {

    @Query("SELECT NEW pl.edziennik.common.view.classroom.classroomsforschool.ClassRoomForSchoolView(cr.classRoomId, cr.classRoomName) " +
            "FROM ClassRoom cr " +
            "WHERE cr.school.schoolId = :schoolId ")
    Page<ClassRoomForSchoolView> getClassRoomForSchoolView(Pageable pageable, SchoolId schoolId);

}
