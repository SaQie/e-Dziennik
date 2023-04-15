package pl.edziennik.eDziennik.domain.parent.domain.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.parent.domain.wrapper.ParentId;

@Builder
public record ParentSimpleResponseApiDto(
        @JsonUnwrapped
        ParentId parentId,
        String fullName
) {

}
