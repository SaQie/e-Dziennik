package pl.edziennik.eDziennik.domain.parent.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.wrapper.ParentId;
import pl.edziennik.eDziennik.domain.parent.services.validator.ParentValidators;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;

@Service
@AllArgsConstructor
class ParentValidatorService extends ServiceValidator<ParentValidators, ParentRequestApiDto> {

    protected void valid(ParentRequestApiDto dto) {
        runValidators(dto);
    }


    protected void checkParentHasStudent(ParentId parentId) {
        runSelectedValidator(parentId.id(), ParentValidators.PARENT_STILL_HAS_STUDENT_VALIDATOR);
    }
}
