package pl.edziennik.eDziennik.infrastructure.repository.command.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.address.domain.Address;
import pl.edziennik.eDziennik.domain.address.domain.wrapper.AddressId;

@Repository
public interface AddressCommandRepository extends JpaRepository<Address, AddressId> {

}
