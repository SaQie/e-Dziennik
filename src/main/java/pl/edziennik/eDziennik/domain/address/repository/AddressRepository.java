package pl.edziennik.eDziennik.domain.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.address.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
