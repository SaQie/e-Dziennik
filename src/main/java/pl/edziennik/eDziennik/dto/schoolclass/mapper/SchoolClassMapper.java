package pl.edziennik.eDziennik.dto.schoolclass.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.dto.schoolclass.SchoolClassRequestDto;
import pl.edziennik.eDziennik.dto.schoolclass.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.dto.teacher.mapper.TeacherMapper;
import pl.edziennik.eDziennik.server.schoolclass.SchoolClass;
import pl.edziennik.eDziennik.server.basics.AbstractMapper;

@Component
@AllArgsConstructor
public class SchoolClassMapper implements AbstractMapper<SchoolClassResponseApiDto, SchoolClass> {

    private final TeacherMapper teacherMapper;

    @Override
    public SchoolClassResponseApiDto toDto(SchoolClass entity) {
        return new SchoolClassResponseApiDto(
                entity.getId(),
                entity.getClassName(),
                teacherMapper.toDto(entity.getTeacher())
        );
    }

    @Override
    public SchoolClass toEntity(SchoolClassResponseApiDto dto) {
        return null;
    }

    public SchoolClass toEntity(SchoolClassRequestDto dto){
        return new SchoolClass(
                dto.getClassName()
        );
    }
}
