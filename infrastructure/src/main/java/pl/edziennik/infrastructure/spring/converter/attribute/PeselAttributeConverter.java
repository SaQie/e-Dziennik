package pl.edziennik.infrastructure.spring.converter.attribute;


import jakarta.persistence.AttributeConverter;
import pl.edziennik.domain.personinfromation.Pesel;

public class PeselAttributeConverter implements AttributeConverter<Pesel, String> {
    @Override
    public String convertToDatabaseColumn(Pesel pesel) {
        return pesel == null ? null : pesel.value();
    }

    @Override
    public Pesel convertToEntityAttribute(String value) {
        return value == null ? null : Pesel.of(value);
    }
}
