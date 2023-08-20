package pl.edziennik.application.integration.groovy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.groovy.ExecuteGroovyScriptCommand;
import pl.edziennik.common.valueobject.vo.ScriptContent;
import pl.edziennik.common.valueobject.id.GroovyScriptId;
import pl.edziennik.common.valueobject.id.GroovyScriptStatusId;
import pl.edziennik.domain.groovy.GroovyScriptStatus;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DirtiesContext
public class GroovyScriptExecutorTest extends BaseIntegrationTest {

    private static final String TEST_USER = "Test";

    @BeforeEach
    public void createTestUser() {
        createAdmin(TEST_USER, "Test@example.com");
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldExecuteSimpleGroovyScriptAndSaveResultToDatabase() {
        // given
        String script = """
                        def random = new Random();
                         def randomNumber = random.nextInt(10) + 1;
                        return new ScriptResult(randomNumber);
                """;

        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        dispatcher.dispatch(command);

        // then
        assertOneRowExists("groovy_script");
        assertOneRowExists("groovy_script_result");
        GroovyScriptId groovyScriptId = getGroovyScriptId();
        checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.SUCCESS);
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldExecuteGroovyScriptAndSaveWithError() {
        // given
        String script = """
                        def random = new Random();
                         def randomNumber = random.nextInt(10) + 1;
                        return new ScriptResult(randomNumber);
                        System.exit(0);        
                """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains not allowed constraints");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfScriptContainsDisallowedImport() {
        // given
        String script = "import java.net.*";
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains not allowed imports");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfScriptContainsSystemExitFirstScenario() {
        // given
        String script = """
                    def c = System;
                    c.exit(-1);
                """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains not allowed imports");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfScriptContainsSystemExitSecondScenario() {
        // given
        String script = "System.exit(0);";
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains not allowed imports");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfScriptContainsSystemExitThirdScenario() {
        // given
        String script = "    ((Object)System).exit(-1)\n"
                + "Class.forName('java.lang.System').exit(-1)\n"
                + "('java.lang.System' as Class).exit(-1)\n"
                + "\n" + "import static java.lang.System.exit\n"
                + "    exit(-1)";
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains not allowed imports");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfScriptContainsSystemExitFourthScenario() {
        // given
        String script = """
                    void foo(){
                        def c = System;
                        c.exit(-1);
                    }
                    
                    foo();
                """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains not allowed imports");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }


    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfScriptContainsAnnotations() {
        // given
        String script = """
                    @TypeChecked
                    def test(){
                        println "Test";
                    }
                    
                    return new ScriptResult("Test");
                """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains not allowed imports");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfScriptContainsFileReadersFirstScenario() {
        // given
        String script =
                """
                            File file = new File("test.txt");
                        """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains not allowed file readers");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfScriptContainsFileReadersSecondScenario() {
        // given
        String script =
                """
                            Scanner scanner = new Scanner("test.txt");
                        """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains not allowed file readers");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }


    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfScriptContainsFileReadersThirdScenario() {
        // given
        String script =
                """
                            BufferedReader reader = new BufferedReader(new FileReader("test.txt"));
                        """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains not allowed file readers");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfScriptContainsFileReadersFourthScenario() {
        // given
        String script =
                """
                            FileInputStream reader = new FileInputStream("test.txt");
                        """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains not allowed file readers");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }


    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfScriptDoesNotReturnScriptResultObject() {
        // given
        String script =
                """
                         def random = new Random();
                         def randomNumber = random.nextInt(10) + 1;
                        """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script doesn't return script result object");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfScriptExecutionTimeoutExceeded() {
        // Limit is set in application.properties file (groovy.execution.timeout)
        // given
        String script =
                """
                         while(true){
                            println "Test"
                         }
                        """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains infinite loops");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfSomeoneWillTryToThrowExceptionInsideScript() {
        // given
        String script =
                """
                         println "Test";
                         throw new Exception();
                        """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains exceptions");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfSomeoneWillTryToUseAssertInScript() {
        // given
        String script =
                """
                    def num = -3;
                    assert num >= 0 : "Number must be non-negative";
                        """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains assertions");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldThrowExceptionIfSomeoneWillTryToUseTryCatchInsideScript() {
        // given
        String script =
                """
                    def num = -3;
                    try{
                        println "Test";
                    }catch(Exception e){
                        println "Test";
                    }
                        """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        try {
            dispatcher.dispatch(command);
            fail("Should throw exception if script contains try catch");
        } catch (BusinessException e) {
            // then
            assertOneRowExists("groovy_script");
            assertOneRowExists("groovy_script_result");
            GroovyScriptId groovyScriptId = getGroovyScriptId();
            checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.ERROR);
        }
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldSaveIfScriptContainsMethods() {
        // given
        String script = """
                        def test() {
                            Random random = new Random()
                            return random.nextInt()
                        }
                                        
                        def randomNumber = test()
                                        
                        return new ScriptResult(randomNumber);
                """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        dispatcher.dispatch(command);

        // then
        assertOneRowExists("groovy_script");
        assertOneRowExists("groovy_script_result");
        GroovyScriptId groovyScriptId = getGroovyScriptId();
        checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.SUCCESS);
    }

    @Test
    @WithMockUser(username = TEST_USER)
    public void shouldSaveIfScriptContainsJavaCollections() {
        // given
        String script = """
                        import java.util.List;
                        List<String> values = new ArrayList<>();
                        values.add("testt");
                        values.add("test");
                        return new ScriptResult(values);
                """;
        ScriptContent scriptContent = ScriptContent.of(script);
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(scriptContent);

        // when
        dispatcher.dispatch(command);

        // then
        assertOneRowExists("groovy_script");
        assertOneRowExists("groovy_script_result");
        GroovyScriptId groovyScriptId = getGroovyScriptId();
        checkGroovyScriptStatus(groovyScriptId, GroovyScriptStatusId.PredefinedRow.SUCCESS);
    }


    private void checkGroovyScriptStatus(GroovyScriptId groovyScriptId, GroovyScriptStatusId groovyScriptStatusId) {
        GroovyScriptStatus groovyScriptStatus = groovyScriptStatusQueryRepository.getByGroovyScriptId(groovyScriptId);
        assertEquals(groovyScriptStatus.groovyScriptStatusId(), groovyScriptStatusId);
    }

    private GroovyScriptId getGroovyScriptId() {
        UUID uuid = jdbcTemplate.queryForObject("SELECT gs.id FROM groovy_script gs", new MapSqlParameterSource(), UUID.class);
        return GroovyScriptId.of(uuid);
    }

}
