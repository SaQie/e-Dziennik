package pl.edziennik.eDziennik.domain.schoollevel.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.schoollevel.domain.wrapper.SchoolLevelId;

@Builder
public record SchoolLevelResponseApiDto(
        @JsonUnwrapped
        SchoolLevelId schoolLevelId,
        String name
) {

}
