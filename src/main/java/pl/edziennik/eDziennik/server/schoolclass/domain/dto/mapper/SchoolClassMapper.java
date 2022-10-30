package pl.edziennik.eDziennik.server.schoolclass.domain.dto.mapper;

import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassResponseApiDto;

public class SchoolClassMapper {

    private SchoolClassMapper() {
    }

    public static SchoolClassResponseApiDto toDto(SchoolClass entity) {
        return new SchoolClassResponseApiDto(
                entity.getId(),
                entity.getClassName(),
                entity.getSchool().getId(),
                entity.getTeacher().getId()
        );
    }

    public static SchoolClass toEntity(SchoolClassRequestApiDto dto) {
        return new SchoolClass(
                dto.getClassName()
        );
    }
}
