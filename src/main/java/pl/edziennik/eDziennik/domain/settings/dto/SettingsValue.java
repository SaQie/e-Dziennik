package pl.edziennik.eDziennik.domain.settings.dto;

import lombok.Builder;



@Builder
public record SettingsValue(
        Boolean booleanValue,
        String stringValue,
        Long longValue
) {


}
