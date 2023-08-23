package pl.edziennik.common.valueobject.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.attribute.TimeFrameDurationAttributeConverter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Embeddable
@Accessors(fluent = true)
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeFrame {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @Convert(converter = TimeFrameDurationAttributeConverter.class)
    @JsonProperty
    private TimeFrameDuration duration;

    @Builder
    @JsonCreator
    public static TimeFrame of(LocalDateTime startDate, LocalDateTime endDate) {
        Duration duration = Duration.between(startDate, endDate);
        long minutes = duration.toMinutes();

        return new TimeFrame(startDate, endDate, TimeFrameDuration.of((int) minutes));
    }

    public String formattedStartDate() {
        return startDate.format(DATE_TIME_FORMATTER);
    }


    public String formattedEndDate() {
        return endDate.format(DATE_TIME_FORMATTER);
    }

}
