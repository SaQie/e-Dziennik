package pl.edziennik.eDziennik.domain.subject.dto.mapper;

import pl.edziennik.eDziennik.domain.schoolclass.dto.mapper.SchoolClassMapper;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.mapper.TeacherMapper;

import java.util.List;

public class SubjectMapper {


    private SubjectMapper() {
    }

    public static SubjectResponseApiDto toDto(Subject entity) {
        if (entity.getTeacher() != null) {
            return new SubjectResponseApiDto(
                    entity.getId(),
                    entity.getName(),
                    entity.getDescription(),
                    SchoolClassMapper.toSimpleDto(entity.getSchoolClass()),
                    TeacherMapper.toSimpleDto(entity.getTeacher())
            );
        } else {
            return new SubjectResponseApiDto(
                    entity.getId(),
                    entity.getName(),
                    entity.getDescription(),
                    SchoolClassMapper.toSimpleDto(entity.getSchoolClass()),
                    null
            );
        }
    }

    public static List<SubjectResponseApiDto> toDto(List<Subject> entites) {
        return entites.stream().map(SubjectMapper::toDto).toList();
    }

    public static Subject toEntity(SubjectRequestApiDto dto) {
        return new Subject(
                dto.name(),
                dto.description()
        );
    }
}
