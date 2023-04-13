package pl.edziennik.eDziennik.domain.personinformation.domain;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.spi.EmbeddableInstantiator;
import org.hibernate.metamodel.spi.ValueAccess;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.Pesel;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.PhoneNumber;

public class PersonInformationInstantiator implements EmbeddableInstantiator {
    @Override
    public Object instantiate(ValueAccess valueAccess, SessionFactoryImplementor sessionFactoryImplementor) {
        final String firstName = valueAccess.getValue(0, String.class);
        final String lastName = valueAccess.getValue(2, String.class);
        final String fullName = firstName + " " + lastName;
        final Pesel pesel = valueAccess.getValue(3, Pesel.class);
        final PhoneNumber phoneNumber =valueAccess.getValue(4, PhoneNumber.class);
        return new PersonInformation(firstName, lastName, fullName, phoneNumber, pesel);
    }

    @Override
    public boolean isInstance(Object o, SessionFactoryImplementor sessionFactoryImplementor) {
        return o instanceof PersonInformation;
    }

    @Override
    public boolean isSameClass(Object o, SessionFactoryImplementor sessionFactoryImplementor) {
        return o.getClass().equals(PersonInformation.class);
    }
}
