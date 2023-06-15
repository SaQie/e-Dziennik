package pl.edziennik.application.query.school.config;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.school.config.SchoolConfigSummaryDto;
import pl.edziennik.infrastructure.repository.school.SchoolQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class GetSchoolConfigSummaryQueryHandler implements IQueryHandler<GetSchoolConfigSummaryQuery, SchoolConfigSummaryDto> {

    private final ResourceCreator res;
    private final SchoolQueryRepository schoolQueryRepository;

    @Override
    public SchoolConfigSummaryDto handle(GetSchoolConfigSummaryQuery command) {
        SchoolConfigSummaryDto dto = schoolQueryRepository.getSchoolConfiguration(command.schoolId());

        if (dto == null) {
            throw new BusinessException(
                    res.notFoundError(GetSchoolConfigSummaryQuery.SCHOOL_ID, command.schoolId())
            );
        }

        return dto;
    }
}
