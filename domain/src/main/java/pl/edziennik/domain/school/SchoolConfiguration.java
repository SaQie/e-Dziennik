package pl.edziennik.domain.school;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.properties.SchoolConfigurationProperties;
import pl.edziennik.common.valueobject.id.SchoolConfigurationId;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolConfiguration {

    @EmbeddedId
    private SchoolConfigurationId schoolConfigurationId = SchoolConfigurationId.create();

    @Enumerated(EnumType.STRING)
    @Column(name = "average_type", nullable = false)
    private AverageType averageType;


    public static SchoolConfiguration createConfigFromProperties(SchoolConfigurationProperties properties) {
        SchoolConfiguration schoolConfiguration = new SchoolConfiguration();

        schoolConfiguration.averageType = properties.getAverageType();

        return schoolConfiguration;
    }

    public void changeAverageType(AverageType averageType) {
        if (averageType != this.averageType) {
            this.averageType = averageType;
        }
    }


}
