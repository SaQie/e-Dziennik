package pl.edziennik.domain.address;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.City;
import pl.edziennik.common.valueobject.PostalCode;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@IdClass(AddressId.class)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_seq")
    @SequenceGenerator(name = "address_id_seq", sequenceName = "address_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "address"))
    })
    private pl.edziennik.common.valueobject.Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "city"))
    })
    private City city;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "postal_code"))
    })
    private PostalCode postalCode;

    public AddressId getAddressId() {
        return AddressId.wrap(id);
    }

    public Address(pl.edziennik.common.valueobject.Address address, City city, PostalCode postalCode) {
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }
}
