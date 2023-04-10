package pl.edziennik.eDziennik.domain.role.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Role{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Role(String name) {
        this.name = name;
    }

    public enum RoleConst {
        ROLE_ADMIN(1L),
        ROLE_TEACHER(2L),
        ROLE_STUDENT(3L),
        ROLE_PARENT(4L);

        private final Long id;

        RoleConst(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }

}
