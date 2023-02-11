package pl.edziennik.eDziennik.server.schoolclass.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.basics.ServiceValidator;
import pl.edziennik.eDziennik.server.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.mapper.SchoolClassMapper;
import pl.edziennik.eDziennik.server.schoolclass.services.validator.SchoolClassValidators;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

@Service
@AllArgsConstructor
class SchoolClassValidatorService extends ServiceValidator<SchoolClassValidators, SchoolClassRequestApiDto> {

    @Override
    protected void valid(SchoolClassRequestApiDto dto) {
        runValidatorChain(dto);

    }
}
