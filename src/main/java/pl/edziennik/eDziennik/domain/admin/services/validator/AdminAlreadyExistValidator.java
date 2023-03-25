package pl.edziennik.eDziennik.domain.admin.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.repository.AdminRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

@Component
@AllArgsConstructor
class AdminAlreadyExistValidator implements AdminValidators {

    private final ResourceCreator resourceCreator;
    private final AdminRepository repository;

    @Override
    public String getValidatorId() {
        return ADMIN_ALREADY_EXISTS_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiValidationResult> validate(AdminRequestApiDto dto) {
        if (!repository.findAll().isEmpty()) {

            String message = resourceCreator.of(EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST);

            ApiValidationResult apiValidationResult = ApiValidationResult.builder()
                    .cause(message)
                    .thrownImmediately(true)
                    .errorThrownedBy(getValidatorId())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiValidationResult);
        }
        return Optional.empty();
    }
}
