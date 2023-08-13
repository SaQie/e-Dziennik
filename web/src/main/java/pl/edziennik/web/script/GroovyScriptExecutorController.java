package pl.edziennik.web.script;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.groovy.ExecuteGroovyScriptCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.ScriptContent;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/scripts")
public class GroovyScriptExecutorController {

    private final Dispatcher dispatcher;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> executeGroovyScript(@RequestBody ExecuteGroovyScriptCommand command) {
        OperationResult operationResult = dispatcher.dispatch(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(operationResult.identifier().id())
                .toUri();

        return ResponseEntity.ok().location(location).build();
    }

    @PostMapping("/file")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> executeGroovyScriptFile(@RequestPart("file") MultipartFile file) {
        ExecuteGroovyScriptCommand command = new ExecuteGroovyScriptCommand(ScriptContent.of(file));
        OperationResult operationResult = dispatcher.dispatch(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(operationResult.identifier().id())
                .toUri();

        return ResponseEntity.ok().location(location).build();
    }

}
