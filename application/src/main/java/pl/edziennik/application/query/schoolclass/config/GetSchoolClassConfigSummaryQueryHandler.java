package pl.edziennik.application.query.schoolclass.config;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.schoolclass.config.SchoolClassConfigSummaryDto;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassConfigurationQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;


@Component
@AllArgsConstructor
class GetSchoolClassConfigSummaryQueryHandler implements IQueryHandler<GetSchoolClassConfigSummaryQuery, SchoolClassConfigSummaryDto> {

    private final SchoolClassConfigurationQueryRepository schoolClassConfigurationQueryRepository;
    private final ResourceCreator res;

    @Override
    public SchoolClassConfigSummaryDto handle(GetSchoolClassConfigSummaryQuery command) {
        SchoolClassConfigSummaryDto dto = schoolClassConfigurationQueryRepository.getSchoolClassConfigurationSummaryDto(command.schoolClassId());

        if (dto == null) {
            throw new BusinessException(
                    res.notFoundError(GetSchoolClassConfigSummaryQuery.SCHOOL_CLASS_ID, command.schoolClassId())
            );
        }
        return dto;
    }
}
