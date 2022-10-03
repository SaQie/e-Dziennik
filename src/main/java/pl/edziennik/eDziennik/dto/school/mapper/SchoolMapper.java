package pl.edziennik.eDziennik.dto.school.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.dto.school.SchoolRequestDto;
import pl.edziennik.eDziennik.dto.school.SchoolResponseApiDto;
import pl.edziennik.eDziennik.dto.schoollevel.mapper.SchoolLevelMapper;
import pl.edziennik.eDziennik.server.school.School;
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

    public School toEntity(SchoolRequestDto dto){
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
