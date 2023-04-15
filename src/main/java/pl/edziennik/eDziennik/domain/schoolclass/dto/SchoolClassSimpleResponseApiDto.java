package pl.edziennik.eDziennik.domain.schoolclass.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;

@Builder
public record SchoolClassSimpleResponseApiDto(
        @JsonUnwrapped
        SchoolClassId schoolClassId,
        String className) {

}
