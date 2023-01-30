package pl.edziennik.eDziennik.server.settings.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SettingsDto {

    private Long id;
    private String name;
    private boolean enabled;

}
