package pl.edziennik.eDziennik.domain.school.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatePurpose;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that check, school with nip already exists
 */
@Component
@AllArgsConstructor
class SchoolNipAlreadyExistsValidator implements SchoolValidators {

    private final ResourceCreator resourceCreator;
    private final SchoolDao dao;

    @Override
    public String getValidatorId() {
        return SCHOOL_NIP_ALREADY_EXISTS_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiErrorDto> validate(SchoolRequestApiDto dto) {
        if (dao.isSchoolWithNipExist(dto.getNip())) {

            String message = resourceCreator.of(EXCEPTION_MESSAGE_SCHOOL_WITH_NIP_ALREADY_EXIST, dto.getNip());

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(SchoolRequestApiDto.NIP)
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
