package pl.edziennik.eDziennik.server.utils;

import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;

/**
 * Utility class for getting person information
 */
@Component
public class PersonUtils {

    public String getFullName(PersonInformation personInformation){
        return personInformation.getFirstName() + " " + personInformation.getLastName();
    }


}
