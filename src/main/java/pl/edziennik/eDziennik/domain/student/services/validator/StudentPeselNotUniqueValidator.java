package pl.edziennik.eDziennik.domain.student.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.domain.student.dao.StudentDao;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator for already existing students pesel numbers
 */
@Component
@AllArgsConstructor
class StudentPeselNotUniqueValidator implements StudentValidators {

    private final StudentDao dao;
    private final ResourceCreator resourceCreator;


    @Override
    public String getValidatorId() {
        return STUDENT_PESEL_NOT_UNIQUE_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiErrorDto> validate(StudentRequestApiDto dto) {
        if (dao.isStudentExistsByPesel(dto.getPesel())) {
            String message = resourceCreator.of(EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE, dto.getPesel());

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(StudentRequestApiDto.PESEL)
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
