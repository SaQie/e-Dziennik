package pl.edziennik.eDziennik.domain.personinformation.domain.wrapper;

public record PersonInformationId(
        Long id
) {

    public static PersonInformationId wrap(Long id) {
        return new PersonInformationId(id);
    }

}
