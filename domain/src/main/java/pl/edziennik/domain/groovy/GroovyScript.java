package pl.edziennik.domain.groovy;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.ScriptContent;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.common.valueobject.id.GroovyScriptStatusId;
import pl.edziennik.domain.user.User;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class GroovyScript {

    @EmbeddedId
    private GroovyScriptId groovyScriptId = GroovyScriptId.create();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Embedded
    @Lob
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "content", nullable = false))
    })
    private ScriptContent scriptContent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groovy_script_status_id", referencedColumnName = "id", nullable = false)
    private GroovyScriptStatus groovyScriptStatus;

    @Builder
    public static GroovyScript of(User user, ScriptContent scriptContent, GroovyScriptStatus scriptStatus) {
        GroovyScript groovyScript = new GroovyScript();

        groovyScript.groovyScriptStatus = scriptStatus;
        groovyScript.user = user;
        groovyScript.scriptContent = scriptContent;

        return groovyScript;
    }

    public void changeStatus(GroovyScriptStatus newStatus) {
        GroovyScriptStatusId oldStatusId = groovyScriptStatus.groovyScriptStatusId();
        if (newStatus.groovyScriptStatusId().equals(oldStatusId)) {
            // do nothing if status is already set
            return;
        }
        groovyScriptStatus = newStatus;
    }

}
