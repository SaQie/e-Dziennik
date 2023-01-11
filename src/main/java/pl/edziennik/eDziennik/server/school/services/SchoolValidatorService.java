package pl.edziennik.eDziennik.server.school.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.basics.ServiceValidator;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.server.school.services.validator.SchoolValidators;
import pl.edziennik.eDziennik.server.schoollevel.dao.SchoolLevelDao;

@Service
@AllArgsConstructor
public class SchoolValidatorService extends ServiceValidator<SchoolValidators, SchoolRequestApiDto> {

    @Override
    protected void valid(SchoolRequestApiDto dto) {
        runValidatorChain(dto);
    }
}
