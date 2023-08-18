package pl.edziennik.application.query.teacher.summary;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacher.TeacherSummaryView;
import pl.edziennik.infrastructure.repository.teacher.TeacherQueryRepository;

@Component
@AllArgsConstructor
class GetTeacherSummaryQueryHandler implements IQueryHandler<GetTeacherSummaryQuery, PageView<TeacherSummaryView>> {

    private final TeacherQueryRepository teacherQueryRepository;

    @Override
    @Cacheable(value = "teachers", key = "#root.method.name")
    public PageView<TeacherSummaryView> handle(GetTeacherSummaryQuery query) {
        Page<TeacherSummaryView> dtos = teacherQueryRepository.findAllWithPagination(query.pageable());

        return PageView.fromPage(dtos);
    }
}
