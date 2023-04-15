package pl.edziennik.eDziennik.domain.settings.dto.mapper;

import pl.edziennik.eDziennik.domain.settings.domain.Settings;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsDto;

public class SettingsMapper {

    private SettingsMapper() {
    }

    public static SettingsDto mapToDto(Settings settings){
        return new SettingsDto(
                settings.getSettingsId(),
                settings.getName(),
                settings.getBooleanValue(),
                settings.getStringValue(),
                settings.getLongValue()
        );
    }
}
