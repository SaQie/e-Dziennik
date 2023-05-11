package pl.edziennik.application.query.student.studentsummary;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.student.StudentSummaryDto;
import pl.edziennik.infrastructure.repositories.student.StudentQueryRepository;

@Component
@AllArgsConstructor
class GetStudentSummaryQueryHandler implements IQueryHandler<GetStudentSummaryQuery, PageDto<StudentSummaryDto>> {

    private final StudentQueryRepository studentQueryRepository;

    @Override
    public PageDto<StudentSummaryDto> handle(GetStudentSummaryQuery query) {
        Page<StudentSummaryDto> dtos = studentQueryRepository.findAllWithPagination(query.pageable());
        return PageDto.fromPage(dtos);
    }
}
