package pl.edziennik.eDziennik.server.schoolclass.domain.dto.mapper;

import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.server.subject.domain.Subject;

public class SchoolClassMapper {

    private SchoolClassMapper() {
    }

    public static SchoolClassResponseApiDto toDto(SchoolClass entity) {
        if (entity.getTeacher() != null) {
            return new SchoolClassResponseApiDto(
                    entity.getId(),
                    entity.getClassName(),
                    entity.getSchool().getId(),
                    entity.getTeacher().getId()
            );
        }else{
            return new SchoolClassResponseApiDto(
                    entity.getId(),
                    entity.getClassName(),
                    entity.getSchool().getId(),
                    null
            );
        }
    }

    public static SchoolClass toEntity(SchoolClassRequestApiDto dto) {
        return new SchoolClass(
                dto.getClassName()
        );
    }
}
