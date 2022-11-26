package pl.edziennik.eDziennik.server.school.domain.dto.mapper;

import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.schoollevel.domain.dto.mapper.SchoolLevelMapper;

public class SchoolMapper{

    private SchoolMapper() {
    }

    public static SchoolResponseApiDto toDto(School entity) {
        return new SchoolResponseApiDto(
                entity.getId(),
                entity.getName(),
                entity.getPostalCode(),
                entity.getCity(),
                entity.getNip(),
                entity.getRegon(),
                entity.getAdress(),
                entity.getPhoneNumber(),
                entity.getSchoolLevel().getId()
        );
    }
    public static School toEntity(SchoolRequestApiDto dto){
        return new School(
                dto.getName(),
                dto.getAddress(),
                dto.getPostalCode(),
                dto.getCity(),
                dto.getNip(),
                dto.getRegon(),
                dto.getPhoneNumber()
        );
    }

}
