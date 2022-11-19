package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.authentication.AuthCredentials;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.services.TeacherService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@AllArgsConstructor
class TeacherController {

    private final TeacherService service;

    @PostMapping("/login")
    public void login(@RequestBody AuthCredentials authCredentials){

    }
    @PostMapping()
    public ResponseEntity<?> register(@RequestBody TeacherRequestApiDto requestApiDto){
        TeacherResponseApiDto responseApiDto = service.register(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(uri);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseApiDto> findTeacher(@PathVariable Long id){
        TeacherResponseApiDto responseApiDto = service.findTeacherById(id);
        return ResponseEntity.ok(responseApiDto);
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponseApiDto>> findAllTeachers(){
        return ResponseEntity.ok(service.findAllTeachers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id){
        service.deleteTeacherById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id, TeacherRequestApiDto requestApiDto){
        TeacherResponseApiDto responseApiDto = service.updateTeacher(id, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/teachers")
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        if (responseApiDto.getId().equals(id)){
            return ResponseEntity.ok(uri);
        }
        return ResponseEntity.created(uri).body(uri);
    }

}
