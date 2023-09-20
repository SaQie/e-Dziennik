package pl.edziennik.web.command.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.application.command.admin.create.CreateAdminCommand;
import pl.edziennik.application.common.dispatcher.Dispatcher;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admins")
@Tag(name = "Admin API")
public class AdminCommandController {

    private final Dispatcher dispatcher;

    @Operation(summary = "Create a new admin account",
            description = "This API endpoint creates a new admin account, keep in mind that, only one admin account can exist in the system")
    @PostMapping()
    public ResponseEntity<Void> createAdmin(@RequestBody @Valid CreateAdminCommand command) {
        dispatcher.run(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(command.adminId().id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
