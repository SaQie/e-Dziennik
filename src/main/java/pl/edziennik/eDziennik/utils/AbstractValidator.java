package pl.edziennik.eDziennik.utils;

public interface AbstractValidator <T extends AbstractDto>{

    void validate(T dtoToValidate);

}
