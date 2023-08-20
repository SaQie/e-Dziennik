package pl.edziennik.application.integration.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.query.groovy.byscript.GetGroovyScriptExecResultQuery;
import pl.edziennik.application.query.groovy.byuser.GetGroovyScriptExecResultByUserQuery;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.common.valueobject.vo.Username;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.groovy.GroovyScriptResultView;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
public class GroovyScriptExecResultQueryIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldThrowExceptionIfGroovyScriptNotExists() {
        // given
        GetGroovyScriptExecResultQuery query = new GetGroovyScriptExecResultQuery(GroovyScriptId.create());

        try {
            // when
            dispatcher.dispatch(query);
            fail("Should throw exception if groovy script id not exsits");
        } catch (BusinessException e) {
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertEquals(errors.get(0).field(), GetGroovyScriptExecResultQuery.GROOVY_SCRIPT_ID);
            assertEquals(errors.get(0).errorCode(), ErrorCode.OBJECT_NOT_EXISTS.errorCode());
        }
    }

    @Test
    public void shouldReturnGroovyScriptExecResultDto() {
        // given
        createAdmin("Test", "Test@example.com");
        User user = userCommandRepository.getByUsername(Username.of("Test"));

        GroovyScriptId groovyScriptId = execSimpleGroovyScript(user);

        GetGroovyScriptExecResultQuery query = new GetGroovyScriptExecResultQuery(groovyScriptId);

        // when
        GroovyScriptResultView view = dispatcher.dispatch(query);

        // then
        Assertions.assertNotNull(view);
    }

    @Test
    public void shouldReturnGroovyScriptExecResultDtoByUser() {
        // given
        createAdmin("Test", "Test@example.com");
        User user = userCommandRepository.getByUsername(Username.of("Test"));

        execSimpleGroovyScript(user);

        GetGroovyScriptExecResultByUserQuery query = new GetGroovyScriptExecResultByUserQuery(user.userId(), Pageable.unpaged());

        // when
        PageView<GroovyScriptResultView> view = dispatcher.dispatch(query);

        // then
        Assertions.assertNotNull(view);
        List<GroovyScriptResultView> content = view.getContent();
        assertEquals(1, content.size());
    }

}
