package pl.edziennik.application.query.school.config;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.school.config.SchoolConfigSummaryView;
import pl.edziennik.infrastructure.repository.school.SchoolConfigurationQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class GetSchoolConfigSummaryQueryHandler implements IQueryHandler<GetSchoolConfigSummaryQuery, SchoolConfigSummaryView> {

    private final ResourceCreator res;
    private final SchoolConfigurationQueryRepository schoolConfigurationQueryRepository;

    @Override
    public SchoolConfigSummaryView handle(GetSchoolConfigSummaryQuery command) {
        SchoolConfigSummaryView view = schoolConfigurationQueryRepository.getSchoolConfigurationView(command.schoolId());

        if (view == null) {
            throw new BusinessException(
                    res.notFoundError(GetSchoolConfigSummaryQuery.SCHOOL_ID, command.schoolId())
            );
        }

        return view;
    }
}
