package pl.edziennik.eDziennik.domain.personinformation.domain;

import lombok.*;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.PersonInformationId;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PersonInformation{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_information_id_seq")
    @SequenceGenerator(name = "person_information_id_seq", sequenceName = "person_information_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    private String firstName;
    private String lastName;
    private String fullName;
    private String phoneNumber;
    private String pesel;

    public PersonInformationId getPersonInformationId(){
        return PersonInformationId.wrap(id);
    }


    @PrePersist
    @PreUpdate
    public void setFullName() {
        this.fullName = firstName + " " + lastName;
    }

}
