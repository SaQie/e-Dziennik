package pl.edziennik.domain.setting;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "app_settings")
@IdClass(SettingsId.class)
@EqualsAndHashCode
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configuration_id_seq")
    @SequenceGenerator(name = "configuration_id_seq", sequenceName = "configuration_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    private String name;
    private Boolean booleanValue;
    private String stringValue;
    private Long longValue;


    @PrePersist
    @PreUpdate
    public void checkValues() {
        if (!isValidValues()) {
            throw new RuntimeException("One of settings value must be not null");
        }
    }

    public SettingsId getSettingsId() {
        return SettingsId.wrap(id);
    }

    private boolean isValidValues() {
        return booleanValue != null || stringValue != null || longValue != null;
    }
}
