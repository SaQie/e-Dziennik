package pl.edziennik.eDziennik.domain.parent.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.repository.ParentRepository;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
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
    public Optional<ApiErrorDto> validate(ParentRequestApiDto dto) {
        if (repository.existsByPesel(dto.getPesel(), Role.RoleConst.ROLE_PARENT.getId())) {

            String message = resourceCreator.of(EXCEPTION_MESSAGE_PARENT_PESEL_ALREADY_EXISTS, dto.getPesel());

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(ParentRequestApiDto.PESEL)
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorId())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiErrorDto);

        }
        return Optional.empty();
    }
}
