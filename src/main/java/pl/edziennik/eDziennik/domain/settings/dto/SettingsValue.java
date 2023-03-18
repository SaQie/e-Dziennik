package pl.edziennik.eDziennik.domain.settings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SettingsValue {

    private Boolean booleanValue;
    private String stringValue;
    private Long longValue;

    public SettingsValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public SettingsValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public SettingsValue(Long longValue) {
        this.longValue = longValue;
    }
}
