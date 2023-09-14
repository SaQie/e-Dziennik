package pl.edziennik.application.query.lessonplan;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.valueobject.base.Identifier;
import pl.edziennik.common.valueobject.id.LessonPlanId;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.lessonplan.DetailedLessonPlanView;
import pl.edziennik.infrastructure.repository.lessonplan.LessonPlanQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Repository
@AllArgsConstructor
public class LessonPlanDaoImpl implements LessonPlanDao {

    private final ResourceCreator res;
    private final LessonPlanQueryRepository lessonPlanQueryRepository;


    @Override
    public DetailedLessonPlanView getDetailedLessonPlanView(LessonPlanId lessonPlanId) {
        DetailedLessonPlanView view = lessonPlanQueryRepository.getDetailedLessonPlanView(lessonPlanId);

        requireNonNull(view, lessonPlanId);

        return view;
    }

    @Override
    public PageView<DetailedLessonPlanView> getDetailedLessonPlanViewForSchoolClass(SchoolClassId schoolClassId, Pageable pageable) {
        Page<DetailedLessonPlanView> view = lessonPlanQueryRepository.getDetailedLessonPlanViewForSchoolClass(schoolClassId, pageable);

        return PageView.fromPage(view);
    }

    private void requireNonNull(Object view, Identifier id) {
        if (view == null) {
            throw new BusinessException(res.notFoundError(id));
        }
    }
}
