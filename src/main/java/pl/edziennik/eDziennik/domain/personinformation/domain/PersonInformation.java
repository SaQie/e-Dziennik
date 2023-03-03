package pl.edziennik.eDziennik.domain.personinformation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.server.basics.entity.AbstractEntity;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PersonInformation extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_information_id_seq")
    @SequenceGenerator(name = "person_information_id_seq", sequenceName = "person_information_id_seq", allocationSize = 1)
    private Long id;

    private String firstName;
    private String lastName;
    private String fullName;
    private String phoneNumber;
    private String pesel;

    @Override
    public boolean isNew() {
        return (id == null);
    }

    @PrePersist
    public void setFullName() {
        this.fullName = firstName + " " + lastName;
    }

}
