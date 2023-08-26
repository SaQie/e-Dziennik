package pl.edziennik.infrastructure.repository.classroomschedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.ClassRoomScheduleId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.classroomschedule.summary.ClassRoomScheduleSummaryView;
import pl.edziennik.common.view.classroomschedule.summaryforschool.ClassRoomScheduleSummaryForSchoolView;
import pl.edziennik.domain.classroom.ClassRoomSchedule;

import java.util.List;

@RepositoryDefinition(idClass = ClassRoomScheduleId.class, domainClass = ClassRoomSchedule.class)
public interface ClassRoomScheduleQueryRepository {

    @Query("SELECT NEW pl.edziennik.common.view.classroomschedule.summary.ClassRoomScheduleSummaryView" +
            "(crs.classRoomScheduleId,crs.description,crs.timeFrame) " +
            "FROM ClassRoom cr " +
            "LEFT JOIN ClassRoomSchedule crs ON (crs.classRoom.classRoomId = cr.classRoomId) " +
            "WHERE cr.classRoomId = :classRoomId " +
            "ORDER BY crs.timeFrame.startDate ")
    Page<ClassRoomScheduleSummaryView> getClassRoomScheduleView(Pageable pageable, ClassRoomId classRoomId);

    @Query("SELECT NEW pl.edziennik.common.view.classroomschedule.summaryforschool.ClassRoomScheduleSummaryForSchoolView" +
            "(cr.classRoomId,cr.classRoomName) " +
            "FROM ClassRoom cr " +
            "WHERE cr.school.schoolId = :schoolId")
    Page<ClassRoomScheduleSummaryForSchoolView> getClassRoomScheduleForSchoolView(Pageable pageable, SchoolId schoolId);

    @Query("SELECT NEW pl.edziennik.common.view.classroomschedule.summary.ClassRoomScheduleSummaryView" +
            "(crs.classRoomScheduleId,crs.description,crs.timeFrame) " +
            "FROM ClassRoomSchedule crs " +
            "JOIN crs.classRoom cr " +
            "WHERE cr.classRoomId = :classRoomId " +
            "ORDER BY crs.timeFrame.startDate ")
    List<ClassRoomScheduleSummaryView> getClassRoomScheduleView(ClassRoomId classRoomId);

}
