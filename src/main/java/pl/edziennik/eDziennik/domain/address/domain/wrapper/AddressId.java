package pl.edziennik.eDziennik.domain.address.domain.wrapper;

public record AddressId(
        Long id
) {
    public static AddressId wrap(Long id){
        return new AddressId(id);
    }
}
