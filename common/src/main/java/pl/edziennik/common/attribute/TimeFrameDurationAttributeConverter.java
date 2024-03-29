package pl.edziennik.common.attribute;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.edziennik.common.valueobject.vo.TimeFrameDuration;

@Converter(autoApply = true)
public class TimeFrameDurationAttributeConverter implements AttributeConverter<TimeFrameDuration, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TimeFrameDuration timeFrameDuration) {
        return timeFrameDuration == null ? null : timeFrameDuration.value();
    }

    @Override
    public TimeFrameDuration convertToEntityAttribute(Integer value) {
        return value == null ? null : TimeFrameDuration.of(value);
    }
}
