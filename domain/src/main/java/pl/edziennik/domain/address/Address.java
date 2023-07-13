package pl.edziennik.domain.address;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.City;
import pl.edziennik.common.valueobject.PostalCode;
import pl.edziennik.common.valueobject.id.AddressId;


@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class Address {

    @EmbeddedId
    private AddressId addressId = AddressId.create();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "address", nullable = false))
    })
    private pl.edziennik.common.valueobject.Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "city", nullable = false))
    })
    private City city;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "postal_code", nullable = false))
    })
    private PostalCode postalCode;

    @Version
    private Long version;


    @Builder
    public static Address of(pl.edziennik.common.valueobject.Address address, City city, PostalCode postalCode) {
        Address addressEntity = new Address();

        addressEntity.city = city;
        addressEntity.address = address;
        addressEntity.postalCode = postalCode;

        return addressEntity;
    }

    public void changeCity(City city) {
        this.city = city;
    }

    public void changePostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }

    public void changeAddress(pl.edziennik.common.valueobject.Address address) {
        this.address = address;
    }
}
