package pl.edziennik.eDziennik.server.role.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @OneToMany(mappedBy = "role")
    private Collection<Teacher> teachers = new ArrayList<>();

    public enum RoleConst{
        ROLE_ADMIN(1L),
        ROLE_MODERATOR(2L),
        ROLE_TEACHER(3L);

        private Long id;
        RoleConst(Long id) {
            this.id = id;
        }

        public Long getId(){
            return id;
        }
    }

}
