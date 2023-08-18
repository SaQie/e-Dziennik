package pl.edziennik.application.query.student.summary;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.student.StudentSummaryView;
import pl.edziennik.infrastructure.repository.student.StudentQueryRepository;

@Component
@AllArgsConstructor
class GetStudentSummaryQueryHandler implements IQueryHandler<GetStudentSummaryQuery, PageView<StudentSummaryView>> {

    private final StudentQueryRepository studentQueryRepository;

    @Override
    @Cacheable(value = "students", key = "#root.method.name")
    public PageView<StudentSummaryView> handle(GetStudentSummaryQuery query) {
        Page<StudentSummaryView> dtos = studentQueryRepository.findAllWithPagination(query.pageable());
        return PageView.fromPage(dtos);
    }
}
