package pl.edziennik.infrastructure.query.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.address.AddressId;

@Repository
public interface AddressQueryRepository extends JpaRepository<Address, AddressId> {

}
