package pl.edziennik.eDziennik.domain.personinformation.domain.wrapper;

public record Pesel(
        String value
) {

    public static Pesel of(String value) {
        return new Pesel(value);
    }

}
