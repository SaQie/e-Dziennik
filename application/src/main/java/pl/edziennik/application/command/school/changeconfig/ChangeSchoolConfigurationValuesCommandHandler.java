package pl.edziennik.application.command.school.changeconfig;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.school.SchoolConfiguration;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class ChangeSchoolConfigurationValuesCommandHandler implements ICommandHandler<ChangeSchoolConfigurationValuesCommand, OperationResult> {

    private final SchoolCommandRepository schoolCommandRepository;
    private final ResourceCreator res;

    @Override
    @Transactional
    public OperationResult handle(ChangeSchoolConfigurationValuesCommand command) {
        School school = schoolCommandRepository.findById(command.schoolId())
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(ChangeSchoolConfigurationValuesCommand.SCHOOL_ID, command.schoolId()))
                );

        SchoolConfiguration schoolConfiguration = school.getSchoolConfiguration();
        schoolConfiguration.changeAverageType(command.averageType());

        schoolCommandRepository.save(school);

        return OperationResult.success();
    }
}
