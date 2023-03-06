package pl.edziennik.eDziennik.domain.teacher.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatePurpose;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator for already existing teachers pesel numbers
 */
@Component
@AllArgsConstructor
class TeacherPeselNotUniqueValidator implements TeacherValidators {

    private final TeacherDao dao;
    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorId() {
        return TEACHER_PESEL_NOT_UNIQUE_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiErrorDto> validate(TeacherRequestApiDto dto) {
        if (dao.isTeacherExistsByPesel(dto.getPesel())) {
            String message = resourceCreator.of(EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE, dto.getPesel());

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(TeacherRequestApiDto.PESEL)
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
