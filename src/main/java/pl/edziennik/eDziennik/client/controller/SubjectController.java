package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.basics.ApiResponse;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.server.subject.services.SubjectService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/subjects")
@SuppressWarnings("rawtypes")
public class SubjectController {

    private final SubjectService service;

    @PostMapping()
    public ResponseEntity<ApiResponse> createSubject(@RequestBody @Valid SubjectRequestApiDto requestApiDto){
        SubjectResponseApiDto responseApiDto = service.createNewSubject(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(ApiResponse.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> findAllSubjects(){
        List<SubjectResponseApiDto> responseApiDtos = service.findAllSubjects();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.GET,HttpStatus.OK, responseApiDtos, uri));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findSubjectById(@PathVariable Long id){
        SubjectResponseApiDto responseApiDto = service.findSubjectById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(
                ApiResponse.buildApiResponse(HttpMethod.GET,HttpStatus.OK, responseApiDto, uri));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSubjectById(@PathVariable Long id){
        service.deleteSubjectById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.DELETE,HttpStatus.OK,"Subject deleted successfully",uri));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateSubject(@PathVariable Long id, SubjectRequestApiDto requestApiDto){
        SubjectResponseApiDto responseApiDto = service.updateSubject(id, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/subjects")
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        if (responseApiDto.getId().equals(id)){
            return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.PUT,HttpStatus.OK, responseApiDto, uri));
        }
        return ResponseEntity.created(uri).body(ApiResponse.buildApiResponse(HttpMethod.PUT,HttpStatus.OK, responseApiDto, uri));
    }

}
