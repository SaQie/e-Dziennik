package pl.edziennik.web.query.lessonplan;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Lesson-plan API")
public class LessonPlanQueryController {

    private final LessonPlanDao dao;

    @Operation(summary = "Get detailed information about given lesson-plan",
            description = "This API endpoint returns a detailed information about given lessonPlanId")
    @GetMapping("/school-classes/lesson-plans/{lessonPlanId}")
    public DetailedLessonPlanView getDetailedLessonPlan(@PathVariable LessonPlanId lessonPlanId) {
        return dao.getDetailedLessonPlanView(lessonPlanId);
    }

    @Operation(summary = "Get given school-class lesson-plans",
            description = "This API endpoint returns a paginated school-class lesson-plans data")
    @GetMapping("/school-classes/{schoolClassId}/lesson-plans")
    public PageView<DetailedLessonPlanView> getLessonPlanForSchoolClass(@PathVariable SchoolClassId schoolClassId, Pageable pageable) {
        return dao.getDetailedLessonPlanViewForSchoolClass(schoolClassId, pageable);
    }

}
