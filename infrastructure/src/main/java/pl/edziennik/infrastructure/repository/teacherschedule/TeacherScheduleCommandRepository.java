package pl.edziennik.infrastructure.repository.teacherschedule;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.LessonPlanId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
import pl.edziennik.domain.teacher.TeacherSchedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = TeacherSchedule.class, idClass = TeacherScheduleId.class)
public interface TeacherScheduleCommandRepository {

    @Query("SELECT ts FROM TeacherSchedule ts where ts.teacherScheduleId = :teacherScheduleId ")
    TeacherSchedule getReferenceById(TeacherScheduleId teacherScheduleId);

    TeacherSchedule save(TeacherSchedule teacherSchedule);

    @Query("SELECT ts FROM TeacherSchedule ts " +
            "WHERE ts.teacher.teacherId = :teacherId " +
            "AND ts.timeFrame.endDate BETWEEN :startDate and :endDate ")
    List<TeacherSchedule> getTeacherSchedulesInTimeFrame(LocalDateTime startDate, LocalDateTime endDate,
                                                         TeacherId teacherId);

    @Query("SELECT ts.teacherScheduleId FROM TeacherSchedule ts " +
            "WHERE ts.lessonPlan.lessonPlanId IN (:lessonPlanIds)")
    List<TeacherScheduleId> getTeacherSchedulesByLessonPlans(List<LessonPlanId> lessonPlanIds);

    void deleteById(TeacherScheduleId teacherScheduleId);

    Optional<TeacherSchedule> findById(TeacherScheduleId teacherScheduleId);
}
