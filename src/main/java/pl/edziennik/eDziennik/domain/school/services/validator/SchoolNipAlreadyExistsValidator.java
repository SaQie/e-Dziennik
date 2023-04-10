package pl.edziennik.eDziennik.domain.school.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.school.repository.SchoolRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that check, school with nip already exists
 */
@Component
@AllArgsConstructor
class SchoolNipAlreadyExistsValidator implements SchoolValidators {

    private final ResourceCreator resourceCreator;
    private final SchoolRepository repository;

    @Override
    public String getValidatorId() {
        return SCHOOL_NIP_ALREADY_EXISTS_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiValidationResult> validate(SchoolRequestApiDto dto) {
        if (repository.existsByNip(dto.nip())) {

            String message = resourceCreator.of(EXCEPTION_MESSAGE_SCHOOL_WITH_NIP_ALREADY_EXIST, dto.nip());

            ApiValidationResult apiValidationResult = ApiValidationResult.builder()
                    .field(SchoolRequestApiDto.NIP)
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorId())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiValidationResult);

        }
        return Optional.empty();
    }
}
