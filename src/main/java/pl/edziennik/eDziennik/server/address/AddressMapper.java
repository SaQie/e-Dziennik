package pl.edziennik.eDziennik.server.address;

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
