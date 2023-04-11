package pl.edziennik.eDziennik.domain.address.services;

import pl.edziennik.eDziennik.domain.address.domain.Address;
import pl.edziennik.eDziennik.domain.address.domain.wrapper.AddressId;

public interface AddressService {

    void update(final AddressId addressId, Address address);

}
