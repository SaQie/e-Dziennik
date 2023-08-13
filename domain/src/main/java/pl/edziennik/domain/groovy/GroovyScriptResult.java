package pl.edziennik.domain.groovy;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.attribute.ScriptResultAttributeConverter;
import pl.edziennik.common.valueobject.ScriptResult;
import pl.edziennik.common.valueobject.id.GroovyScriptResultId;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class GroovyScriptResult {

    @EmbeddedId
    private GroovyScriptResultId groovyScriptResultId = GroovyScriptResultId.create();

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

    @Builder
    public static GroovyScriptResult of(ScriptResult scriptResult, GroovyScript groovyScript) {
        GroovyScriptResult groovyScriptResult = new GroovyScriptResult();

        groovyScriptResult.groovyScript = groovyScript;
        groovyScriptResult.scriptResult = scriptResult;

        return groovyScriptResult;
    }

}
