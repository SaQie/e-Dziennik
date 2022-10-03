package pl.edziennik.eDziennik.dto.schoollevel.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.dto.schoollevel.SchoolLevelRequestDto;
import pl.edziennik.eDziennik.dto.schoollevel.SchoolLevelResponseApiDto;
import pl.edziennik.eDziennik.server.schoollevel.SchoolLevel;
import pl.edziennik.eDziennik.server.basics.AbstractMapper;

@Component
@AllArgsConstructor
public class SchoolLevelMapper implements AbstractMapper<SchoolLevelResponseApiDto, SchoolLevel> {


    @Override
    public SchoolLevelResponseApiDto toDto(SchoolLevel entity) {
        return new SchoolLevelResponseApiDto(
                entity.getId(),
                entity.getName()
        );
    }

    @Override
    public SchoolLevel toEntity(SchoolLevelResponseApiDto dto) {
        return new SchoolLevel(
                dto.getId(),
                dto.getName()
        );
    }

    public SchoolLevel toEntity(SchoolLevelRequestDto dto) {
        return new SchoolLevel(
                dto.getId(),
                dto.getName()
        );
    }
}
