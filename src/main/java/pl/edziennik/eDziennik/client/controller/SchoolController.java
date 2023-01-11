package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.basics.ApiResponse;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.school.services.SchoolService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/schools")
@AllArgsConstructor
@SuppressWarnings("rawtypes")
class SchoolController {

    private final SchoolService service;

    @PostMapping()
    public ResponseEntity<ApiResponse> createNewSchool(@Valid @RequestBody SchoolRequestApiDto requestApiDto) {
        SchoolResponseApiDto responseApiDto = service.createNewSchool(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(ApiResponse.buildApiResponse(HttpMethod.POST,HttpStatus.CREATED, responseApiDto, uri));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> findAllSchools() {
        List<SchoolResponseApiDto> responseApiDtos = service.findAllSchools();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.GET,HttpStatus.OK, responseApiDtos, uri));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findSchoolById(@PathVariable Long id) {
        SchoolResponseApiDto responseApiDto = service.findSchoolById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(
                ApiResponse.buildApiResponse(HttpMethod.GET,HttpStatus.OK, responseApiDto, uri));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSchoolById(@PathVariable Long id) {
        service.deleteSchoolById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.DELETE,HttpStatus.OK,"School deleted successfully",uri));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateSchool(@PathVariable Long id, @RequestBody SchoolRequestApiDto requestApiDto) {
        SchoolResponseApiDto responseApiDto = service.updateSchool(id, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/schools")
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        if (responseApiDto.getId().equals(id)) {
            return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.PUT,HttpStatus.OK, responseApiDto, uri));
        }
        return ResponseEntity.created(uri).body(ApiResponse.buildApiResponse(HttpMethod.PUT,HttpStatus.OK, responseApiDto, uri));
    }


}
