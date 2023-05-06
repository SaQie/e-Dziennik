package pl.edziennik.eDziennik.domain.schoolclass.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.basic.validator.ServiceValidator;

@Service
@AllArgsConstructor
class SchoolClassValidatorService extends ServiceValidator<SchoolClassRequestApiDto> {


    protected void valid(final SchoolClassRequestApiDto dto) {
        validate(dto);
    }


}
