package pl.edziennik.common.attribute;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.edziennik.common.valueobject.vo.LastName;

@Converter(autoApply = true)
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
