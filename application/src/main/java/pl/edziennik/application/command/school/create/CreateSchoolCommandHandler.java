package pl.edziennik.application.command.school.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoollevel.SchoolLevel;
import pl.edziennik.infrastructure.repositories.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repositories.schoollevel.SchoolLevelCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;

@Component
@AllArgsConstructor
class CreateSchoolCommandHandler implements ICommandHandler<CreateSchoolCommand, OperationResult> {

    private final SchoolLevelCommandRepository schoolLevelCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;
    private final ResourceCreator res;

    @Override
    public OperationResult handle(CreateSchoolCommand command) {
        SchoolLevel schoolLevel = schoolLevelCommandRepository.findById(command.schoolLevelId())
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(CreateSchoolCommand.SCHOOL_LEVEL_ID, command.schoolLevelId())
                ));

        Address address = Address.of(command.address(), command.city(), command.postalCode());
        School school = School.of(command.name(), command.nip(), command.regon(), command.phoneNumber(), address, schoolLevel);

        SchoolId schoolId = schoolCommandRepository.save(school).getSchoolId();

        return OperationResult.success(schoolId);
    }
}
