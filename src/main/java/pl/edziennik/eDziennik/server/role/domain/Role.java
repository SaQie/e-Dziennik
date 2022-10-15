package pl.edziennik.eDziennik.server.role.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
public class Role{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
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
