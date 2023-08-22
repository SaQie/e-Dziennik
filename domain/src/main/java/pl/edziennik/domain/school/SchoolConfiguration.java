package pl.edziennik.domain.school;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.properties.SchoolConfigurationProperties;
import pl.edziennik.common.valueobject.id.SchoolConfigurationId;
import pl.edziennik.common.valueobject.vo.TimeFrameDuration;

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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "max_lesson_time", nullable = false))
    })
    private TimeFrameDuration maxLessonTime;


    public static SchoolConfiguration createConfigFromProperties(SchoolConfigurationProperties properties) {
        SchoolConfiguration schoolConfiguration = new SchoolConfiguration();

        schoolConfiguration.averageType = properties.getAverageType();
        schoolConfiguration.maxLessonTime = properties.getMaxLessonTime();

        return schoolConfiguration;
    }

    public void changeAverageType(AverageType averageType) {
        if (averageType != null && averageType != this.averageType) {
            this.averageType = averageType;
        }
    }

    public void changeMaxLessonTime(TimeFrameDuration timeFrameDuration) {
        if (timeFrameDuration != null && !timeFrameDuration.equals(maxLessonTime)) {
            this.maxLessonTime = timeFrameDuration;
        }
    }


}
