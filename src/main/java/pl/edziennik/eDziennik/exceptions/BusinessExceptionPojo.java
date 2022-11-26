package pl.edziennik.eDziennik.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class BusinessExceptionPojo {

    private String fieldName;
    private List<String> errorMessages;

    public BusinessExceptionPojo(String fieldName, List<String> errorMessages) {
        this.fieldName = fieldName;
        this.errorMessages = errorMessages;
    }
}
