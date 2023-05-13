package pl.edziennik.application.query.teacher.subjects;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.teacher.TeacherSubjectsSummaryDto;
import pl.edziennik.infrastructure.repositories.teacher.TeacherQueryRepository;

@Component
@AllArgsConstructor
class GetTeacherSubjectsSummaryQueryHandler implements IQueryHandler<GetTeacherSubjectsSummaryQuery, PageDto<TeacherSubjectsSummaryDto>> {

    private final TeacherQueryRepository teacherQueryRepository;

    @Override
    public PageDto<TeacherSubjectsSummaryDto> handle(GetTeacherSubjectsSummaryQuery command) {
        Page<TeacherSubjectsSummaryDto> dtos = teacherQueryRepository.getTeacherSubjectsSummaryWithPagination(command.pageable(), command.teacherId());

        return PageDto.fromPage(dtos);
    }
}
