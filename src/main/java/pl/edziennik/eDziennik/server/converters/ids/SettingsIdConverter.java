package pl.edziennik.eDziennik.server.converters.ids;

import org.springframework.core.convert.converter.Converter;
import pl.edziennik.eDziennik.domain.settings.domain.wrapper.SettingsId;

public class SettingsIdConverter implements Converter<String, SettingsId> {
    @Override
    public SettingsId convert(String source) {
        return SettingsId.wrap(Long.valueOf(source));
    }
}
