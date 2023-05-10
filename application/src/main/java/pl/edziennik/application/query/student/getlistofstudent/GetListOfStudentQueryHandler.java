package pl.edziennik.application.query.student.getlistofstudent;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.student.StudentDto;
import pl.edziennik.infrastructure.repositories.student.StudentQueryRepository;

@Component
@AllArgsConstructor
class GetListOfStudentQueryHandler implements IQueryHandler<GetListOfStudentQuery, PageDto<StudentDto>> {

    private final StudentQueryRepository studentQueryRepository;

    @Override
    public PageDto<StudentDto> handle(GetListOfStudentQuery query) {
        Page<StudentDto> dtos = studentQueryRepository.findAllWithPagination(query.pageable());
        return PageDto.fromPage(dtos);
    }
}
