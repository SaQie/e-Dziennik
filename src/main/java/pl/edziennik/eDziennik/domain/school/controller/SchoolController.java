package pl.edziennik.eDziennik.domain.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.domain.school.services.SchoolService;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/schools")
@AllArgsConstructor
@SuppressWarnings("rawtypes")
class SchoolController {

    private final SchoolService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new school")
    public ApiResponse createNewSchool(@Valid @RequestBody SchoolRequestApiDto requestApiDto) {
        SchoolResponseApiDto responseApiDto = service.createNewSchool(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get school list",
            description = "Returns list of all schools")
    public ApiResponse<Page<SchoolResponseApiDto>> findAllSchools(Pageable pageable) {
        Page<SchoolResponseApiDto> responseApiDtos = service.findAllSchools(pageable);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDtos, uri);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get specific school",
            description = "Returns information about specific school")
    public ApiResponse findSchoolById(@PathVariable Long id) {
        SchoolResponseApiDto responseApiDto = service.findSchoolById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete school")
    public ApiResponse deleteSchoolById(@PathVariable Long id) {
        service.deleteSchoolById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK, "School deleted successfully", uri);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update school",
            description = "This method will update specific school or create new if not exists")
    public ApiResponse updateSchool(@PathVariable Long id, @RequestBody SchoolRequestApiDto requestApiDto) {
        SchoolResponseApiDto responseApiDto = service.updateSchool(id, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/schools")
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.PUT, HttpStatus.OK, responseApiDto, uri);
    }


}
