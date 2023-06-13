package pl.edziennik.domain.school;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.valueobject.id.SchoolConfigurationId;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SchoolConfiguration {

    @EmbeddedId
    private SchoolConfigurationId schoolConfigurationId = SchoolConfigurationId.create();

    @Enumerated(EnumType.STRING)
    @Column(name = "average_type", nullable = false)
    private AverageType averageType;

    public static SchoolConfiguration defaultConfig() {
        SchoolConfiguration schoolConfiguration = new SchoolConfiguration();
        schoolConfiguration.averageType = AverageType.ARITHMETIC;

        return schoolConfiguration;
    }

    public void changeAverageType(AverageType averageType) {
        if (averageType != this.averageType) {
            this.averageType = averageType;
        }
    }


}
