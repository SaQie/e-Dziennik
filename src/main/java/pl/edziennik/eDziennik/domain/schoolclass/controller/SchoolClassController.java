package pl.edziennik.eDziennik.domain.schoolclass.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.services.SchoolClassService;
import pl.edziennik.eDziennik.server.basics.dto.Page;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;
import pl.edziennik.eDziennik.server.basics.handler.Pageable;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/schoolclasses")
public class SchoolClassController {

    private final SchoolClassService service;


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new school class")
    public ApiResponse<?> createSchoolClass(@RequestBody @Valid SchoolClassRequestApiDto requestApiDto) {
        SchoolClassResponseApiDto responseApiDto = service.createSchoolClass(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get specific school class",
            description = "Returns information about speceific school class")
    public ApiResponse<?> findSchoolClassById(@PathVariable Long id) {
        SchoolClassResponseApiDto responseApiDto = service.findSchoolClassById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get school class list",
            description = "Returns list of all school classes")
    public ApiResponse<?> findAllSchoolClasses(@RequestParam(required = false) Long schoolId, @Pageable PageRequest pageRequest) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        if (schoolId == null) {
            Page<List<SchoolClassResponseApiDto>> responseApiDtos = service.findAllSchoolClasses(pageRequest);
            return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDtos, uri);
        }
        List<SchoolClassResponseApiDto> responseApiDtos = service.findSchoolClassesBySchoolId(schoolId);
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDtos, uri);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete school class")
    public ApiResponse<?> deleteSchoolClassById(@PathVariable Long id) {
        service.deleteSchoolClassById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK, "School class deleted successfully", uri);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update school class",
            description = "This method will update specific school class or create new if not exists")
    public ApiResponse<?> updateSchoolClass(@PathVariable Long id, @RequestBody SchoolClassRequestApiDto dto) {
        SchoolClassResponseApiDto responseApiDto = service.updateSchoolClass(id, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/schoolclasses")
                .path("{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.PUT, HttpStatus.OK, responseApiDto, uri);
    }


}
