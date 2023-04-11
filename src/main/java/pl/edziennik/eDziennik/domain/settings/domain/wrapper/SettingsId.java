package pl.edziennik.eDziennik.domain.settings.domain.wrapper;

public record SettingsId(
        Long id
) {
    public static SettingsId wrap(Long id) {
        return new SettingsId(id);
    }

}
