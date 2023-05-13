package pl.edziennik.common.attribute;

import jakarta.persistence.AttributeConverter;
import pl.edziennik.common.valueobject.LastName;

public class LastNameAttributeConverter implements AttributeConverter<LastName, String> {
    @Override
    public String convertToDatabaseColumn(LastName lastName) {
        return lastName == null ? null : lastName.value();
    }

    @Override
    public LastName convertToEntityAttribute(String value) {
        return value == null ? null : LastName.of(value);
    }
}