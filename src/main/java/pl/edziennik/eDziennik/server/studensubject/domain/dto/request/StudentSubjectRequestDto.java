package pl.edziennik.eDziennik.server.studensubject.domain.dto.request;

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

    @NotNull(message = "{subject.empty}")
    private Long idSubject;

}
