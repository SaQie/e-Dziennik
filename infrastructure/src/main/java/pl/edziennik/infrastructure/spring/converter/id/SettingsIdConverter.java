package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.domain.setting.SettingsId;

public class SettingsIdConverter implements Converter<String, SettingsId> {
    @Override
    public SettingsId convert(String source) {
        return SettingsId.wrap(Long.valueOf(source));
    }
}
