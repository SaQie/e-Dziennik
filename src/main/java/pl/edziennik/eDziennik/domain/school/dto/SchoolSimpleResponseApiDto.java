package pl.edziennik.eDziennik.domain.school.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;

@Builder
public record SchoolSimpleResponseApiDto(
        @JsonUnwrapped
        SchoolId schoolId,
        String name) {

}
