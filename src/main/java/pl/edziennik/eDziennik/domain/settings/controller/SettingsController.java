package pl.edziennik.eDziennik.domain.settings.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.server.basics.dto.Page;
import pl.edziennik.eDziennik.domain.school.dao.SchoolDao;
import pl.edziennik.eDziennik.domain.school.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.domain.school.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsDto;
import pl.edziennik.eDziennik.domain.settings.services.SettingsService;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsValue;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/settings")
public class SettingsController {

    private final SettingsService service;
    private ResourceCreator resourceCreator;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get settings list",
            description = "Returns list of all configurations with values")
    public ApiResponse<?> findAllSettings() {
        List<SettingsDto> settings = service.getAllSettings();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, settings, uri);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update setting",
            description = "This method will update specific setting")
    public ApiResponse<?> updateSettings(@PathVariable Long id, @RequestBody SettingsValue value) {
        service.updateSettings(id, value);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.PATCH, HttpStatus.OK, resourceCreator.of("settings.updated.successfully"), uri);
    }

}
