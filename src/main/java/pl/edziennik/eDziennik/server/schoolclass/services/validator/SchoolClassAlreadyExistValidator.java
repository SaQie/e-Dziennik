package pl.edziennik.eDziennik.server.schoolclass.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.ApiErrorsDto;
import pl.edziennik.eDziennik.server.basics.ExceptionType;
import pl.edziennik.eDziennik.server.basics.ValidatorPriority;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.schoolclass.dao.SchoolClassDao;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
class SchoolClassAlreadyExistValidator implements SchoolClassValidators {

    private final SchoolClassDao dao;
    private final ResourceCreator resourceCreator;

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
    public Optional<ApiErrorsDto> validate(SchoolClassRequestApiDto dto) {
        if (dao.isSchoolClassAlreadyExist(dto.getClassName(), dto.getIdSchool())){
            String schoolName = dao.get(School.class, dto.getIdSchool()).getName();
            String message = resourceCreator.of(EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST, dto.getClassName(), schoolName);

            ApiErrorsDto apiErrorsDto = ApiErrorsDto.builder()
                    .fields(List.of(SchoolClassRequestApiDto.CLASS_NAME))
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
