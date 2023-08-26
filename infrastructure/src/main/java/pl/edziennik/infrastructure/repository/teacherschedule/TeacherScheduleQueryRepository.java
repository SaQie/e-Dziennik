package pl.edziennik.infrastructure.repository.teacherschedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
import pl.edziennik.common.view.teacherschedule.summary.TeacherScheduleSummaryView;
import pl.edziennik.common.view.teacherschedule.summaryforschool.TeacherScheduleSummaryForSchoolView;
import pl.edziennik.domain.teacher.TeacherSchedule;

import java.util.List;

@RepositoryDefinition(domainClass = TeacherSchedule.class, idClass = TeacherScheduleId.class)
public interface TeacherScheduleQueryRepository {

    @Query("SELECT NEW pl.edziennik.common.view.teacherschedule.summary.TeacherScheduleSummaryView(ts.teacherScheduleId, ts.description, ts.timeFrame) " +
            "FROM Teacher t " +
            "LEFT JOIN TeacherSchedule ts ON (ts.teacher.teacherId = t.teacherId) " +
            "WHERE t.teacherId = :teacherId " +
            "ORDER BY ts.timeFrame.startDate")
    Page<TeacherScheduleSummaryView> getTeacherScheduleView(Pageable pageable, TeacherId teacherId);


    @Query("SELECT NEW pl.edziennik.common.view.teacherschedule.summaryforschool.TeacherScheduleSummaryForSchoolView(t.teacherId, t.personInformation.fullName) " +
            "FROM Teacher t " +
            "WHERE t.school.schoolId = :schoolId")
    Page<TeacherScheduleSummaryForSchoolView> getTeachersSchedulesForSchoolView(Pageable pageable, SchoolId schoolId);

    @Query("SELECT NEW pl.edziennik.common.view.teacherschedule.summary.TeacherScheduleSummaryView(ts.teacherScheduleId, ts.description, ts.timeFrame) " +
            "FROM TeacherSchedule ts " +
            "JOIN ts.teacher t " +
            "WHERE t.teacherId = :teacherId " +
            "ORDER BY ts.timeFrame.startDate")
    List<TeacherScheduleSummaryView> getTeacherSchedulesView(TeacherId teacherId);
}
