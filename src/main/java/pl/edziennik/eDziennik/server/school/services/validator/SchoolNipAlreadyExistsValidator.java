package pl.edziennik.eDziennik.server.school.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.ApiErrorsDto;
import pl.edziennik.eDziennik.server.basics.ExceptionType;
import pl.edziennik.eDziennik.server.basics.ValidatorPriority;
import pl.edziennik.eDziennik.server.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

@Component
@AllArgsConstructor
public class SchoolNipAlreadyExistsValidator implements SchoolValidators{

    private final ResourceCreator resourceCreator;
    private final SchoolDao dao;

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
    public Optional<ApiErrorsDto> validate(SchoolRequestApiDto dto) {
        if (dao.isSchoolWithNipExist(dto.getNip())){

            String message = resourceCreator.of(EXCEPTION_MESSAGE_SCHOOL_WITH_NIP_ALREADY_EXIST, dto.getNip());

            ApiErrorsDto apiErrorsDto = ApiErrorsDto.builder()
                    .field(SchoolRequestApiDto.NIP)
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
