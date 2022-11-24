package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.basics.ApiResponse;
import pl.edziennik.eDziennik.server.grade.services.GradeService;
import pl.edziennik.eDziennik.server.grade.domain.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.server.grade.domain.dto.GradeResponseApiDto;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/grades")
@SuppressWarnings("rawtypes")
public class GradeController {

    private final GradeService service;

    @PostMapping()
    public ResponseEntity<ApiResponse> createNewGrade(@RequestBody GradeRequestApiDto requestApiDto){
        GradeResponseApiDto responseApiDto = service.addNewGrade(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(ApiResponse.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> findAllGrades(){
        List<GradeResponseApiDto> responseApiDtos = service.findAllGrades();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.GET,HttpStatus.OK, responseApiDtos, uri));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findGradeById(@PathVariable Long id){
        GradeResponseApiDto responseApiDto = service.findGradeById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.GET,HttpStatus.OK, responseApiDto, uri));
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteGradeById(@PathVariable Long id){
        service.deleteRatingById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.DELETE,HttpStatus.OK,"Grade deleted successfully",uri));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateGrade(@PathVariable Long id, @RequestBody GradeRequestApiDto requestApiDto){
        GradeResponseApiDto responseApiDto = service.updateGrade(id, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/grades")
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        if (responseApiDto.getId().equals(id)){
            return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.PUT,HttpStatus.OK, responseApiDto, uri));
        }
        return ResponseEntity.created(uri).body(ApiResponse.buildApiResponse(HttpMethod.PUT,HttpStatus.OK, responseApiDto, uri));
    }
}
