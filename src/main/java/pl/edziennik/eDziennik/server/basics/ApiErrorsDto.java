package pl.edziennik.eDziennik.server.basics;

import lombok.Data;

@Data
public class ApiErrorsDto {

    private String field;
    private String cause;

}
