package pl.edziennik.eDziennik.domain.school.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.basic.validator.ServiceValidator;

@Service
@AllArgsConstructor
class SchoolValidatorService extends ServiceValidator<SchoolRequestApiDto> {


    protected void valid(SchoolRequestApiDto dto) {
        validate(dto);
    }
}
