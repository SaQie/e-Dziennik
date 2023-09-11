package pl.edziennik.domain.groovy;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.attribute.ScriptResultAttributeConverter;
import pl.edziennik.common.valueobject.id.GroovyScriptResultId;
import pl.edziennik.common.valueobject.vo.GroovyScriptExecTime;
import pl.edziennik.common.valueobject.vo.ScriptResult;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class GroovyScriptResult {

    @EmbeddedId
    private GroovyScriptResultId groovyScriptResultId;

    @Embedded
    @Lob
    @Convert(converter = ScriptResultAttributeConverter.class, attributeName = "value")
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "result", nullable = false))
    })
    private ScriptResult scriptResult;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "groovy_script_id", referencedColumnName = "id", nullable = false)
    private GroovyScript groovyScript;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "exec_time", nullable = false))
    })
    private GroovyScriptExecTime execTime;

    @Version
    private Long version;

    @Builder
    public static GroovyScriptResult of(GroovyScriptResultId groovyScriptResultId, ScriptResult scriptResult, GroovyScript groovyScript, GroovyScriptExecTime execTime) {
        GroovyScriptResult groovyScriptResult = new GroovyScriptResult();

        groovyScriptResult.groovyScript = groovyScript;
        groovyScriptResult.scriptResult = scriptResult;
        groovyScriptResult.execTime = execTime;
        groovyScriptResult.groovyScriptResultId = groovyScriptResultId;

        return groovyScriptResult;
    }

}
