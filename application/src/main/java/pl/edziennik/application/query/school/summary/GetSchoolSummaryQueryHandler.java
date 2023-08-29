package pl.edziennik.application.query.school.summary;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.school.SchoolSummaryView;
import pl.edziennik.infrastructure.repository.school.SchoolQueryRepository;

@Component
@AllArgsConstructor
class GetSchoolSummaryQueryHandler implements IQueryHandler<GetSchoolSummaryQuery, PageView<SchoolSummaryView>> {

    private final SchoolQueryRepository schoolQueryRepository;

    @Override
    @Cacheable(value = "schools", key = "#root.method.name")
    public PageView<SchoolSummaryView> handle(GetSchoolSummaryQuery command) {
        Page<SchoolSummaryView> dtos = schoolQueryRepository.findAllWithPagination(command.pageable());

        return PageView.fromPage(dtos);
    }
}
