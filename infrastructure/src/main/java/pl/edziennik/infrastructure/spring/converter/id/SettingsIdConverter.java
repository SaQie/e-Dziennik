package pl.edziennik.infrastructure.spring.converter.id;


import org.springframework.core.convert.converter.Converter;
import pl.edziennik.common.valueobject.id.SettingsId;

public class SettingsIdConverter implements Converter<String, SettingsId> {
    @Override
    public SettingsId convert(String source) {
        return SettingsId.of(source);
    }
}
