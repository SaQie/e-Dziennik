package pl.edziennik.eDziennik.domain.personinformation.domain.wrapper;

public record PhoneNumber (
        String value
){

    public static PhoneNumber of(String value){
        return new PhoneNumber(value);
    }

}
