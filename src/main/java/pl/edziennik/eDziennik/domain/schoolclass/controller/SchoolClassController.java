package pl.edziennik.eDziennik.domain.schoolclass.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.services.SchoolClassService;
import pl.edziennik.eDziennik.server.basic.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basic.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.server.basic.page.PageDto;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/schoolclasses/")
public class SchoolClassController {

    private final SchoolClassService service;


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new school class")
    public ApiResponse<?> createSchoolClass(@RequestBody @Valid SchoolClassRequestApiDto requestApiDto) {
        SchoolClassResponseApiDto responseApiDto = service.createSchoolClass(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.schoolClassId().id())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri);
    }

    @GetMapping("{schoolClassId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get specific school class",
            description = "Returns information about speceific school class")
    public ApiResponse<?> findSchoolClassById(@PathVariable SchoolClassId schoolClassId) {
        SchoolClassResponseApiDto responseApiDto = service.findSchoolClassById(schoolClassId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get school class list",
            description = "Returns list of all school classes")
    public ApiResponse<?> findAllSchoolClasses(@RequestParam(required = false) SchoolId schoolId, Pageable pageable) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        PageDto<SchoolClassResponseApiDto> responseApiDtos;
        if (schoolId == null) {
            responseApiDtos = service.findAllSchoolClasses(pageable);
            return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDtos, uri);
        }
        responseApiDtos = service.findSchoolClassesBySchoolId(pageable, schoolId);
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDtos, uri);
    }

    @DeleteMapping("{schoolClassId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete school class")
    public ApiResponse<?> deleteSchoolClassById(@PathVariable SchoolClassId schoolClassId) {
        service.deleteSchoolClassById(schoolClassId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK, "School class deleted successfully", uri);
    }

    @PutMapping("{schoolClassId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update school class",
            description = "This method will update specific school class or create new if not exists")
    public ApiResponse<?> updateSchoolClass(@PathVariable SchoolClassId schoolClassId, @RequestBody SchoolClassRequestApiDto dto) {
        SchoolClassResponseApiDto responseApiDto = service.updateSchoolClass(schoolClassId, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/schoolclasses")
                .path("{id}")
                .buildAndExpand(responseApiDto.schoolClassId().id())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.PUT, HttpStatus.OK, responseApiDto, uri);
    }


}
