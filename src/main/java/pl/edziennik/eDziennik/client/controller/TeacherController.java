package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.authentication.AuthCredentials;
import pl.edziennik.eDziennik.server.basics.ApiResponse;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.services.TeacherService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@AllArgsConstructor
@SuppressWarnings("rawtypes")
class TeacherController {

    private final TeacherService service;

    @PostMapping("/login")
    public void login(@RequestBody AuthCredentials authCredentials){

    }
    @PostMapping()
    public ResponseEntity<ApiResponse> register(@RequestBody TeacherRequestApiDto requestApiDto){
        TeacherResponseApiDto responseApiDto = service.register(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(ApiResponse.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findTeacher(@PathVariable Long id){
        TeacherResponseApiDto responseApiDto = service.findTeacherById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(
                ApiResponse.buildApiResponse(HttpMethod.GET,HttpStatus.OK, responseApiDto, uri));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAllTeachers(){
        List<TeacherResponseApiDto> responseApiDtos = service.findAllTeachers();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.GET,HttpStatus.OK, responseApiDtos, uri));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTeacher(@PathVariable Long id){
        service.deleteTeacherById(id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK,"Teacher deleted successfully",uri));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTeacher(@PathVariable Long id, TeacherRequestApiDto requestApiDto){
        TeacherResponseApiDto responseApiDto = service.updateTeacher(id, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/teachers")
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        if (responseApiDto.getId().equals(id)){
            return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.PUT,HttpStatus.OK, responseApiDto, uri));
        }
        return ResponseEntity.created(uri).body(ApiResponse.buildApiResponse(HttpMethod.PUT,HttpStatus.OK, responseApiDto, uri));
    }

}
