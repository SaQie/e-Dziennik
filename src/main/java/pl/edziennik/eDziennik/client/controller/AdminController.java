package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminResponseApiDto;
import pl.edziennik.eDziennik.server.admin.services.AdminService;
import pl.edziennik.eDziennik.server.basics.ApiResponse;
import pl.edziennik.eDziennik.server.basics.ApiResponseCreator;

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
    public ApiResponse<?> getAdminList() {
        List<AdminResponseApiDto> responseApiDto = service.getAdminList();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

}
