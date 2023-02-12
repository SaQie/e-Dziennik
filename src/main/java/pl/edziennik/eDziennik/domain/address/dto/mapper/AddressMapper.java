package pl.edziennik.eDziennik.domain.address.dto.mapper;

import pl.edziennik.eDziennik.domain.address.domain.Address;

public class AddressMapper {

    private AddressMapper() {
    }

    public static Address mapToAddress(String address, String city, String postalCode){
        Address addressEntity = new Address();
        addressEntity.setAddress(address);
        addressEntity.setCity(city);
        addressEntity.setPostalCode(postalCode);
        return addressEntity;
    }
}
