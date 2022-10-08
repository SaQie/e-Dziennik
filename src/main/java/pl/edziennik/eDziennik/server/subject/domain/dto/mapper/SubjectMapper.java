package pl.edziennik.eDziennik.server.subject.domain.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.school.domain.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.mapper.TeacherMapper;
import pl.edziennik.eDziennik.server.basics.AbstractMapper;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;

@Component
@AllArgsConstructor
public class SubjectMapper implements AbstractMapper<SubjectResponseApiDto, Subject> {

    private final TeacherMapper teacherMapper;
    private final SchoolMapper schoolMapper;


    @Override
    public SubjectResponseApiDto toDto(Subject entity) {
        return new SubjectResponseApiDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                teacherMapper.toDto(entity.getTeacher())
        );
    }

    @Override
    public Subject toEntity(SubjectResponseApiDto dto) {
        return null;
    }

    public Subject toEntity(SubjectRequestApiDto dto){
        return new Subject(
            dto.getName(),
            dto.getDescription()
        );
    }
}
