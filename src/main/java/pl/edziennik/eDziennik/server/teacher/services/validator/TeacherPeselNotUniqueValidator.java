package pl.edziennik.eDziennik.server.teacher.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.ApiErrorsDto;
import pl.edziennik.eDziennik.server.basics.ExceptionType;
import pl.edziennik.eDziennik.server.basics.ValidatorPriority;
import pl.edziennik.eDziennik.server.personinformation.dao.PersonInformationDao;
import pl.edziennik.eDziennik.server.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.List;
import java.util.Optional;

/**
 * Validator for already existing teachers pesel numbers
 */
@Component
@AllArgsConstructor
class TeacherPeselNotUniqueValidator implements TeacherValidators{

    private final TeacherDao dao;
    private final ResourceCreator resourceCreator;

    public static final Integer VALIDATOR_ID = 2;

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
    public Optional<ApiErrorsDto> validate(TeacherRequestApiDto dto) {
        if (dao.isTeacherExistByPesel(dto.getPesel())){
            String message = resourceCreator.of(EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE, dto.getPesel());

            ApiErrorsDto apiErrorsDto = ApiErrorsDto.builder()
                    .field(TeacherRequestApiDto.PESEL)
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
