package pl.edziennik.eDziennik.domain.settings.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SettingsDto {

    private final Long id;
    private final String name;
    private final boolean enabled;

}