package pl.edziennik.eDziennik.domain.address.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.address.domain.Address;
import pl.edziennik.eDziennik.domain.address.repository.AddressRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
class AddressServiceImpl implements AddressService{

    private final AddressRepository repository;


    @Override
    @Transactional
    public void update(Long id, Address addressSource) {
        Optional<Address> addressOptional = repository.findById(id);
        if (addressOptional.isPresent()){
            Address addressToEdit = addressOptional.get();
            addressToEdit.setAddress(addressSource.getAddress());
            addressToEdit.setCity(addressSource.getCity());
            addressToEdit.setPostalCode(addressSource.getPostalCode());
        }

    }
}
