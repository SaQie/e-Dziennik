package pl.edziennik.domain.setting;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SettingsId;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
@Table(name = "app_settings")
@EqualsAndHashCode
public class Settings {

    @EmbeddedId
    private SettingsId settingsId = SettingsId.create();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "name"))
    })
    private Name name;

    private Boolean booleanValue;
    private String stringValue;
    private Long longValue;


    public static Settings of(Name name, Boolean booleanValue) {
        Settings settings = new Settings();
        settings.booleanValue = booleanValue;
        settings.name = name;

        return settings;
    }

    public static Settings of(Name name, String stringValue) {
        Settings settings = new Settings();
        settings.stringValue = stringValue;
        settings.name = name;

        return settings;
    }

    public static Settings of(Name name, Long longValue) {
        Settings settings = new Settings();
        settings.longValue = longValue;
        settings.name = name;

        return settings;
    }

    public void changeValue(Boolean booleanValue){
        checkValues();
        this.booleanValue = booleanValue;
    }

    public void changeValue(String stringValue){
        checkValues();
        this.stringValue = stringValue;
    }

    public void changeValue(Long longValue){
        checkValues();
        this.longValue = longValue;
    }

    private void checkValues() {
        if (!isValidValues()) {
            throw new RuntimeException("One of settings value must be not null");
        }
    }

    private boolean isValidValues() {
        return booleanValue != null || stringValue != null || longValue != null;
    }
}
