package pl.edziennik.eDziennik.server.converters.attributes;

import jakarta.persistence.AttributeConverter;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.Pesel;

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
