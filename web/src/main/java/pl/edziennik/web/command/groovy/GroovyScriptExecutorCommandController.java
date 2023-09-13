package pl.edziennik.web.command.groovy;

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
public class GroovyScriptExecutorCommandController {

    private final Dispatcher dispatcher;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody()
    public ResponseEntity<Void> executeGroovyScript(@RequestBody ExecuteGroovyScriptCommand command) {
        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/scripts/results/{groovyScriptId}")
                .buildAndExpand(command.groovyScriptId().id())
                .toUri();

        return ResponseEntity.ok().location(location).build();
    }

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
