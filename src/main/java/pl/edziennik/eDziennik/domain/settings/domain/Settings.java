package pl.edziennik.eDziennik.domain.settings.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import pl.edziennik.eDziennik.server.basics.entity.AbstractEntity;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "app_settings")
public class Settings extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configuration_id_seq")
    @SequenceGenerator(name = "configuration_id_seq", sequenceName = "configuration_id_seq", allocationSize = 1)
    private Long id;

    private String name;
    private Boolean booleanValue;
    private String stringValue;
    private Long longValue;

    @Override
    public boolean isNew() {
        return (id == null);
    }

    @PrePersist
    @PreUpdate
    public void checkValues() {
        if (!isValidValues()) {
            throw new BusinessException("One of settings value must be not null");
        }
    }

    private boolean isValidValues() {
        return booleanValue != null || stringValue != null || longValue != null;
    }
}
