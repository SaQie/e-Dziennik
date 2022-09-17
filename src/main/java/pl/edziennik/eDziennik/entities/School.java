package pl.edziennik.eDziennik.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String adress;
    private String postalCode;
    private String city;
    private String nip;
    private String regon;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "school_level_id")
    private SchoolLevel schoolLevel;
}
