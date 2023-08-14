package pl.edziennik.domain.groovy;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.GroovyScriptStatusId;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class GroovyScriptStatus {

    @EmbeddedId
    private GroovyScriptStatusId groovyScriptStatusId = GroovyScriptStatusId.create();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false))
    })
    private Name name;

    @Version
    private Long version;

}
