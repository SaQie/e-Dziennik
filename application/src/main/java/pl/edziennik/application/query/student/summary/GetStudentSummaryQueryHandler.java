package pl.edziennik.application.query.student.summary;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.student.StudentSummaryDto;
import pl.edziennik.infrastructure.repository.student.StudentQueryRepository;

@Component
@AllArgsConstructor
class GetStudentSummaryQueryHandler implements IQueryHandler<GetStudentSummaryQuery, PageDto<StudentSummaryDto>> {

    private final StudentQueryRepository studentQueryRepository;

    @Override
    @Cacheable(value = "students", key = "#root.method.name")
    public PageDto<StudentSummaryDto> handle(GetStudentSummaryQuery query) {
        Page<StudentSummaryDto> dtos = studentQueryRepository.findAllWithPagination(query.pageable());
        return PageDto.fromPage(dtos);
    }
}
