package pl.edziennik.domain.personinfromation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pesel {

    @JsonProperty(value = "schoolId", access = JsonProperty.Access.READ_ONLY)
    private final String pesel;

    @JsonCreator
    public Pesel(String pesel) {
        this.pesel = pesel;
    }

    public static Pesel of(String value) {
        return new Pesel(value);
    }

}
