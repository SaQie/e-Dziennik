package pl.edziennik.eDziennik.domain.schoolclass.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.schoolclass.dao.SchoolClassDao;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatePurpose;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that check, the school class already exist
 */
@Component
@AllArgsConstructor
class SchoolClassAlreadyExistsValidator implements SchoolClassValidators {

    private final SchoolClassDao dao;
    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorId() {
        return SCHOOL_CLASS_ALREADY_EXISTS_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiErrorDto> validate(SchoolClassRequestApiDto dto) {
        if (dao.isSchoolClassAlreadyExist(dto.getClassName(), dto.getIdSchool())) {
            String schoolName = dao.get(School.class, dto.getIdSchool()).getName();
            String message = resourceCreator.of(EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST, dto.getClassName(), schoolName);

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(SchoolClassRequestApiDto.CLASS_NAME)
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
