package pl.edziennik.common.valueobject.vo;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.attribute.TimeFrameDurationAttributeConverter;

import java.time.Duration;
import java.time.LocalDateTime;

@Embeddable
@Accessors(fluent = true)
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class TimeFrame {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Convert(converter = TimeFrameDurationAttributeConverter.class)
    private TimeFrameDuration duration;

    @Builder
    public static TimeFrame of(LocalDateTime startDate, LocalDateTime endDate) {
        Duration duration = Duration.between(startDate, endDate);
        long minutes = duration.toMinutes();

        return new TimeFrame(startDate, endDate, TimeFrameDuration.of(minutes));
    }

}
