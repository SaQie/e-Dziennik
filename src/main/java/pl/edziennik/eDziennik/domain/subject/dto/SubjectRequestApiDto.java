package pl.edziennik.eDziennik.domain.subject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
