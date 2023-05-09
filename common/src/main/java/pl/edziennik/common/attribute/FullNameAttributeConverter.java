package pl.edziennik.common.attribute;

import jakarta.persistence.AttributeConverter;
import pl.edziennik.common.valueobject.FirstName;
import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.LastName;

public class FullNameAttributeConverter implements AttributeConverter<FullName, String> {
    @Override
    public String convertToDatabaseColumn(FullName lastName) {
        return lastName == null ? null : lastName.value();
    }

    @Override
    public FullName convertToEntityAttribute(String value) {
        return value == null ? null : FullName.of(FirstName.of(""), LastName.of(""));
    }
}