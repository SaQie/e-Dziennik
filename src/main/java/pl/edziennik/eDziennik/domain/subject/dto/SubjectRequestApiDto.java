package pl.edziennik.eDziennik.domain.subject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record SubjectRequestApiDto(
        @NotEmpty(message = "{name.empty}")
        String name,
        String description,
        Long idTeacher,
        @NotNull(message = "{schoolClass.empty}")
        Long idSchoolClass
){

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String ID_TEACHER = "idTeacher";
    public static final String ID_SCHOOL_CLASS = "idSchoolClass";

}
