package pl.edziennik.eDziennik.server.subject.domain.dto.mapper;

import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.mapper.TeacherMapper;

public class SubjectMapper {


    private SubjectMapper() {
    }

    public static SubjectResponseApiDto toDto(Subject entity) {
        if (entity.getTeacher() != null) {
            return new SubjectResponseApiDto(
                    entity.getId(),
                    entity.getName(),
                    entity.getDescription(),
                    entity.getTeacher().getId(),
                    entity.getSchoolClass().getId()
            );
        }else{
            return new SubjectResponseApiDto(
                    entity.getId(),
                    entity.getName(),
                    entity.getDescription(),
                    entity.getSchoolClass().getId()
            );
        }
    }

    public static Subject toEntity(SubjectRequestApiDto dto) {
        return new Subject(
                dto.getName(),
                dto.getDescription()
        );
    }
}
