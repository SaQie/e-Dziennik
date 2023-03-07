package pl.edziennik.eDziennik.domain.schoolclass.dto.mapper;

import pl.edziennik.eDziennik.domain.school.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.mapper.TeacherMapper;

import java.util.List;

public class SchoolClassMapper {

    private SchoolClassMapper() {
    }

    public static SchoolClassResponseApiDto toDto(SchoolClass entity) {
        if (entity.getTeacher() != null) {
            return new SchoolClassResponseApiDto(
                    entity.getId(),
                    entity.getClassName(),
                    SchoolMapper.toSimpleDto(entity.getSchool()),
                    TeacherMapper.toSimpleDto(entity.getTeacher())
            );
        } else {
            return new SchoolClassResponseApiDto(
                    entity.getId(),
                    entity.getClassName(),
                    SchoolMapper.toSimpleDto(entity.getSchool()),
                    null
            );
        }
    }

    public static List<SchoolClassResponseApiDto> toDto(List<SchoolClass> entities) {
        return entities.stream().map(SchoolClassMapper::toDto).toList();
    }

    public static SchoolClass toEntity(SchoolClassRequestApiDto dto) {
        return new SchoolClass(
                dto.getClassName()
        );
    }

    public static SchoolClassSimpleResponseApiDto toSimpleDto(SchoolClass schoolClass) {
        return new SchoolClassSimpleResponseApiDto(schoolClass.getId(), schoolClass.getClassName());
    }
}
