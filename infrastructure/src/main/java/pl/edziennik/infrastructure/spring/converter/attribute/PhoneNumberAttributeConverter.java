package pl.edziennik.infrastructure.spring.converter.attribute;


import jakarta.persistence.AttributeConverter;
import pl.edziennik.domain.personinfromation.PhoneNumber;

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
