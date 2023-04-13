package pl.edziennik.eDziennik.domain.studentsubject.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record StudentSubjectRequestDto(
        @NotNull(message = "{subject.empty}")
        Long idSubject,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long idStudent
) {

    public static final String ID_SUBJECT = "idSubject";
    public static final String ID_STUDENT = "idStudent";


}
