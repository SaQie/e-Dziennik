package pl.edziennik.eDziennik.domain.settings.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.settings.domain.wrapper.SettingsId;


@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Builder
public record SettingsDto(
        @JsonUnwrapped
        SettingsId id,
        String name,
        Boolean booleanValue,
        String stringValue,
        Long longValue
) {

    public SettingsDto(
            Long id, String name, Boolean booleanValue, String stringValue, Long longValue) {
        this(SettingsId.wrap(id), name, booleanValue, stringValue, longValue);
    }
}
