package pl.edziennik.eDziennik.server.basics;

public interface AbstractValidator <T extends AbstractDto>{

    void validate(T dto);

}
