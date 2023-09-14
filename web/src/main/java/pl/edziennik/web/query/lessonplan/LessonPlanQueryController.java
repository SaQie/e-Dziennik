package pl.edziennik.web.query.lessonplan;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edziennik.application.query.lessonplan.LessonPlanDao;
import pl.edziennik.common.valueobject.id.LessonPlanId;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.lessonplan.DetailedLessonPlanView;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class LessonPlanQueryController {

    private final LessonPlanDao dao;

    @GetMapping("/school-classes/lesson-plans/{lessonPlanId}")
    public DetailedLessonPlanView getDetailedLessonPlan(@PathVariable LessonPlanId lessonPlanId) {
        return dao.getDetailedLessonPlanView(lessonPlanId);
    }

    @GetMapping("/school-classes/{schoolClassId}/lesson-plans")
    public PageView<DetailedLessonPlanView> getLessonPlanForSchoolClass(@PathVariable SchoolClassId schoolClassId, Pageable pageable) {
        return dao.getDetailedLessonPlanViewForSchoolClass(schoolClassId, pageable);
    }

}
