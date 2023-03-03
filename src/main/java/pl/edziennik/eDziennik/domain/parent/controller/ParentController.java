package pl.edziennik.eDziennik.domain.parent.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentResponseApiDto;
import pl.edziennik.eDziennik.domain.parent.service.ParentService;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/parents")
@AllArgsConstructor
class ParentController {

    private final ParentService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new parent")
    public ApiResponse<?> createNewParent(@Valid @RequestBody ParentRequestApiDto requestApiDto) {
        ParentResponseApiDto responseApiDto = service.register(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get parents list",
            description = "Returns list of all parents")
    public ApiResponse<?> findAllParents() {
        List<ParentResponseApiDto> responseApiDtos = service.findAll();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDtos, uri);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get specific parent",
            description = "Returns information about specific parent")
    public ApiResponse<?> findParentById(@PathVariable Long id) {
        ParentResponseApiDto responseApiDto = service.findById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete parent")
    public ApiResponse<?> deleteSchoolById(@PathVariable Long id) {
        service.deleteById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK, "Parent deleted successfully", uri);
    }


}