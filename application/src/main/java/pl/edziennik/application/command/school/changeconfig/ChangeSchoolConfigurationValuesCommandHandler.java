package pl.edziennik.application.command.school.changeconfig;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.ICommandHandler;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.school.SchoolConfiguration;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.school.SchoolConfigurationCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class ChangeSchoolConfigurationValuesCommandHandler implements ICommandHandler<ChangeSchoolConfigurationValuesCommand, OperationResult> {

    private final SchoolCommandRepository schoolCommandRepository;
    private final SchoolConfigurationCommandRepository schoolConfigurationCommandRepository;
    private final ResourceCreator res;

    @Override
    @Transactional
    public OperationResult handle(ChangeSchoolConfigurationValuesCommand command) {
        School school = schoolCommandRepository.findById(command.schoolId())
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(ChangeSchoolConfigurationValuesCommand.SCHOOL_ID, command.schoolId()))
                );

        SchoolConfiguration schoolConfiguration = school.schoolConfiguration();
        schoolConfiguration.changeAverageType(command.averageType());
        schoolConfiguration.changeMaxLessonTime(command.maxLessonTime());
        schoolConfiguration.changeMinScheduleTime(command.minScheduleTime());

        schoolConfigurationCommandRepository.save(schoolConfiguration);

        return OperationResult.success();
    }
}
