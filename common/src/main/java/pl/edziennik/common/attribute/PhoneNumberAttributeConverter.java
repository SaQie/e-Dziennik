package pl.edziennik.common.attribute;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.edziennik.common.valueobject.vo.PhoneNumber;

@Converter(autoApply = true)
public class PhoneNumberAttributeConverter implements AttributeConverter<PhoneNumber, String> {
    @Override
    public String convertToDatabaseColumn(PhoneNumber phoneNumber) {
        return phoneNumber == null ? null : phoneNumber.value();
    }

    @Override
    public PhoneNumber convertToEntityAttribute(String value) {
        return value == null ? null : PhoneNumber.of(value);

    }
}
