package pl.edziennik.application.query.schoolclass.config;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.schoolclass.config.SchoolClassConfigSummaryView;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassConfigurationQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;


@Component
@AllArgsConstructor
class GetSchoolClassConfigSummaryQueryHandler implements IQueryHandler<GetSchoolClassConfigSummaryQuery, SchoolClassConfigSummaryView> {

    private final SchoolClassConfigurationQueryRepository schoolClassConfigurationQueryRepository;
    private final ResourceCreator res;

    @Override
    public SchoolClassConfigSummaryView handle(GetSchoolClassConfigSummaryQuery command) {
        SchoolClassConfigSummaryView view = schoolClassConfigurationQueryRepository.getSchoolClassConfigurationSummaryView(command.schoolClassId());

        if (view == null) {
            throw new BusinessException(
                    res.notFoundError(GetSchoolClassConfigSummaryQuery.SCHOOL_CLASS_ID, command.schoolClassId())
            );
        }
        return view;
    }
}
