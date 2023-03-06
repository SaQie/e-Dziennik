package pl.edziennik.eDziennik.domain.parent.services;

import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.services.validator.ParentValidators;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;

@Service
class ParentValidatorService extends ServiceValidator<ParentValidators, ParentRequestApiDto> {

    protected void valid(ParentRequestApiDto dto) {
        runValidators(dto);
    }
}
