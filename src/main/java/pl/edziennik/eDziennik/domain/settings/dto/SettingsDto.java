package pl.edziennik.eDziennik.domain.settings.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;


@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Builder
public record SettingsDto(
        Long id,
        String name,
        Boolean booleanValue,
        String stringValue,
        Long longValue
) {

}
