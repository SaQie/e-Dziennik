package pl.edziennik.eDziennik.server.schoolclass.domain.dto.mapper;

import pl.edziennik.eDziennik.server.school.domain.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.mapper.TeacherMapper;

public class SchoolClassMapper {

    private SchoolClassMapper() {
    }

    public static SchoolClassResponseApiDto toDto(SchoolClass entity) {
        return new SchoolClassResponseApiDto(
                entity.getId(),
                entity.getClassName(),
                TeacherMapper.toDto(entity.getTeacher()),
                SchoolMapper.toDto(entity.getSchool())
        );
    }

    public static SchoolClass toEntity(SchoolClassRequestApiDto dto) {
        return new SchoolClass(
                dto.getClassName()
        );
    }
}
