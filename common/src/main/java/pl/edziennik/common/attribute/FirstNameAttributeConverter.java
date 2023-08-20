package pl.edziennik.common.attribute;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.edziennik.common.valueobject.vo.FirstName;

@Converter(autoApply = true)
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