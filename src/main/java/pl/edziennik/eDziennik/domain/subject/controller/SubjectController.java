package pl.edziennik.eDziennik.domain.subject.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.domain.subject.services.SubjectService;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.server.basics.page.PageDto;

import javax.validation.Valid;
import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    private final SubjectService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new subject")
    public ApiResponse<?> createSubject(@RequestBody @Valid SubjectRequestApiDto requestApiDto) {
        SubjectResponseApiDto responseApiDto = service.createNewSubject(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get list of subjects",
            description = "Returns list of all subjects")
    public ApiResponse<?> findAllSubjects(Pageable pageable) {
        PageDto<SubjectResponseApiDto> responseApiDtos = service.findAllSubjects(pageable);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDtos, uri);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get specific subject",
            description = "Returns specific subject information")
    public ApiResponse<?> findSubjectById(@PathVariable Long id) {
        SubjectResponseApiDto responseApiDto = service.findSubjectById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete subject")
    public ApiResponse<?> deleteSubjectById(@PathVariable Long id) {
        service.deleteSubjectById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK, "Subject deleted successfully", uri);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update subject",
            description = "This method will update specific subject or create new if not exists")
    public ApiResponse<?> updateSubject(@PathVariable Long id, SubjectRequestApiDto requestApiDto) {
        SubjectResponseApiDto responseApiDto = service.updateSubject(id, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/subjects")
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.PUT, HttpStatus.OK, responseApiDto, uri);
    }

}
