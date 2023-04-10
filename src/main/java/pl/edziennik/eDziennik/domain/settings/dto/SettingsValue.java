package pl.edziennik.eDziennik.domain.settings.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Builder
public record SettingsValue(
        Boolean booleanValue,
        String stringValue,
        Long longValue
) {


}
