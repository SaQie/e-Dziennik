package pl.edziennik.eDziennik.domain.settings.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.domain.settings.domain.wrapper.SettingsId;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "app_settings")
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
            throw new BusinessException("One of settings value must be not null");
        }
    }

    public SettingsId getSettingsId() {
        return SettingsId.wrap(id);
    }

    private boolean isValidValues() {
        return booleanValue != null || stringValue != null || longValue != null;
    }
}
