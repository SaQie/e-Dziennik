package pl.edziennik.eDziennik.domain.studensubject.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudentSubjectRequestDto {

    public static final String ID_SUBJECT = "idSubject";
    public static final String ID_STUDENT = "idStudent";

    @NotNull(message = "{subject.empty}")
    private Long idSubject;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long idStudent;

    public void setIdStudent(Long idStudent){
        this.idStudent = idStudent;
    }

}
