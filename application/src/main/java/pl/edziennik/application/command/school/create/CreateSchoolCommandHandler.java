package pl.edziennik.application.command.school.create;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ICommandHandler;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.properties.SchoolConfigurationProperties;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoollevel.SchoolLevel;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.schoollevel.SchoolLevelCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class CreateSchoolCommandHandler implements ICommandHandler<CreateSchoolCommand, OperationResult> {

    private final SchoolLevelCommandRepository schoolLevelCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;
    private final SchoolConfigurationProperties configurationProperties;
    private final ResourceCreator res;

    @Override
    @CacheEvict(allEntries = true, value = "schools")
    public OperationResult handle(CreateSchoolCommand command) {
        SchoolLevel schoolLevel = schoolLevelCommandRepository.findById(command.schoolLevelId())
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(CreateSchoolCommand.SCHOOL_LEVEL_ID, command.schoolLevelId())
                ));

        Address address = createAddress(command);

        School school = School.builder()
                .name(command.name())
                .nip(command.nip())
                .regon(command.regon())
                .phoneNumber(command.phoneNumber())
                .address(address)
                .schoolLevel(schoolLevel)
                .properties(configurationProperties)
                .build();

        SchoolId schoolId = schoolCommandRepository.save(school).schoolId();

        return OperationResult.success(schoolId);
    }

    private Address createAddress(CreateSchoolCommand command) {
        return Address.builder()
                .address(command.address())
                .city(command.city())
                .postalCode(command.postalCode())
                .build();
    }

}
