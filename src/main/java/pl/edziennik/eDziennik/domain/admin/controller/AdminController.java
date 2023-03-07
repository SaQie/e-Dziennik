package pl.edziennik.eDziennik.domain.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.AdminResponseApiDto;
import pl.edziennik.eDziennik.domain.admin.services.AdminService;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.server.basics.dto.Page;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;
import pl.edziennik.eDziennik.server.basics.handler.Pageable;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequestMapping("/api/admins")
@RestController
@AllArgsConstructor
public class AdminController {

    private final AdminService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add administration account",
            description = "Add administration account, that will have all permissions to manage application")
    public ApiResponse<?> createAdminAccount(@RequestBody @Valid AdminRequestApiDto requestApiDto) {
        AdminResponseApiDto responseApiDto = service.createNewAdminAccount(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get admin list",
            description = "Returns list of admins")
    public ApiResponse<Page<List<AdminResponseApiDto>>> getAdminList(@Pageable PageRequest pageRequest) {
        Page<List<AdminResponseApiDto>> responseApiDto = service.getAdminList(pageRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get specific admin",
            description = "Returns information about specific admin")
    public ApiResponse<AdminResponseApiDto> getAdmin(@PathVariable Long id) {
        AdminResponseApiDto responseApiDto = service.getAdminById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete admin")
    public ApiResponse<?> deleteAdminById(@PathVariable Long id) {
        service.deleteAdminById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK, "Admin deleted successfully", uri);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update admin",
            description = "This method will update specific Admin or create new if not exists")
    public ApiResponse<AdminResponseApiDto> updateAdmin(@RequestBody @Valid AdminRequestApiDto dto, @PathVariable Long id) {
        AdminResponseApiDto responseApiDto = service.updateAdmin(dto, id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.PUT, HttpStatus.OK, responseApiDto, uri);
    }
}
