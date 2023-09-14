package pl.edziennik.infrastructure.repository.lessonplan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.LessonPlanId;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.view.lessonplan.DetailedLessonPlanView;
import pl.edziennik.domain.lessonplan.LessonPlan;

@RepositoryDefinition(domainClass = LessonPlan.class, idClass = LessonPlanId.class)
public interface LessonPlanQueryRepository {

    @Query("SELECT new pl.edziennik.common.view.lessonplan.DetailedLessonPlanView(lp.lessonPlanId,lp.lessonOrder,cr.classRoomName,lp.timeFrame,s.name,t.personInformation.fullName,lp.isSubstitute) " +
            "FROM LessonPlan lp " +
            "JOIN lp.classRoom cr " +
            "JOIN lp.subject s " +
            "JOIN lp.teacher t " +
            "WHERE lp.lessonPlanId = :lessonPlanId ")
    DetailedLessonPlanView getDetailedLessonPlanView(LessonPlanId lessonPlanId);

    @Query("SELECT new pl.edziennik.common.view.lessonplan.DetailedLessonPlanView(lp.lessonPlanId,lp.lessonOrder,cr.classRoomName,lp.timeFrame,s.name,t.personInformation.fullName,lp.isSubstitute) " +
            "FROM LessonPlan lp " +
            "JOIN lp.classRoom cr " +
            "JOIN lp.subject s " +
            "JOIN lp.teacher t " +
            "WHERE lp.schoolClass.schoolClassId = :schoolClassId " +
            "ORDER BY lp.lessonOrder")
    Page<DetailedLessonPlanView> getDetailedLessonPlanViewForSchoolClass(SchoolClassId schoolClassId, Pageable pageable);

}
