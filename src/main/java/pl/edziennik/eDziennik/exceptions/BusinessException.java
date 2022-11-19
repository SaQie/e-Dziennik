package pl.edziennik.eDziennik.exceptions;

public class BusinessException extends RuntimeException{

    private String field;

    // To mozna bardziej rozbudowac (jakims obiektem POJO) ktory bedzie mial w sobie pole i message do kazdego pola moze byc wiadomosc bledu
    // czyli bedzie np.
    // Arrays.asList("idSchool", "Nie udalo sie odnalezc szkoly", "idSchoolLevel" , "Poziom z x nie istnieje")
    // i wtedy to zapakowac tutaj do konstruktora
    // zrobic gettery
    // i masz wskazania na pola ladnie i ich komunikat bledu :)

    public BusinessException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getErrorFieldName(){
        return field;
    }


}
