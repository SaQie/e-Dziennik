package pl.edziennik.infrastructure.repository.classroomschedule;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.ClassRoomScheduleId;
import pl.edziennik.domain.classroom.ClassRoomSchedule;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryDefinition(idClass = ClassRoomScheduleId.class, domainClass = ClassRoomSchedule.class)
public interface ClassRoomScheduleCommandRepository {

    ClassRoomSchedule save(ClassRoomSchedule classRoomSchedule);

    @Query("SELECT crs FROM ClassRoomSchedule crs " +
            "WHERE crs.classRoom.classRoomId = :classRoomId " +
            "AND crs.timeFrame.endDate BETWEEN :startDate and :endDate ")
    List<ClassRoomSchedule> getClassRoomSchedulesInTimeFrame(LocalDateTime startDate,
                                                       LocalDateTime endDate, ClassRoomId classRoomId);
}
