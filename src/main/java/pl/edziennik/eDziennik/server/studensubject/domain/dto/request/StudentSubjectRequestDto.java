package pl.edziennik.eDziennik.server.studensubject.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudentSubjectRequestDto {

    public static final String ID_SUBJECT = "idSubject";

    private Long idSubject;

}
