package pl.edziennik.eDziennik.domain.parent.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.parent.dao.ParentDao;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.basics.validator.AbstractValidator;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

import static pl.edziennik.eDziennik.domain.parent.services.validator.ParentValidators.EXCEPTON_MESSAGE_PARENT_STILL_HAS_STUDENT;
import static pl.edziennik.eDziennik.domain.parent.services.validator.ParentValidators.PARENT_STILL_HAS_STUDENT_VALIDATOR;

@Component
@AllArgsConstructor
public class ParentStillHasStudentValidator implements AbstractValidator<Long> {

    private final ParentDao dao;
    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorId() {
        return PARENT_STILL_HAS_STUDENT_VALIDATOR;
    }

    @Override
    public Optional<ApiErrorDto> validate(Long id) {
        Parent parent = dao.get(id);
        if (parent.getStudent() != null) {

            String message = resourceCreator.of(EXCEPTON_MESSAGE_PARENT_STILL_HAS_STUDENT, parent.getStudent().getPersonInformation().getFullName());

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(ParentRequestApiDto.ID_STUDENT)
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
