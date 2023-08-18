package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.common.view.groovy.GroovyScriptResultView;
import pl.edziennik.domain.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
public class GroovyScriptExecutorProjectionTest extends BaseIntegrationTest {

    @Test
    public void shouldReturnGroovyScriptResultView() {
        // given
        createAdmin("Test", "Test@example.com");
        User user = userQueryRepository.getByUsername(Username.of("Test"));

        GroovyScriptId groovyScriptId = execSimpleGroovyScript(user);
        // when
        GroovyScriptResultView view = groovyScriptResultQueryRepository.getGroovyScriptResultView(groovyScriptId);

        // then
        assertEquals(groovyScriptId, view.groovyScriptId());
        assertEquals(Name.of("SUCCESS"), view.status());
    }

}
