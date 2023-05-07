package pl.edziennik.infrastructure.validator.errorcode;

public enum ErrorCode {

    OBJECT_NOT_EXISTS(1000),
    OBJECT_ALREADY_EXISTS(1010),
    SCHOOL_CLASS_IS_NOT_PART_OF_SCHOOL(1020);

    private final Integer errorCode;

    public Integer errorCode() {
        return errorCode;
    }

    ErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }


    }
