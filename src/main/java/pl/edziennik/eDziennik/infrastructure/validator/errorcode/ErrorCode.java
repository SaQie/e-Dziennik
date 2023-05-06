package pl.edziennik.eDziennik.infrastructure.validator.errorcode;

public enum ErrorCode {

    OBJECT_NOT_EXISTS(1000);

    private Integer errorCode;

    public Integer errorCode() {
        return errorCode;
    }

    ErrorCode(Integer code) {
    }


}
