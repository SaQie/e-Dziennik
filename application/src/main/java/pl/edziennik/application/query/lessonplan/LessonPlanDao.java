package pl.edziennik.application.query.lessonplan;

import org.springframework.data.domain.Pageable;
import pl.edziennik.common.valueobject.id.LessonPlanId;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.lessonplan.DetailedLessonPlanView;

public interface LessonPlanDao {

    DetailedLessonPlanView getDetailedLessonPlanView(LessonPlanId lessonPlanId);

    PageView<DetailedLessonPlanView> getDetailedLessonPlanViewForSchoolClass(SchoolClassId schoolClassId, Pageable pageable);

}
