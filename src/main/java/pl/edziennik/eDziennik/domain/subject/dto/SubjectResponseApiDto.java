package pl.edziennik.eDziennik.domain.subject.dto;

import lombok.Getter;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherSimpleResponseApiDto;

@Getter
public class SubjectResponseApiDto {

    private final Long id;
    private final String name;
    private final String description;
    private final SchoolClassSimpleResponseApiDto schoolClass;
    private final TeacherSimpleResponseApiDto teacher;

    public SubjectResponseApiDto(Long id, String name, String description, TeacherSimpleResponseApiDto teacher, SchoolClassSimpleResponseApiDto schoolClass) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teacher = teacher;
        this.schoolClass = schoolClass;
    }

    public SubjectResponseApiDto(Long id, String name, String description, SchoolClassSimpleResponseApiDto schoolClass) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teacher = null;
        this.schoolClass = schoolClass;
    }
}
