package pl.edziennik.eDziennik.domain.parent.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.repository.ParentRepository;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.Pesel;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that checks parent with given pesel already exists
 */
@Component
@AllArgsConstructor
class ParentPeselNotUniqueValidator implements ParentValidators {

    private final ParentRepository repository;
    private final ResourceCreator resourceCreator;


    @Override
    public String getValidatorId() {
        return PARENT_PESEL_NOT_UNIQUE_VALIDATOR;
    }

    @Override
    public Optional<ApiValidationResult> validate(ParentRequestApiDto dto) {
        if (repository.existsByPesel(Pesel.of(dto.pesel()), Role.RoleConst.ROLE_PARENT.getId())) {

            String message = resourceCreator.of(EXCEPTION_MESSAGE_PARENT_PESEL_ALREADY_EXISTS, dto.pesel());

            ApiValidationResult apiValidationResult = ApiValidationResult.builder()
                    .field(ParentRequestApiDto.PESEL)
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
