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
    public ResponseEntity<ApiResponse> createSchoolClass(@RequestBody @Valid SchoolClassRequestApiDto requestApiDto) {
        SchoolClassResponseApiDto responseApiDto = service.createSchoolClass(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findSchoolClassById(@PathVariable Long id) {
        SchoolClassResponseApiDto responseApiDto = service.findSchoolClassById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(
                ApiResponseCreator.buildApiResponse(HttpMethod.GET,HttpStatus.OK, responseApiDto, uri));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> findAllSchoolClasses(){
        List<SchoolClassResponseApiDto> responseApiDtos = service.findAllSchoolClasses();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponseCreator.buildApiResponse(HttpMethod.GET,HttpStatus.OK, responseApiDtos, uri));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSchoolClassById(@PathVariable Long id){
        service.deleteSchoolClassById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponseCreator.buildApiResponse(HttpMethod.DELETE,HttpStatus.OK,"School class deleted successfully",uri));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateSchoolClass(@PathVariable Long id, @RequestBody SchoolClassRequestApiDto dto){
        SchoolClassResponseApiDto responseApiDto = service.updateSchoolClass(id, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/schoolclasses")
                .path("{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        if (responseApiDto.getId().equals(id)){
            return ResponseEntity.ok(ApiResponseCreator.buildApiResponse(HttpMethod.PUT,HttpStatus.OK, responseApiDto, uri));
        }
        return ResponseEntity.created(uri).body(ApiResponseCreator.buildApiResponse(HttpMethod.PUT,HttpStatus.OK, responseApiDto, uri));
    }


}
