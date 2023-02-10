package pl.edziennik.eDziennik.server.school.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.ApiErrorsDto;
import pl.edziennik.eDziennik.server.basics.ExceptionType;
import pl.edziennik.eDziennik.server.basics.ValidatorPriority;
import pl.edziennik.eDziennik.server.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.List;
import java.util.Optional;

/**
 * Validator that check, school with name already exists
 */
@Component
@AllArgsConstructor
class SchoolAlreadyExistsValidator implements SchoolValidators{

    private final ResourceCreator resourceCreator;
    private final SchoolDao dao;

    public static final Integer VALIDATOR_ID = 1;

    @Override
    public String getValidatorName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Integer getValidationNumber() {
        return VALIDATOR_ID;
    }

    @Override
    public ValidatorPriority getValidationPriority() {
        return ValidatorPriority.HIGH;
    }

    @Override
    public Optional<ApiErrorsDto> validate(SchoolRequestApiDto dto) {
        if (dao.isSchoolExist(dto.getName())){

            String message = resourceCreator.of(EXCEPTION_MESSAGE_SCHOOL_ALREADY_EXIST, dto.getName());

            ApiErrorsDto apiErrorsDto = ApiErrorsDto.builder()
                    .field(SchoolRequestApiDto.NAME)
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorName())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiErrorsDto);

        }

        return Optional.empty();
    }
}
