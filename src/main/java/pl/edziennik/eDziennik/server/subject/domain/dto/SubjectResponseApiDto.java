package pl.edziennik.eDziennik.server.subject.domain.dto;

import lombok.Getter;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.basics.AbstractDto;

@Getter
public class SubjectResponseApiDto extends AbstractDto {

    private Long id;
    private String name;
    private String description;
    private TeacherResponseApiDto teacher;

    public SubjectResponseApiDto(Long id, String name, String description, TeacherResponseApiDto teacher) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teacher = teacher;
    }
}
