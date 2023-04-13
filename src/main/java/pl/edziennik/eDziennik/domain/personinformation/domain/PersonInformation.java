package pl.edziennik.eDziennik.domain.personinformation.domain;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.EmbeddableInstantiator;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.Pesel;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.PhoneNumber;
import pl.edziennik.eDziennik.server.converters.attributes.PeselAttributeConverter;
import pl.edziennik.eDziennik.server.converters.attributes.PhoneNumberAttributeConverter;


@Embeddable
@EmbeddableInstantiator(PersonInformationInstantiator.class)
public record PersonInformation(
        String firstName,
        String lastName,
        String fullName,

        @Convert(converter = PhoneNumberAttributeConverter.class)
        PhoneNumber phoneNumber,

        @Convert(converter = PeselAttributeConverter.class)
        Pesel pesel

) {

    public static PersonInformation of(String firstName, String lastName, PhoneNumber phoneNumber, Pesel pesel) {
        String fullName = firstName + " " + lastName;
        return new PersonInformation(firstName, lastName, fullName, phoneNumber, pesel);
    }

    public PersonInformation() {
        this(null, null, null, null, null);
    }
}
