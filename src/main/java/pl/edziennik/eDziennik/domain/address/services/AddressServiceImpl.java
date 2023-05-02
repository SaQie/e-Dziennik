package pl.edziennik.eDziennik.domain.address.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.address.domain.Address;
import pl.edziennik.eDziennik.domain.address.domain.wrapper.AddressId;
import pl.edziennik.eDziennik.domain.address.repository.AddressRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
class AddressServiceImpl implements AddressService{

    private final AddressRepository repository;


    @Override
    @Transactional
    public void update(final AddressId addressId, final Address addressSource) {
        Optional<Address> addressOptional = repository.findById(addressId);
        if (addressOptional.isPresent()){
            Address addressToEdit = addressOptional.get();
            addressToEdit.setAddress(addressSource.getAddress());
            addressToEdit.setCity(addressSource.getCity());
            addressToEdit.setPostalCode(addressSource.getPostalCode());
        }

    }
}
