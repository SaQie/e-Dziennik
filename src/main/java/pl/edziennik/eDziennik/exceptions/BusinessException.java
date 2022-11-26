package pl.edziennik.eDziennik.exceptions;

import java.util.List;

public class BusinessException extends RuntimeException{

    private BusinessExceptionPojo exceptionInfo;

    // To mozna bardziej rozbudowac (jakims obiektem POJO) ktory bedzie mial w sobie pole i message do kazdego pola moze byc wiadomosc bledu
    // czyli bedzie np.
    // Arrays.asList("idSchool", "Nie udalo sie odnalezc szkoly", "idSchoolLevel" , "Poziom z x nie istnieje")
    // i wtedy to zapakowac tutaj do konstruktora
    // zrobic gettery
    // i masz wskazania na pola ladnie i ich komunikat bledu :)

    public BusinessException(BusinessExceptionPojo exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public BusinessException(String message) {
        super(message);
    }

    public String getErrorFieldName(){
        return exceptionInfo.getFieldName();
    }

    public List<String> getErrorMessages(){
        return exceptionInfo.getErrorMessages();
    }


}
