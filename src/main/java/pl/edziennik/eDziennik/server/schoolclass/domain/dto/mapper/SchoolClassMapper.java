package pl.edziennik.eDziennik.server.schoolclass.domain.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.school.domain.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.mapper.TeacherMapper;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.basics.AbstractMapper;

@Component
@AllArgsConstructor
public class SchoolClassMapper implements AbstractMapper<SchoolClassResponseApiDto, SchoolClass> {

    private final TeacherMapper teacherMapper;
    private final SchoolMapper schoolMapper;

    @Override
    public SchoolClassResponseApiDto toDto(SchoolClass entity) {
        return new SchoolClassResponseApiDto(
                entity.getId(),
                entity.getClassName(),
                teacherMapper.toDto(entity.getTeacher()),
                schoolMapper.toDto(entity.getSchool())
        );
    }

    @Override
    public SchoolClass toEntity(SchoolClassResponseApiDto dto) {
        return null;
    }

    public SchoolClass toEntity(SchoolClassRequestApiDto dto){
        return new SchoolClass(
                dto.getClassName()
        );
    }
}
