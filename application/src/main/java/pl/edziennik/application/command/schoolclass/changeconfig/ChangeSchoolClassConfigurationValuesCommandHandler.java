package pl.edziennik.application.command.schoolclass.changeconfig;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.common.valueobject.id.SchoolClassConfigurationId;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.schoolclass.SchoolClassConfiguration;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassConfigurationCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class ChangeSchoolClassConfigurationValuesCommandHandler implements ICommandHandler<ChangeSchoolClassConfigurationValuesCommand, OperationResult> {

    private final ResourceCreator res;
    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final SchoolClassConfigurationCommandRepository schoolClassConfigurationCommandRepository;

    @Override
    public OperationResult handle(ChangeSchoolClassConfigurationValuesCommand command) {
        SchoolClass schoolClass = schoolClassCommandRepository.findById(command.schoolClassId())
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(ChangeSchoolClassConfigurationValuesCommand.SCHOOL_CLASS_ID, command.schoolClassId())
                ));

        SchoolClassConfigurationId schoolClassConfigurationId = schoolClass.schoolClassConfiguration().schoolClassConfigurationId();

        SchoolClassConfiguration schoolClassConfiguration = schoolClassConfigurationCommandRepository.getSchoolClassConfigurationBySchoolClassConfigurationId(schoolClassConfigurationId);
        schoolClassConfiguration.changeAutoAssignSubject(command.autoAssignSubjects());
        schoolClassConfiguration.changeMaxStudentsSize(command.maxStudentsSize());

        schoolClassConfigurationCommandRepository.save(schoolClassConfiguration);

        return OperationResult.success();
    }
}
