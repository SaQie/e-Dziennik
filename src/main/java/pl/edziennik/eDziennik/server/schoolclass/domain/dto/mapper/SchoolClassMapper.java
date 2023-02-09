package pl.edziennik.eDziennik.server.schoolclass.domain.dto.mapper;

import pl.edziennik.eDziennik.server.school.domain.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassSimpleResponseApiDto;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.teacher.domain.dto.mapper.TeacherMapper;

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
        }else{
            return new SchoolClassResponseApiDto(
                    entity.getId(),
                    entity.getClassName(),
                    SchoolMapper.toSimpleDto(entity.getSchool()),
                    null
            );
        }
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
