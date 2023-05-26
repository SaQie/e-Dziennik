package pl.edziennik.application.query.schoolclass.config;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.schoolclass.config.SchoolClassConfigSummaryDto;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassConfigurationQueryRepository;

@Component
@AllArgsConstructor
class GetSchoolClassConfigSummaryQueryHandler implements IQueryHandler<GetSchoolClassConfigSummaryQuery, SchoolClassConfigSummaryDto> {

    private final SchoolClassConfigurationQueryRepository schoolClassConfigurationQueryRepository;

    @Override
    public SchoolClassConfigSummaryDto handle(GetSchoolClassConfigSummaryQuery command) {
        return null;
    }
}
