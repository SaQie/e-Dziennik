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

    private final SchoolLevelDao dao;

    protected School validateDtoAndMapToEntity(SchoolRequestApiDto dto){
        super.validate(dto);
        School school = SchoolMapper.toEntity(dto);
        dao.findWithExecute(dto.getIdSchoolLevel(), school::setSchoolLevel);
        return school;
    }

}
