package pl.edziennik.infrastructure.command.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.address.AddressId;

@Repository
public interface AddressCommandRepository extends JpaRepository<Address, AddressId> {

}
