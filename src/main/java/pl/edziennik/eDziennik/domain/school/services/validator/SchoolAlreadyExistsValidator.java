package pl.edziennik.eDziennik.domain.school.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatePurpose;
import pl.edziennik.eDziennik.domain.school.dao.SchoolDao;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that check, school with name already exists
 */
@Component
@AllArgsConstructor
class SchoolAlreadyExistsValidator implements SchoolValidators {

    private final ResourceCreator resourceCreator;
    private final SchoolDao dao;

    @Override
    public String getValidatorId() {
        return SCHOOL_ALREADY_EXISTS_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiErrorDto> validate(SchoolRequestApiDto dto) {
        if (dao.isSchoolExist(dto.getName())) {

            String message = resourceCreator.of(EXCEPTION_MESSAGE_SCHOOL_ALREADY_EXIST, dto.getName());

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(SchoolRequestApiDto.NAME)
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
