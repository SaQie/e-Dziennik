package pl.edziennik.application.command.school.create;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.common.properties.SchoolConfigurationProperties;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoollevel.SchoolLevel;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.schoollevel.SchoolLevelCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class CreateSchoolCommandHandler implements CommandHandler<CreateSchoolCommand> {

    private final SchoolLevelCommandRepository schoolLevelCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;
    private final SchoolConfigurationProperties configurationProperties;
    private final ResourceCreator res;

    @Override
    @CacheEvict(allEntries = true, value = "schools")
    public void handle(CreateSchoolCommand command) {
        SchoolLevel schoolLevel = schoolLevelCommandRepository.findById(command.schoolLevelId())
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(CreateSchoolCommand.SCHOOL_LEVEL_ID, command.schoolLevelId())
                ));

        Address address = createAddress(command);

        School school = School.builder()
                .schoolId(command.schoolId())
                .name(command.name())
                .nip(command.nip())
                .regon(command.regon())
                .phoneNumber(command.phoneNumber())
                .address(address)
                .schoolLevel(schoolLevel)
                .properties(configurationProperties)
                .build();

        schoolCommandRepository.save(school);
    }

    private Address createAddress(CreateSchoolCommand command) {
        return Address.builder()
                .address(command.address())
                .city(command.city())
                .postalCode(command.postalCode())
                .build();
    }

}
