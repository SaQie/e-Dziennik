package pl.edziennik.eDziennik.domain.admin.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.domain.admin.domain.wrapper.AdminId;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.dto.AdminResponseApiDto;
import pl.edziennik.eDziennik.domain.admin.services.AdminService;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.server.basics.page.PageDto;

import java.net.URI;

@RequestMapping("/api/v1/admins")
@RestController
@AllArgsConstructor
public class AdminController {

    private final AdminService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "Add administration account",
//            description = "Add administration account, that will have all permissions to manage application")
    public ApiResponse<?> createAdminAccount(@RequestBody @Valid AdminRequestApiDto requestApiDto) {
        AdminResponseApiDto responseApiDto = service.createNewAdminAccount(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.id())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Get admin list",
//            description = "Returns list of admins")
    public ApiResponse<PageDto<AdminResponseApiDto>> getAdminList(Pageable pageable) {
        PageDto<AdminResponseApiDto> responseApiDto = service.getAdminList(pageable);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping("/{adminId}")
    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Get specific admin",
//            description = "Returns information about specific admin")
    public ApiResponse<AdminResponseApiDto> getAdmin(@PathVariable AdminId adminId) {
        AdminResponseApiDto responseApiDto = service.getAdminById(adminId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @DeleteMapping("/{adminId}")
    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Delete admin")
    public ApiResponse<?> deleteAdminById(@PathVariable AdminId adminId) {
        service.deleteAdminById(adminId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK, "Admin deleted successfully", uri);
    }

    @PutMapping("/{adminId}")
    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Update admin",
//            description = "This method will update specific Admin or create new if not exists")
    public ApiResponse<AdminResponseApiDto> updateAdmin(@RequestBody @Valid AdminRequestApiDto dto, @PathVariable AdminId adminId) {
        AdminResponseApiDto responseApiDto = service.updateAdmin(dto, adminId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.PUT, HttpStatus.OK, responseApiDto, uri);
    }
}
