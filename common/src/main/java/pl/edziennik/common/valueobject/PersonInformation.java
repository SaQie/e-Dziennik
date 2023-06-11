package pl.edziennik.common.valueobject;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.edziennik.common.attribute.FirstNameAttributeConverter;
import pl.edziennik.common.attribute.FullNameAttributeConverter;
import pl.edziennik.common.attribute.LastNameAttributeConverter;
import pl.edziennik.common.attribute.PhoneNumberAttributeConverter;

import java.io.Serializable;


@Embeddable
@Accessors(fluent = true)
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class PersonInformation implements Serializable {

    @Convert(converter = FirstNameAttributeConverter.class)
    private FirstName firstName;

    @Convert(converter = LastNameAttributeConverter.class)
    private LastName lastName;

    @Convert(converter = FullNameAttributeConverter.class)
    private FullName fullName;

    @Convert(converter = PhoneNumberAttributeConverter.class)
    private PhoneNumber phoneNumber;

    public static PersonInformation of(FirstName firstName, LastName lastName, PhoneNumber phoneNumber) {
        return new PersonInformation(firstName, lastName, FullName.of(firstName, lastName), phoneNumber);
    }


}

