package pl.edziennik.infrastructure.repository.classroomschedule;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.ClassRoomScheduleId;
import pl.edziennik.common.valueobject.id.LessonPlanId;
import pl.edziennik.domain.classroom.ClassRoomSchedule;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryDefinition(idClass = ClassRoomScheduleId.class, domainClass = ClassRoomSchedule.class)
public interface ClassRoomScheduleCommandRepository {

    ClassRoomSchedule getReferenceById(ClassRoomScheduleId classRoomScheduleId);
    ClassRoomSchedule save(ClassRoomSchedule classRoomSchedule);

    @Query("SELECT crs FROM ClassRoomSchedule crs " +
            "WHERE crs.classRoom.classRoomId = :classRoomId " +
            "AND crs.timeFrame.endDate BETWEEN :startDate and :endDate ")
    List<ClassRoomSchedule> getClassRoomSchedulesInTimeFrame(LocalDateTime startDate,
                                                       LocalDateTime endDate, ClassRoomId classRoomId);

    @Query("SELECT crs.classRoomScheduleId FROM ClassRoomSchedule crs " +
            "WHERE crs.lessonPlan.lessonPlanId IN (:lessonPlanIds)")
    List<ClassRoomScheduleId> getClassRoomSchedulesByLessonPlans(List<LessonPlanId> lessonPlanIds);

    void deleteById(ClassRoomScheduleId classRoomScheduleId);
}
