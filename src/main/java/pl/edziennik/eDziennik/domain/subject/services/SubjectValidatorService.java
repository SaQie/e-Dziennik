package pl.edziennik.eDziennik.domain.subject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.basic.validator.ServiceValidator;

@Service
@AllArgsConstructor
class SubjectValidatorService extends ServiceValidator<SubjectRequestApiDto> {

    protected void valid(final SubjectRequestApiDto dto) {
        runValidators(dto);

    }
}
