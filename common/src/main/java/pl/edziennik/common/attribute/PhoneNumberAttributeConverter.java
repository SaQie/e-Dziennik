package pl.edziennik.common.attribute;


import jakarta.persistence.AttributeConverter;
import pl.edziennik.common.valueobject.PhoneNumber;

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
