package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import pl.edziennik.eDziennik.server.basics.ApiResponse;
import pl.edziennik.eDziennik.server.basics.ApiResponseCreator;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassAssignSubjectRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.server.schoolclass.services.SchoolClassService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/schoolclasses")
@SuppressWarnings("rawtypes")
public class SchoolClassController {

    private final SchoolClassService service;


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createSchoolClass(@RequestBody @Valid SchoolClassRequestApiDto requestApiDto) {
        SchoolClassResponseApiDto responseApiDto = service.createSchoolClass(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse findSchoolClassById(@PathVariable Long id) {
        SchoolClassResponseApiDto responseApiDto = service.findSchoolClassById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse findAllSchoolClasses() {
        List<SchoolClassResponseApiDto> responseApiDtos = service.findAllSchoolClasses();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDtos, uri);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteSchoolClassById(@PathVariable Long id) {
        service.deleteSchoolClassById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK, "School class deleted successfully", uri);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse updateSchoolClass(@PathVariable Long id, @RequestBody SchoolClassRequestApiDto dto) {
        SchoolClassResponseApiDto responseApiDto = service.updateSchoolClass(id, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/schoolclasses")
                .path("{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.PUT, HttpStatus.OK, responseApiDto, uri);
    }


}
