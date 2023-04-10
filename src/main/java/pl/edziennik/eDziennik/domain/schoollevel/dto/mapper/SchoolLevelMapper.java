package pl.edziennik.eDziennik.domain.schoollevel.dto.mapper;

import pl.edziennik.eDziennik.domain.schoollevel.dto.SchoolLevelRequestApiDto;
import pl.edziennik.eDziennik.domain.schoollevel.dto.SchoolLevelResponseApiDto;
import pl.edziennik.eDziennik.domain.schoollevel.domain.SchoolLevel;

import java.util.List;

public class SchoolLevelMapper {
    private SchoolLevelMapper() {
    }

    public static SchoolLevelResponseApiDto toDto(SchoolLevel entity) {
        return new SchoolLevelResponseApiDto(
                entity.getId(),
                entity.getName()
        );
    }

    public static List<SchoolLevelResponseApiDto> toDto(List<SchoolLevel> entities) {
        return entities.stream().map(schoolLevel ->
                new SchoolLevelResponseApiDto(schoolLevel.getId(), schoolLevel.getName())).toList();
    }

    public static SchoolLevel toEntity(SchoolLevelResponseApiDto dto) {
        return new SchoolLevel(
                dto.id(),
                dto.name()
        );
    }

    public static SchoolLevel toEntity(SchoolLevelRequestApiDto dto) {
        return new SchoolLevel(
                dto.id(),
                dto.name()
        );
    }
}
