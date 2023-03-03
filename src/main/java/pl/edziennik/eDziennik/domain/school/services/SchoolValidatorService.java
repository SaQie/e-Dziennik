package pl.edziennik.eDziennik.domain.school.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.school.services.validator.SchoolValidators;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;

@Service
@AllArgsConstructor
class SchoolValidatorService extends ServiceValidator<SchoolValidators, SchoolRequestApiDto> {

    @Override
    protected void valid(SchoolRequestApiDto dto) {
        runValidatorChain(dto);
    }
}