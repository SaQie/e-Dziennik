package pl.edziennik.application.query.school.detailed;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.school.DetailedSchoolView;
import pl.edziennik.infrastructure.repository.school.SchoolQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class GetDetailedSchoolQueryHandler implements IQueryHandler<GetDetailedSchoolQuery, DetailedSchoolView> {

    private final SchoolQueryRepository schoolQueryRepository;
    private final ResourceCreator res;

    @Override
    public DetailedSchoolView handle(GetDetailedSchoolQuery query) {
        DetailedSchoolView dto = schoolQueryRepository.getSchoolView(query.schoolId());

        if (dto == null) {
            throw new BusinessException(res.notFoundError(GetDetailedSchoolQuery.SCHOOL_ID, query.schoolId()));
        }

        return dto;
    }
}
