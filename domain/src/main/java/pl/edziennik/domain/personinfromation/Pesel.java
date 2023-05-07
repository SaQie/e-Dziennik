package pl.edziennik.domain.personinfromation;

public record Pesel(
        String value
) {

    public static Pesel of(String value) {
        return new Pesel(value);
    }

}
