package pl.edziennik.domain.personinfromation;

public record PhoneNumber (
        String value
){

    public static PhoneNumber of(String value){
        return new PhoneNumber(value);
    }

}
