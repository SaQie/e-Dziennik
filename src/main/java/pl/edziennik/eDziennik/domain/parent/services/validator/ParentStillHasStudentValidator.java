package pl.edziennik.eDziennik.domain.parent.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.wrapper.ParentId;
import pl.edziennik.eDziennik.domain.parent.repository.ParentRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.server.basics.validator.AbstractValidator;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

import static pl.edziennik.eDziennik.domain.parent.services.validator.ParentValidators.EXCEPTON_MESSAGE_PARENT_STILL_HAS_STUDENT;
import static pl.edziennik.eDziennik.domain.parent.services.validator.ParentValidators.PARENT_STILL_HAS_STUDENT_VALIDATOR;

@Component
@AllArgsConstructor
class ParentStillHasStudentValidator extends BaseService implements AbstractValidator<ParentId> {

    private final ParentRepository repository;
    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorId() {
        return PARENT_STILL_HAS_STUDENT_VALIDATOR;
    }

    @Override
    public Optional<ApiValidationResult> validate(ParentId parentId) {
        Parent parent = repository.findById(parentId)
                .orElseThrow(notFoundException(parentId.id(), Parent.class));

        if (parent.getStudent() != null) {

            String message = resourceCreator.of(EXCEPTON_MESSAGE_PARENT_STILL_HAS_STUDENT, parent.getStudent().getPersonInformation().fullName());

            ApiValidationResult apiValidationResult = ApiValidationResult.builder()
                    .field(ParentRequestApiDto.ID_STUDENT)
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorId())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiValidationResult);

        }
        return Optional.empty();
    }
}
