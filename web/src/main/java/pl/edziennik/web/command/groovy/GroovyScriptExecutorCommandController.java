package pl.edziennik.web.command.groovy;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.groovy.ExecuteGroovyScriptCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.common.valueobject.vo.ScriptContent;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/scripts")
@Tag(name = "Groovy-script API")
public class GroovyScriptExecutorCommandController {

    private final Dispatcher dispatcher;

    @Operation(summary = "Execute groovy script",
            description = "This API endpoint executes given in body groovy script, and saves an attempt to execute if fails, " +
                    "or a result of groovy script of no error. Check location header after execute to see result")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> executeGroovyScript(@RequestBody ExecuteGroovyScriptCommand command) {
        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/scripts/results/{groovyScriptId}")
                .buildAndExpand(command.groovyScriptId().id())
                .toUri();

        return ResponseEntity.ok().location(location).build();
    }

    @Operation(summary = "Execute groovy script from file",
            description = "This API endpoint executes given in file groovy script, and saves an attempt to execute if fails, " +
                    "or a result of groovy script of no error. Check location header after execute to see result")
    @PostMapping("/file")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> executeGroovyScriptFile(@RequestPart("file") MultipartFile file) {
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(ScriptContent.of(file));

        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/scripts/results/{groovyScriptId}")
                .buildAndExpand(command.groovyScriptId().id())
                .toUri();

        return ResponseEntity.ok().location(location).build();
    }

}
