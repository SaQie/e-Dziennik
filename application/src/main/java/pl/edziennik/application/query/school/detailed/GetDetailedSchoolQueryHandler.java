package pl.edziennik.application.query.school.detailed;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.school.DetailedSchoolDto;
import pl.edziennik.infrastructure.repositories.school.SchoolQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;

@Component
@AllArgsConstructor
class GetDetailedSchoolQueryHandler implements IQueryHandler<GetDetailedSchoolQuery, DetailedSchoolDto> {

    private final SchoolQueryRepository schoolQueryRepository;
    private final ResourceCreator res;

    @Override
    public DetailedSchoolDto handle(GetDetailedSchoolQuery query) {
        DetailedSchoolDto dto = schoolQueryRepository.getSchool(query.schoolId());

        if (dto == null) {
            throw new BusinessException(res.notFoundError(GetDetailedSchoolQuery.SCHOOL_ID, query.schoolId()));
        }

        return dto;
    }
}
