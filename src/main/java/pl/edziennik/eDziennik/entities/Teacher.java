package pl.edziennik.eDziennik.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Teacher extends BasicUser{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "teacher")
    private Set<Subject> subjects = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private School school;

    public void addSubject(Subject subject){
        subjects.add(subject);
        subject.setTeacher(this);
    }

}
