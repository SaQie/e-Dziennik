package pl.edziennik.eDziennik.domain.schoolclass.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.schoolclass.services.validator.SchoolClassValidators;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;

@Service
@AllArgsConstructor
class SchoolClassValidatorService extends ServiceValidator<SchoolClassValidators, SchoolClassRequestApiDto> {


    protected void valid(SchoolClassRequestApiDto dto) {
        runValidators(dto);
    }
}
