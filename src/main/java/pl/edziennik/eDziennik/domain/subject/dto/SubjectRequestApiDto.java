package pl.edziennik.eDziennik.domain.subject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;


@Builder
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class SubjectRequestApiDto {
    @NotEmpty(message = "{name.empty}")
    private final String name;
    private final String description;
    private final TeacherId teacherId;
    @NotNull(message = "{schoolClass.empty}")
    private final SchoolClassId schoolClassId;


    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String ID_TEACHER = "idTeacher";
    public static final String ID_SCHOOL_CLASS = "idSchoolClass";

}
