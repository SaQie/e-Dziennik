package pl.edziennik.domain.groovy;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.common.valueobject.id.GroovyScriptStatusId;
import pl.edziennik.common.valueobject.vo.ScriptContent;
import pl.edziennik.domain.user.User;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class GroovyScript {

    @EmbeddedId
    private GroovyScriptId groovyScriptId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Embedded
    @Lob
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(columnDefinition = "TEXT", name = "content", nullable = false))
    })
    private ScriptContent scriptContent;

    private LocalDateTime startTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groovy_script_status_id", referencedColumnName = "id", nullable = false)
    private GroovyScriptStatus groovyScriptStatus;

    @Version
    private Long version;

    @Builder
    public static GroovyScript of(GroovyScriptId groovyScriptId, User user, ScriptContent scriptContent, GroovyScriptStatus scriptStatus) {
        GroovyScript groovyScript = new GroovyScript();

        groovyScript.groovyScriptStatus = scriptStatus;
        groovyScript.user = user;
        groovyScript.scriptContent = scriptContent;
        groovyScript.startTime = LocalDateTime.now();
        groovyScript.groovyScriptId = groovyScriptId;

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
