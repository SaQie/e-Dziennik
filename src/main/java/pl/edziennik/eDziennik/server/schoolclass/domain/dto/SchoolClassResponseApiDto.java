package pl.edziennik.eDziennik.server.schoolclass.domain.dto;

import lombok.Getter;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolSimpleResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherSimpleResponseApiDto;

import java.util.List;

@Getter
public class SchoolClassResponseApiDto {

    private final Long id;
    private final String className;
    private final TeacherSimpleResponseApiDto supervisingTeacher;
    private final SchoolSimpleResponseApiDto school;

    public SchoolClassResponseApiDto(Long id, String className, SchoolSimpleResponseApiDto school, TeacherSimpleResponseApiDto supervisingTeacher) {
        this.id = id;
        this.className = className;
        this.supervisingTeacher = supervisingTeacher;
        this.school = school;
    }
}
