package pl.edziennik.common.attribute;

import jakarta.persistence.AttributeConverter;
import pl.edziennik.common.valueobject.FirstName;

public class FirstNameAttributeConverter implements AttributeConverter<FirstName, String> {
    @Override
    public String convertToDatabaseColumn(FirstName firstName) {
        return firstName == null ? null : firstName.value();
    }

    @Override
    public FirstName convertToEntityAttribute(String value) {
        return value == null ? null : FirstName.of(value);
    }
}