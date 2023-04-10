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
 * Validator that check, school with regon already exists
 */
@Component
@AllArgsConstructor
class SchoolRegonAlreadyExistValidator implements SchoolValidators {

    private final ResourceCreator resourceCreator;
    private final SchoolRepository repository;


    @Override
    public String getValidatorId() {
        return SCHOOL_REGON_ALREADY_EXISTS_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiValidationResult> validate(SchoolRequestApiDto dto) {
        if (repository.existsByRegon(dto.regon())) {

            String message = resourceCreator.of(EXCEPTION_MESSAGE_SCHOOL_WITH_REGON_ALREADY_EXIST, dto.regon());

            ApiValidationResult apiValidationResult = ApiValidationResult.builder()
                    .field(SchoolRequestApiDto.REGON)
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
