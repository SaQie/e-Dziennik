package pl.edziennik.eDziennik.server.subject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SubjectRequestApiDto{

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String ID_TEACHER = "idTeacher";

    @NotEmpty(message = "{name.empty}")
    private String name;
    private String description;
    private Long idTeacher;

}
