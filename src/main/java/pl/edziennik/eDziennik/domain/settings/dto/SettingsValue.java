package pl.edziennik.eDziennik.domain.settings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SettingsValue {

    @NotNull
    private Boolean enabled;

}
