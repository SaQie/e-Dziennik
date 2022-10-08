package pl.edziennik.eDziennik.server.school.domain.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.schoollevel.domain.dto.mapper.SchoolLevelMapper;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.basics.AbstractMapper;

@Component
@AllArgsConstructor
public class SchoolMapper implements AbstractMapper<SchoolResponseApiDto, School> {

    private final SchoolLevelMapper schoolLevelMapper;

    @Override
    public SchoolResponseApiDto toDto(School entity) {
        return new SchoolResponseApiDto(
                entity.getId(),
                entity.getName(),
                entity.getPostalCode(),
                entity.getCity(),
                entity.getNip(),
                entity.getRegon(),
                entity.getPhoneNumber(),
                schoolLevelMapper.toDto(entity.getSchoolLevel())
        );
    }

    @Override
    public School toEntity(SchoolResponseApiDto dto) {
        return null;
    }

    public School toEntity(SchoolRequestApiDto dto){
        return new School(
                dto.getName(),
                dto.getAdress(),
                dto.getPostalCode(),
                dto.getCity(),
                dto.getNip(),
                dto.getRegon(),
                dto.getPhoneNumber()
        );
    }

}
