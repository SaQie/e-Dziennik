package pl.edziennik.eDziennik.server.role.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.basics.AbstractEntity;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
public class Role extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean isNew() {
        return (id == null);
    }

    public Role(String name) {
        this.name = name;
    }

    public enum RoleConst{
        ROLE_ADMIN(1L),
        ROLE_MODERATOR(2L),
        ROLE_TEACHER(3L),
        ROLE_STUDENT(4L);

        private Long id;
        RoleConst(Long id) {
            this.id = id;
        }

        public Long getId(){
            return id;
        }
    }

}
