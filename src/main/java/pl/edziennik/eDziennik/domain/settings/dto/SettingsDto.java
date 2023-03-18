package pl.edziennik.eDziennik.domain.settings.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SettingsDto {

    private final Long id;
    private final String name;
    private final Boolean booleanValue;
    private final String stringValue;
    private final Long longValue;

    public SettingsDto(Long id, String name, boolean booleanValue) {
        this.id = id;
        this.name = name;
        this.booleanValue = booleanValue;
        this.longValue = null;
        this.stringValue = null;
    }

    public SettingsDto(Long id, String name, String stringValue) {
        this.id = id;
        this.name = name;
        this.stringValue = stringValue;
        this.longValue = null;
        this.booleanValue = null;
    }

    public SettingsDto(Long id, String name, Long longValue) {
        this.id = id;
        this.name = name;
        this.longValue = longValue;
        this.booleanValue = null;
        this.stringValue = null;
    }
}
