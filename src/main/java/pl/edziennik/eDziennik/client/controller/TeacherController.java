package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.authentication.AuthCredentials;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.TeacherService;

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
    public ResponseEntity<TeacherResponseApiDto> register(@RequestBody TeacherRequestApiDto dto){
        TeacherResponseApiDto savedTeacher = service.register(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTeacher.getId())
                .toUri();
        return ResponseEntity.created(uri).body(savedTeacher);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseApiDto> findTeacher(@PathVariable Long id){
        TeacherResponseApiDto dto = service.findTeacherById(id);
        return ResponseEntity.ok(dto);
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

}
