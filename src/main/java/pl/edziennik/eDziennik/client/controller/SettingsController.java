package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.basics.ApiResponse;
import pl.edziennik.eDziennik.server.basics.ApiResponseCreator;
import pl.edziennik.eDziennik.server.settings.domain.SettingsDto;
import pl.edziennik.eDziennik.server.settings.services.SettingsService;
import pl.edziennik.eDziennik.server.settings.domain.SettingsValue;

import java.net.URI;
import java.util.List;

@RestController
@SuppressWarnings("rawtypes")
@AllArgsConstructor
@RequestMapping("/api/settings")
public class SettingsController {

    private final SettingsService service;

    @GetMapping
    public ResponseEntity<ApiResponse> findAllSettings(){
        List<SettingsDto> settings = service.getAllSettings();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, settings, uri));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateSettings(@RequestBody SettingsValue value, @PathVariable Long id){
        service.updateSettings(id, value);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponseCreator.buildApiResponse(HttpMethod.PATCH, HttpStatus.OK,"Settings updated sucessfully", uri));
    }

}
