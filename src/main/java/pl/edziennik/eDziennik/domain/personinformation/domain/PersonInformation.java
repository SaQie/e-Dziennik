package pl.edziennik.eDziennik.domain.personinformation.domain;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.Pesel;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.PhoneNumber;
import pl.edziennik.eDziennik.server.basic.vo.ValueObject;
import pl.edziennik.eDziennik.server.converter.attribute.PeselAttributeConverter;
import pl.edziennik.eDziennik.server.converter.attribute.PhoneNumberAttributeConverter;

import java.io.Serializable;


@Embeddable
@Accessors(fluent = true)
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class PersonInformation implements Serializable, ValueObject {
    private String firstName;
    private String lastName;
    private String fullName;

    @Convert(converter = PhoneNumberAttributeConverter.class)
    private PhoneNumber phoneNumber;

    @Convert(converter = PeselAttributeConverter.class)
    private Pesel pesel;


    public static PersonInformation of(String firstName, String lastName, PhoneNumber phoneNumber, Pesel pesel) {
        String fullName = firstName + " " + lastName;
        return new PersonInformation(firstName, lastName, fullName, phoneNumber, pesel);
    }


}

