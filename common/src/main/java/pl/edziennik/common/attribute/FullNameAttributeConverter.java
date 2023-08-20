package pl.edziennik.common.attribute;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.edziennik.common.valueobject.vo.FullName;

@Converter(autoApply = true)
public class FullNameAttributeConverter implements AttributeConverter<FullName, String> {
    @Override
    public String convertToDatabaseColumn(FullName lastName) {
        return lastName == null ? null : lastName.value();
    }

    @Override
    public FullName convertToEntityAttribute(String value) {
        return value == null ? null : FullName.of(value);
    }
}